
package engine;

import java.net.*;
import java.io.*;

import Connect4.Config;
import engine.GameEngine;
import entity.Pawn;
import entity.Player;

import java.awt.Color;
import java.awt.Font;
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
			ss = new ServerSocket(Config.onlineServerPort);
			soc = null;
	        Thread t = new Thread() {
				public void run() {
					try {
						soc = ss.accept();
				        soc.setSoTimeout(Config.maxWaitingTimeForTurn);
				        Output = new PrintWriter(soc.getOutputStream(), true);
				        otherPlayerReader = new InputStreamReader(soc.getInputStream());
					}
			    	catch (IOException e) {
			    		// TODO: error gesture
			    		
			    		endGame();
					}
				}
			};
			t.start();
		}
		catch (IOException e1) {
			// TODO: error gesture
			
			e1.printStackTrace();
		}
        
        build();
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
		    soc = new Socket(host, Config.onlineServerPort);
		    soc.setSoTimeout(Config.maxWaitingTimeForTurn);
		    Output = new PrintWriter(soc.getOutputStream(), true);
		    otherPlayerReader = new InputStreamReader(soc.getInputStream());
		    
		    // TODO: enable to connect exception
		    startListen();
	    }
	    catch(IOException e){
	        e.printStackTrace();
	        
	        // TODO: error gesture
	    }
	    
	    build();
    }
    
    /**
	 * This function here is not usable because we only need the first player so we override.
	 * -----------------------
	 * DON'T USE THIS FUNCTION -> Use the start(Player localPlayer) function instead.
	 * -----------------------
	 */
	public void start(Player player1, Player player2) {
		start(player1);
	}
	
	/**
	 * This function starts a game with the local player and get the data for the second player automatically.
	 * @param localPlayer: the local player
	 */
	public void start(Player localPlayer) {
		// TODO: get all the informations concerning the second player
		Player player2 = new Player("Paulette", Config.PLAYER2_LABEL);
		
		super.start(localPlayer, player2);
	}
    
    /**
	 * This function updates the game engine.
	 */
    @Override
	public void update() {		
		super.update();
	}
    
    /**
	 * Render function for the game engine.
	 */
	public void render(Graphics g) {
		super.render(g);
		
		// not connected
		if(soc == null) {
			Font font = g.getFont();
			Color color = g.getColor();
			g.setFont(font.deriveFont((float) 30));
			g.setColor(new Color(0, 255, 0));
			g.drawString("En attente du joueur...", 115, 325);
			g.setColor(new Color(0, 255, 0));
			g.setFont(font);
			g.setColor(color);
		}
	}
    
	/**
	 * This function display the turn in the engine.
	 * @param g: the graphics by swing
	 */
	protected void displayTurn(Graphics g) {
		if(soc != null)
			super.displayTurn(g);
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
    	if(soc == null)
    		return;
    	
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
		if(soc != null && isMyTurn())
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
    	if(this.stopEngine) {
	    	try {
	    		if(soc != null) {
		    		Output.close();
		    		otherPlayerReader.close();
					soc.close();
	    		}
	    		if(ss != null)
					ss.close();
			} 
	    	catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }

}
