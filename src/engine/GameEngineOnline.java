
package engine;

import java.net.*;
import java.io.*;

import Connect4.Config;
import engine.GameEngine;
import entity.Pawn;

import java.awt.Graphics;
import java.awt.Point;

/**
 * This class is a game engine for the online mode.
 */
public class GameEngineOnline extends GameEngine{
        
	private static final long serialVersionUID = 1L; 	// to fix warning
	
	private ServerSocket ss = null;						/** The socket server. */
    private Socket soc;									/** The socket. */
    private PrintWriter Output;							/** The output of the socket. */
    private InputStreamReader otherPlayerReader;		/** The input of the socket. */
    
    /**
     * Constructor to set the engine as a server.
     * @param width: the width
     * @param height: the height
     */
    public GameEngineOnline(int width, int height)
    {
        super(width, height);
        
        try {
	        ss = new ServerSocket(1996);
	        soc = ss.accept();
	        soc.setSoTimeout(Config.maxWaitingTimeForTurn);
	        Output = new PrintWriter(soc.getOutputStream(), true);
	        otherPlayerReader = new InputStreamReader(soc.getInputStream());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Constructor to set the engine as a client.
     * @param width: the width
     * @param height: the height
     * @param host: the host to connect with
     */
    public GameEngineOnline(int width, int height, String host){
	   super(width, height);
	   this.playerOneTurn= false;

	    try {   
		    soc = new Socket(host,1996);
		    soc.setSoTimeout(Config.maxWaitingTimeForTurn);
		    Output = new PrintWriter(soc.getOutputStream(), true);
		    otherPlayerReader = new InputStreamReader(soc.getInputStream());
		    
		    // TODO: enable to connect exception
		    startListen();
	    }
	    catch(IOException e){
	        e.printStackTrace();
	    }
    }
    
    /**
	 * This function updates the game engine.
	 */
    @Override
	public void update() {		
		super.update();
	}
    
    /**
     * This function listens the socket.
     * @return the value gave by the socket
     */
    public String listen()
    { 
        String Message = null;
        try {
        	Message = "" + otherPlayerReader.read();
        }
        catch(SocketTimeoutException e) {
        	// TODO: too long exception
        	
        	closeConnection();
        }
        catch (IOException e) {
        	closeConnection();
        }
        
        return Message;
    }
    
    /**
     * This function starts to listen for the other player in a thread.
     */
    public void startListen() {
    	Thread t = new Thread() {
			public void run() {
				try {
			    	String Message = listen();
			        if(gameIsRunning && !Message.isEmpty())
		        	{
		        		int pawnColPosition = Integer.parseInt(Message);
		            	addPawn(pawnColPosition, new Pawn(getCurrentPlayer()));
		        	}
				}
		    	catch (NullPointerException e) {
		    		endGame();
		    	}
			}
		};
		t.start();
    }
    
    /**
     * See GameEngine.addPawn().
     * Here we add the management of the network.
     */
    @Override
    public void addPawn(int x, Pawn pawn) {
    	super.addPawn(x, pawn);
    	
    	if(!isMyTurn()) {
    		// start to listen
    		startListen();
    		
    		// send the value played (because we have played so it's not our turn)
			Output.write(this.currentPawnColPosition);
	        Output.flush();
    	}
    	else {
    		// TODO: start a timer
    	}
    }
    
    /**
	 * This function is called when the grid is clicked.
	 * @param p: the mouse click position
	 */
    @Override
	protected void gridClicked(Point p) {
		if(isMyTurn())
			super.gridClicked(p);
	}
    
    /**
	 * This function draw the pawn above the grid.
	 * @param g: the graphics object
	 */
    @Override
	public void displayPawnAboveGrid(Graphics g) {
		if(isMyTurn())
			super.displayPawnAboveGrid(g);
	}
    
    /**
     * Returns if it's the turn of the local player.
     * @return the result lol
     */
    private boolean isMyTurn() {
    	return (
    			(this.ss == null && !this.playerOneTurn) || // client and player 2
    			(this.ss != null && this.playerOneTurn)		// server and player 1
    			);
    }
    
    /**
     * See GameEngine.enGame().
     * Here we add the management of the network.
     */
    @Override
    public void endGame() {
    	super.endGame();
    	
    	closeConnection();
    }
    
    /**
     * This function closes the connection.
     */
    private void closeConnection() {
    	try {
			soc.close();
			if(ss != null)
				ss.close();
			ss = null;
		} 
    	catch (IOException e) {
			e.printStackTrace();
		}
    }

}
