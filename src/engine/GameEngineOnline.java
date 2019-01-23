
package engine;

import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.io.*;

import Connect4.Config;
import engine.GameEngine;
import entity.Pawn;
import entity.PawnName;
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
	
	private ServerSocket ss = null;								/** The socket server. */
    private Socket soc;											/** The socket. */
    private PrintWriter Output;									/** The output of the socket. */
    private InputStreamReader otherPlayerReader;				/** The input of the socket. */
    private float timerValue = Config.maxWaitingTimeForTurn;	/** The value of the timer in ms. */
    private boolean startTimer = false;							/** If the timer is started or not. */
    
    /**
     * Constructor to set the engine as a server.
     * @param width: the width
     * @param height: the height
     * @throws IOException: if we can't start the server. 
     */
    public GameEngineOnline(int width, int height) throws IOException
    {
        super(width, height);
		ss = new ServerSocket(Config.onlineServerPort);
		soc = null;
        Thread t = new Thread() {
			public void run() {
				try {
					soc = ss.accept();
			        soc.setSoTimeout(Config.maxWaitingTimeForTurn);
			        Output = new PrintWriter(soc.getOutputStream(), true);
			        otherPlayerReader = new InputStreamReader(soc.getInputStream());
			        
			        // get informations about the second player
			        BufferedReader reader = new BufferedReader(otherPlayerReader);
			        String result = reader.readLine();
			        player2 = new Player(result.substring(0, result.indexOf(0)), PawnName.valueOf(result.substring(result.indexOf(0) + 1)));
			        
			        // send ours
			        Output.println(player1.getName() + (char) 0 + player1.getPawnName());
		        	Output.flush();
		        	
		        	checkPbWithPlayers();
			        
			        startTimer();
				}
		    	catch (IOException e) {
		    		if(ss == null)
		    			JOptionPane.showMessageDialog(new JFrame(), "Impossible d'héberger une partie.", "Erreur", JOptionPane.ERROR_MESSAGE);
					quitEngine();
				}
			}
		};
		t.start();
    
		build();
    }

    /**
     * Constructor to set the engine as a client.
     * @param width: the width
     * @param height: the height
     * @param host: the host to connect with
     * @throws IOException: can't listen
     * @throws UnknownHostException: impossible to connect to the host
     */
    public GameEngineOnline(int width, int height, String host) throws UnknownHostException, IOException{
    	super(width, height);
    	this.playerOneTurn = false;

    	ss = null;
    	soc = new Socket(host, Config.onlineServerPort);
	    soc.setSoTimeout(Config.maxWaitingTimeForTurn);
	    Output = new PrintWriter(soc.getOutputStream(), true);
	    otherPlayerReader = new InputStreamReader(soc.getInputStream());
	    
	    startTimer();
	    
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
		
		// the client send its informations and then receive the informations of the server
		if(ss == null) {
			BufferedReader reader = new BufferedReader(otherPlayerReader);
			
			Output.println(localPlayer.getName() + (char) 0 + localPlayer.getPawnName());
        	Output.flush();
        	
	        try {
	        	String result = reader.readLine();
		        player2 = new Player(result.substring(0, result.indexOf(0)), PawnName.valueOf(result.substring(result.indexOf(0) + 1)));
				
				startListen();
				
				super.start(player2, localPlayer);
				
			} catch (IOException e) {
    			JOptionPane.showMessageDialog(new JFrame(), "Erreur lors de la récupération des informations sur le joueur.", "Erreur", JOptionPane.ERROR_MESSAGE);
				quitEngine();
			}   
		}
		
		// for the server
		else
			super.start(localPlayer, player2);
		
		checkPbWithPlayers();
	}
    
    /**
	 * This function updates the game engine.
	 */
    @Override
	public void update() {
		super.update();
		
		if(this.startTimer && this.gameIsRunning) {
			if(this.timerValue > 0)
				this.timerValue -= 1000 / (long) Config.gameFPS;
			else if (isMyTurn()) {
				JOptionPane.showMessageDialog(new JFrame(), "Vous avez mis trop de temps à jouer, vous avez perdu (et oui c'est comme ça papi).", "Information", JOptionPane.INFORMATION_MESSAGE);
				quitEngine();
				this.startTimer = false;
			}
		}
	}
    
    /**
	 * Render function for the game engine.
	 */
	public void render(Graphics g) {
		super.render(g);
		
		// not connected
		if(soc == null) {
			// for the server
			if(ss != null) {
				Font font = g.getFont();
				Color color = g.getColor();
				g.setFont(font.deriveFont((float) 30));
				g.setColor(new Color(0, 255, 0));
				g.drawString("En attente du joueur...", 115, 325);
				g.setColor(new Color(0, 255, 0));
				g.setFont(font);
				g.setColor(color);
			}
			// for the client
			else {
				Font font = g.getFont();
				Color color = g.getColor();
				g.setFont(font.deriveFont((float) 30));
				g.setColor(new Color(0, 255, 0));
				g.drawString("Connexion...", 115, 325);
				g.setColor(new Color(0, 255, 0));
				g.setFont(font);
				g.setColor(color);
			}
		}
	}
    
	/**
	 * This function display the turn in the engine.
	 * @param g: the graphics by swing
	 */
	protected void displayTurn(Graphics g) {
		if(soc != null) {
			super.displayTurn(g);

			if(this.gameIsRunning) {
				Font font = g.getFont();
				Color color = g.getColor();
				g.setFont(font.deriveFont((float) 20));
				g.setColor(new Color(0, 255, 0));
				g.drawString("Plus que " + (((int) (this.timerValue / 1000)) + 1) + "s", 525, 250);
				g.setColor(new Color(0, 255, 0));
				g.setFont(font);
				g.setColor(color);
			}
		}
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
        	
        	if(Message.equals("-1")) {
        		if(this.gameIsRunning)
            		JOptionPane.showMessageDialog(new JFrame(), "L'adversaire s'est déconnecté.", "Erreur", JOptionPane.ERROR_MESSAGE);
        		
            	quitEngine();
        	}
        }
        catch(SocketTimeoutException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Le joueur adverse a mis trop de temps à jouer, vous avez donc gagné.", "Information", JOptionPane.INFORMATION_MESSAGE);
			Connect4.Connect4.accountManager.getConnectedAccount().addPoints(Config.pointsAddedWhenWin); // add the points
        	quitEngine();
        }
        catch (IOException e) {
        	if(this.gameIsRunning)
        		JOptionPane.showMessageDialog(new JFrame(), "L'adversaire s'est déconnecté.", "Erreur", JOptionPane.ERROR_MESSAGE);
        	quitEngine();
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
		    		quitEngine();
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
    	
    	if(!isMyTurn() || (!this.gameIsRunning && isMyTurn())) {
    		// start to listen if the game is not finished yet
    		if(this.gameIsRunning)
    			startListen();
    		
    		// send the value played (because we have played so it's not our turn)
			Output.write(this.currentPawnColPosition);
	        Output.flush();
    	}

   		startTimer();
    	
    	// the game is finished: we give the points
    	if(!this.gameIsRunning) 
			if(
	    			(ss != null && this.playerOneTurn) ||	// we are the server and we have won
	    			(ss == null && !this.playerOneTurn)		// we are the client and we have won
	    		)
				Connect4.Connect4.accountManager.getConnectedAccount().addPoints(Config.pointsAddedWhenWin); // add the points
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
	 * This function draws the pawn above the grid.
	 * @param g: the graphics object
	 */
    @Override
	public void displayPawnAboveGrid(Graphics g) {
		if(soc != null && isMyTurn())
			super.displayPawnAboveGrid(g);
	}
    
    /**
	 * This function display the name of the player that must play.
	 * @param g: graphics by swing
	 */
    @Override
	protected void displayPlayerTurn(Graphics g) {
    	// not out turn: display the login of the other player
    	// for the client
    	if(!isMyTurn() && ss == null) {
			Font font = g.getFont();
			Color color = g.getColor();
			g.setFont(font.deriveFont((float) 30));
			g.setColor(new Color(0, 255, 0));
			g.drawString("Au tour de :", 525, 150);
			g.drawString(this.player1.getName(), 525, 190);
			g.setColor(new Color(0, 255, 0));
			g.setFont(font);
			g.setColor(color);
    	}
    	
    	// for the server
    	else if(!isMyTurn() && ss != null) { 
			Font font = g.getFont();
			Color color = g.getColor();
			g.setFont(font.deriveFont((float) 30));
			g.setColor(new Color(0, 255, 0));
			g.drawString("Au tour de :", 525, 150);
			g.drawString(this.player2.getName(), 525, 190);
			g.setColor(new Color(0, 255, 0));
			g.setFont(font);
			g.setColor(color);
    	}
    	
    	// our turn
    	else {
    		Font font = g.getFont();
			Color color = g.getColor();
			g.setFont(font.deriveFont((float) 30));
			g.setColor(new Color(0, 255, 0));
			g.drawString("À votre tour", 525, 150);
			g.setColor(new Color(0, 255, 0));
			g.setFont(font);
			g.setColor(color);
    	}
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

    	Connect4.Connect4.menu.refreshNbPoints();
    }
    
    /**
     * See GameEngine.quitEngine().
     */
    public void quitEngine() {
    	super.quitEngine();
    	
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
    
    /**
     * This function starts the timer.
     */
    private void startTimer() {
    	this.startTimer = true;
		this.timerValue = Config.maxWaitingTimeForTurn;
    }

    /**
     * This function checks if there are problems with the player: if they have the same pawn.
     */
    private void checkPbWithPlayers() {
    	// same pawns
    	if(this.player2 != null && this.player1.getPawnName().equals(this.player2.getPawnName())) {
    		JOptionPane.showMessageDialog(new JFrame(), "Vous avez sélectionné les mêmes pions, vous allez donc avoir les pions basiques.", "Attention", JOptionPane.INFORMATION_MESSAGE);
    		this.player1.setPawnName(PawnName.BasicPawn1);
    		this.player2.setPawnName(PawnName.BasicPawn2);
    	}
    }
}
