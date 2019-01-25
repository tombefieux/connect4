package engine;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Connect4.Config;
import entity.Pawn;
import entity.Player;

/**
 * This class represents the game engine of the connect 4.
 * It manages the games and the win and loose system. 
 */
public class GameEngine extends JPanel implements MouseListener {

	protected static final long serialVersionUID = 1L;
	
	protected Pawn[][] grid;				/** The grid of the connect 4 */
	protected int width;					/** The width of the grid */
	protected int height;					/** The height of the grid */
	protected Player player1;				/** The first player */
	protected Player player2;				/** The second player */
	protected boolean playerOneTurn;		/** If it's to the player 1 to play. */
	protected boolean gameIsRunning;		/** If a game is running in the engine. */
	protected int currentPawnColPosition;	/** The current position of the pawn. */
	protected boolean stopEngine = true;	/** If the engine must be stopped. */
        
	// for graphics
	protected Image backgroundImage;		/** The background image. */
	
	/**
	 * Constructor of the engine.
	 * @param width: the width of the grid
	 * @param height: the height of the grid
	 */
	public GameEngine(int width, int height) {
		super();
		
		// init the grid
		this.grid = new Pawn[width][];
		for (int i = 0 ; i < width; i++) {
			this.grid[i] = new Pawn[height];
		}
		
		// reset the grid (init to null)
		resetGrid();
		
		this.width = width;
		this.height = height;
	
		// init player to null
		this.player1 = null;
		this.player2 = null;
		
		this.playerOneTurn = true;
		this.gameIsRunning = false;
		this.stopEngine = false;
		
		// graphics content
		loadImages();
		setSize(Config.windowWidth, Config.windowHeight);
		setBackground(Color.black);
		setFocusable(true);
		addMouseListener(this);
		
		build();
	}
	
	/**
	 * This function builds graphics elements.
	 */
	protected void build() {
		// return button
		Button btRetour = new Button("Retour au menu");
		btRetour.setBounds(600, 400, 150, 30);
		btRetour.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 quitEngine();
			}
		});
		this.setLayout(null);
		this.add(btRetour);
	}
	
	/**
	 * This function load the images needed to render.
	 */
	private void loadImages() {
        ImageIcon iid = new ImageIcon(Config.gameBackgroundImagePath);
        this.backgroundImage = iid.getImage();
    }
	
	/**
	 * This function starts a game.
	 * @param player1: the first player
	 * @param player2: the second player
	 */
	public void start(Player player1, Player player2) {
		resetEngine();
		
		this.player1 = player1;
		this.player2 = player2;
		
		this.playerOneTurn = true;
		this.gameIsRunning = !this.gameIsRunning;
		this.stopEngine = false;
	}
	
	/**
	 * This function updates the game engine.
	 */
	public void update() {		
		repaint();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	// (function triggered when there's a click)
	@Override
	public void mouseClicked(MouseEvent event) {
		
		Point p = event.getPoint();
		
		// if it's on the grid and it's the turn of a player (not an AI)
		if(
				p.x >= Config.gridMarginLeft && p.x <= Config.gridMarginLeft + Config.grigSize &&
				p.y >= Config.windowHeight - Config.gridMarginLeft - Config.grigSize - 25 && p.y <= Config.windowHeight - Config.gridMarginLeft - 25 &&
				getCurrentPlayer() instanceof Player
				) {
			gridClicked(p);	
		}
	}
	
	/**
	 * This function is called when the grid is clicked.
	 * @param p: the mouse click position
	 */
	protected void gridClicked(Point p) {
		
		// if we are not in a game
		if(!isGameRunning())
			return;

		// get the column
		currentPawnColPosition = (p.x - Config.gridMarginLeft) / 60;
		
		// if impossible on this column
		if(!getPossiblesX().contains(currentPawnColPosition))
			return;

		// add the pawn
		addPawn(currentPawnColPosition, new Pawn(getCurrentPlayer()));
	}
	
	/**
	 * Add a pawn at this x.
	 * After have added the pawn, it checks if the game is ended or not etc...
	 * @param x: the x value for the grid
	 * @param pawn: the pawn
	 */
	public void addPawn(int x, Pawn pawn) {
		
		List<Integer> possiblesX = getPossiblesX();
		
		// see if possible
		if(!possiblesX.contains(x))
			return;
		
		// add it
		this.grid[x][getYWithX(x)] = pawn;
		
		boolean gameEnded = false;
		
		// see if won with this pawn
		if(isWonWith(x, getYWithX(x) + 1)) // -1 because we've already add the pawn 
			gameEnded = true;	

		
		// if the grid is full
		possiblesX = getPossiblesX();
		if(possiblesX.isEmpty())
			gameEnded = true;
		
		// change the turn if we continue
		if(!gameEnded)
			this.playerOneTurn = !this.playerOneTurn;
		
		// if the game is finished
		if(gameEnded) endGame();
		
		// TODO: FOR THE AI: check if the new current player is an instance of AI and then let it play.
		/**
		 * Create a function play which takes the grid and return the x to play. Then, use the add pawn function with the x returned. (not that difficult ;))
		 */
	}
	
	/**
	 * This function is an override of the JPanel function to draw our game.
	 * @param g: graphic object (gave by swing)
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        render(g);
    }
	
	/**
	 * Render function for the game engine.
	 */
	public void render(Graphics g) {
		// display the background
		g.drawImage(this.backgroundImage, 0, -30, this);
		
		// display the pawn above the grid
		displayPawnAboveGrid(g);
			
		// display all the pawns
		for(int i = 0; i < Config.GRID_WIDTH; i++)
			for(int j = 0; j < Config.GRID_HEIGHT; j++)
				if(this.grid[i][j] != null)
					g.drawImage(
								Config.getImageOfPawn(this.grid[i][j].getOwner().getPawnName(), this.grid[i][j].isHighlighted()),
								Config.gridMarginLeft + i * Config.pawnSize,
								Config.windowHeight - Config.gridMarginLeft - Config.grigSize - Config.pawnSize + j * Config.pawnSize + 35,
								this
							);
		
		// display the turn
		displayTurn(g);
	}
	
	/**
	 * This function draws the pawn above the grid.
	 * @param g: the graphics object
	 */
	public void displayPawnAboveGrid(Graphics g) {
		// if we're running a game
		if(isGameRunning()) {
			// if the mouse is at the good position
			Point p = getMousePositionInWindow();
			if(
					p.x >= Config.gridMarginLeft && p.x <= Config.gridMarginLeft + Config.grigSize &&
					p.y >= Config.windowHeight - Config.gridMarginLeft - Config.grigSize && p.y <= Config.windowHeight - Config.gridMarginLeft
					) {
				
				// get the column
				int pawnColPosition = (p.x - Config.gridMarginLeft) / 60;
				
				// if possible on this column
				if(getPossiblesX().contains(pawnColPosition))
					g.drawImage(
								Config.getImageOfPawn(getCurrentPlayer().getPawnName(), false),
								Config.gridMarginLeft + pawnColPosition * Config.pawnSize,
								Config.windowHeight - Config.gridMarginLeft - Config.grigSize - Config.pawnSize - 40,
								this
							);
				
			}
		}
	}
	
	/**
	 * This function display the turn in the engine.
	 * @param g: the graphics by swing
	 */
	protected void displayTurn(Graphics g) {
		if(this.gameIsRunning) {
			displayPlayerTurn(g);
		}
		else if(getPossiblesX().isEmpty()) {
			Font font = g.getFont();
			Color color = g.getColor();
			g.setFont(font.deriveFont((float) 30));
			g.setColor(new Color(0, 0, 0));
			g.drawString("Partie nulle !", 525, 150);
			g.setFont(font);
			g.setColor(color);
		}
		else {
			Font font = g.getFont();
			Color color = g.getColor();
			g.setFont(font.deriveFont((float) 30));
			g.setColor(new Color(0, 0, 0));
			g.drawString("Partie gagnée par :", 525, 150);
			g.drawString(getCurrentPlayer().getName(), 525, 190);
			g.setFont(font);
			g.setColor(color);
		}
	}
	
	/**
	 * This function display the name of the player that must play.
	 * @param g: graphics by swing
	 */
	protected void displayPlayerTurn(Graphics g) {
		Font font = g.getFont();
		Color color = g.getColor();
		g.setFont(font.deriveFont((float) 30));
		g.setColor(new Color(0, 0, 0));
		g.drawString("Au tour de :", 525, 150);
		g.drawString(getCurrentPlayer().getName(), 525, 190);
		g.setFont(font);
		g.setColor(color);
	}
	
	/**
	 * This function ends a game.
	 */
	public void endGame() {
		repaint();
		this.gameIsRunning = false;
	}
	
	/**
	 * This function resets the engine of previous game.
	 */
	public void resetEngine() {
		resetGrid();
		this.player1 = null;
		this.player2 = null;
		
		this.stopEngine = true;
	}
	
	/**
	 * This function checks if there's a connect 4 with the pawn at (x, y).
	 * @param x
	 * @param y
	 * @return if there's a connect 4
	 */
	public boolean isWonWith(int x, int y) {
		return (checkLine(x, y) ||
				checkRow(x, y)  ||
				checkDiagonal(x, y) ||
				 checkReversedDiagonal(x, y)
				);
	}
	
	/**
	 * This function checks if there's a connect4 with the pawn at (x, y) in line.
	 * This function also highlights the winning pawns.
	 * @param x
	 * @param y
	 * @return if there's a connect 4
	 */
	public boolean checkLine(int x, int y) {
		int cpt = 1;
		int startingX = x;
		boolean stopLeft = false, stopRight = false;
		Player owner = this.grid[x][y].getOwner();
		
		for (int i = 1; i <= 3 && (!stopLeft || !stopRight); i++) {
			
			// check on the left of the pawn
			if(x - i >= 0 && !stopLeft) {
				if(this.grid[x - i][y] != null && this.grid[x - i][y].getOwner().equals(owner)) {
					if(startingX > x - i)
						startingX = x - i;
					cpt++;
				}
					
				else
					stopLeft = true;
			}
			
			// check on the right of the pawn
			if(x + i < Config.GRID_WIDTH && !stopRight) {
				if(this.grid[x + i][y] != null && this.grid[x + i][y].getOwner().equals(owner))
					cpt++;
				else
					stopRight = true;
			}
		}
	
		// if win highlight
		if(cpt >= 4)
			for (int i = 0; i < cpt; i++)
				this.grid[startingX + i][y].setHighlighted(true);
		
		return (cpt >= 4);
	}

	/**
	 * This function checks if there's a connect4 with the pawn at (x, y) in row.
	 * This function also highlights the winning pawns.
	 * @param x
	 * @param y
	 * @return if there's a connect 4
	 */
	public boolean checkRow(int x, int y) {
		int cpt = 1;
		int startingY = y;
		boolean stopTop = false, stopBottom = false;
		Player owner = this.grid[x][y].getOwner();
		
		for (int i = 1; i <= 3 && (!stopTop || !stopBottom); i++) {
			
			// check on the top of the pawn
			if(y - i >= 0 && !stopTop) {
				if(this.grid[x][y - i] != null && this.grid[x][y - i].getOwner().equals(owner)) {
					if(startingY > y - i)
						startingY = y - i;
					cpt++;
				}
				else
					stopTop = true;
			}
			
			// check on the bottom of the pawn
			if(y + i < Config.GRID_HEIGHT && !stopBottom) {
				if(this.grid[x][y + i] != null && this.grid[x][y + i].getOwner().equals(owner))
					cpt++;
				else
					stopBottom = true;
			}
		}
		
		// if win highlight
		if(cpt >= 4)
			for (int i = 0; i < cpt; i++)
				this.grid[x][startingY + i].setHighlighted(true);
		
		return (cpt >= 4);
	}
	
	/**
	 * This function checks if there's a connect4 with the pawn at (x, y) in diagonal.
	 * This function also highlights the winning pawns.
	 * @param x
	 * @param y
	 * @return if there's a connect 4
	 */
	public boolean checkDiagonal(int x, int y) {
		int cpt = 1;
		int startingX = x;
		boolean stopTopLeft = false, stopBottomRight = false;
		Player owner = this.grid[x][y].getOwner();
		
		for (int i = 1; i <= 3 && (!stopTopLeft || !stopBottomRight); i++) {
			
			// check on the top left of the pawn
			if(x - i >= 0 && y - i >= 0 && !stopTopLeft) {
				if(this.grid[x - i][y - i] != null && this.grid[x - i][y - i].getOwner().equals(owner)) {
					if(startingX > x - i)
						startingX = x - i;
					cpt++;
				}
				else
					stopTopLeft = true;
			}
			
			// check on the bottom right of the pawn
			if(x + i < Config.GRID_WIDTH && y + i < Config.GRID_HEIGHT && !stopBottomRight) {
				if(this.grid[x + i][y + i] != null && this.grid[x + i][y + i].getOwner().equals(owner))
					cpt++;
				else
					stopBottomRight = true;
			}
		}
		
		// if win highlight
		if(cpt >= 4)
			for (int i = 0; i < cpt; i++)
				this.grid[startingX + i][y - (x - startingX) + i].setHighlighted(true);
		
		return (cpt >= 4);
	}
	
	/**
	 * This function checks if there's a connect4 with the pawn at (x, y) in the reversed diagonal.
	 * This function also highlights the winning pawns.
	 * @param x
	 * @param y
	 * @return if there's a connect 4
	 */
	public boolean checkReversedDiagonal(int x, int y) {
		int cpt = 1;
		int startingY = y;
		boolean stopBottomLeft = false, stopTopRight = false;
		Player owner = this.grid[x][y].getOwner();
		
		for (int i = 1; i <= 3 && (!stopBottomLeft || !stopTopRight); i++) {
			
			// check on the bottom left of the pawn
			if(x - i >= 0 && y + i < Config.GRID_HEIGHT && !stopBottomLeft) {
				if(this.grid[x - i][y + i] != null && this.grid[x - i][y + i].getOwner().equals(owner))
					cpt++;
				else
					stopBottomLeft = true;
			}
			
			// check on the top right of the pawn
			if(x + i < Config.GRID_WIDTH && y - i >= 0 && !stopTopRight) {
				if(this.grid[x + i][y - i] != null && this.grid[x + i][y - i].getOwner().equals(owner)) {
					if(startingY > y - 1)
						startingY = y- i;
					cpt++;
				}
				else
					stopTopRight = true;
			}
		}
		
		// if win highlight
		if(cpt >= 4)
			for (int i = 0; i < cpt; i++)
				this.grid[x + (y - startingY) - i][startingY + i].setHighlighted(true);
		
		return (cpt >= 4);
	}
	
	/**
	 * This function returns the y where we can drop a pawn in x.
	 * @param x
	 * @return the y
	 */
	public int getYWithX(int x) {
		int y = 0;
		while (y < height && this.grid[x][y] == null)
			y++;
		
		if(y != 0) y--;
		
		return y;
	}
	
	/**
	 * This function returns the x that are possibles to play.
	 * @return the x that are possible (from 0 to ... (the indexes)).
	 */
	protected List<Integer> getPossiblesX() {
		List<Integer> result = new ArrayList<Integer>();
		
		// for each line
		for (int i = 0; i < this.height; i++)
			if(this.grid[i][0] == null)
				result.add(i);
		
		return result;
	}
	
	/**
	 * This function resets the grid with null values.
	 */
	private void resetGrid() {
		for (int i = 0 ; i < width; i++)
			for (int j = 0 ; j < width; j++)
				this.grid[i][j] = null;
	}
	
	/**
	 * The to string function.
	 * @return the grid in string.
	 */
	public String toString() {
		
		String result = "";
		
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				if(i == 0)
					result += "|";
				
				if(grid[i][j] == null)
					result += " ";
				else {
					if(grid[i][j].getOwner().equals(player1))
						result += "X";
					else
						result += "Y";
				}
					
				result += "|";
			}
			
			result += "\n";
			
			if(j == height - 1) {
				for (int i = 0; i <= width * 2; i++)
					result += "-";
				
				result += "\n";
				
				for (int i = 0; i < width; i++)
					result += " " + (i + 1);
			}
		}
		
		return result;
	}
	
	/**
	 * This function returns the position of the mouse in the window.
	 * @return the position of the mouse
	 */
	private Point getMousePositionInWindow() {
		// get the position of the mouse in the window
		Point p = MouseInfo.getPointerInfo().getLocation();
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		int mouseX = (int) (p.x - topFrame.getLocation().getX() - 3); // - 3 I don't know why but it's to match with the position returned by the click event 
		int mouseY = (int) (p.y - topFrame.getLocation().getY());
		if(mouseX < 0) mouseX = 0;
		else if (mouseX > Config.windowWidth) mouseX = Config.windowWidth;
		if(mouseY < 0) mouseY = 0;
		else if (mouseY > Config.windowHeight - 25) mouseY = Config.windowHeight;
		
		return new Point(mouseX, mouseY);
	}

	/**
	 * Getter to know if a game is running.
	 * @return: if a game is running
	 */
	public boolean isGameRunning() {
		return gameIsRunning;
	}
	
	/**
	 * Getter to know if the game engine is done.
	 * @return: if the game engine is done.
	 */
	public boolean isStopped() {
		return this.stopEngine;
	}
	
	/**
	 * To properly quit the engine.
	 */
	public void quitEngine() {
		this.stopEngine = true;
		endGame();
	}
	
	/**
	 * Returns the current player.
	 * @return the current player.
	 */
	public Player getCurrentPlayer() {
		Player currentPlayer;
		if(this.playerOneTurn)
			currentPlayer = this.player1;
		else
			currentPlayer = this.player2;
		return currentPlayer;
	}        

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
