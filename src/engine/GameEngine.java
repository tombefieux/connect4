package engine;

import java.util.ArrayList;
import java.util.List;

import Connect4.Config;
import entity.Pawn;
import entity.Player;

/**
 * This class represents the game engine of the connect 4.
 * It manages the games and the win and loose system. 
 */
public class GameEngine {

	private Pawn[][] grid;				/** The grid of the connect 4 */
	private int width;					/** The width of the grid */
	private int height;					/** The height of the grid */
	private Player player1;				/** The first player */
	private Player player2;				/** The second player */
	private boolean playerOneTurn;		/** If it's to the player 1 to play. */
	private boolean gameIsRunning;		/** If a game is running in the engine. */
	
	/**
	 * Constructor of the engine.
	 * @param width: the width of the grid
	 * @param height: the height of the grid
	 */
	public GameEngine(int width, int height) {
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
	}
	
	/**
	 * This function starts a game.
	 * @param player1: the first player
	 * @param player2: the second player
	 */
	public void start(Player player1, Player player2) {
		resetGrid();
		
		this.player1 = player1;
		this.player2 = player2;
		
		this.playerOneTurn = true;
		this.gameIsRunning = true;
	}
	
	/**
	 * This function updates the game engine, it lets play the player for a turn.
	 */
	public void newTurn() {
		
		// if we haven't start
		if(this.player1 == null)
			return;
		
		// play (get the x of the pawn)
		Player currentPlayer;
		if(this.playerOneTurn)
			currentPlayer = this.player1;
		else
			currentPlayer = this.player2;
		
		// get the x
		int pawnX = -1;
		List<Integer> possiblesX = getPossiblesX();
		do {
			System.out.println("\nA " + currentPlayer.getName() + " de jouer :");	
			pawnX = currentPlayer.play();
			
			// if not possible
			if(!possiblesX.contains(pawnX)) {
				System.out.print("Les possibilitées sont : ");
				for (int i = 0; i < possiblesX.size(); i++)
					System.out.print((possiblesX.get(i) + 1) + " ");
				System.out.println("");
			}
			
		} while (!possiblesX.contains(pawnX));
		
		// add the pawn
		addPawn(pawnX, new Pawn(currentPlayer));
		
		boolean gameEnded = false;
		
		// see if won with this pawn
		if(isWonWith(pawnX, getYWithX(pawnX) + 1)) { // -1 because we've already add the pawn 
			render();
			
			System.out.println("\nPartie gagnée par " + currentPlayer.getName() + " !");
			gameEnded = true;	
		}
		
		// if the grid is full
		possiblesX = getPossiblesX();
		if(possiblesX.isEmpty()) {
			render();
			
			System.out.println("\nPartie nulle ! La grille est remplie sans aucun puissance 4 !");
			gameEnded = true;
		}
		
		// change the turn
		this.playerOneTurn = !this.playerOneTurn;
		
		// if the game is finished
		if(gameEnded) endGame();
	}
	
	/**
	 * Render function for the game engine.
	 */
	public void render() {
		System.out.println("\n" + this);
	}
	
	/**
	 * This function ends a game for the engine.
	 */
	public void endGame() {
		this.player1 = null;
		this.player2 = null;
		this.gameIsRunning = false;
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
	 * @param x
	 * @param y
	 * @return if there's a connect 4
	 */
	public boolean checkLine(int x, int y) {
		int cpt = 1;
		boolean stopLeft = false, stopRight = false;
		Player owner = this.grid[x][y].getOwner();
		
		for (int i = 1; i <= 3 && (!stopLeft || !stopRight); i++) {
			
			// check on the left of the pawn
			if(x - i >= 0 && !stopLeft) {
				if(this.grid[x - i][y] != null && this.grid[x - i][y].getOwner().equals(owner))
					cpt++;
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
	
		return (cpt >= 4);
	}

	/**
	 * This function checks if there's a connect4 with the pawn at (x, y) in row.
	 * @param x
	 * @param y
	 * @return if there's a connect 4
	 */
	public boolean checkRow(int x, int y) {
		int cpt = 1;
		boolean stopTop = false, stopBottom = false;
		Player owner = this.grid[x][y].getOwner();
		
		for (int i = 1; i <= 3 && (!stopTop || !stopBottom); i++) {
			
			// check on the top of the pawn
			if(y - i >= 0 && !stopTop) {
				if(this.grid[x][y - i] != null && this.grid[x][y - i].getOwner().equals(owner))
					cpt++;
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
		
		return (cpt >= 4);
	}
	
	/**
	 * This function checks if there's a connect4 with the pawn at (x, y) in diagonal.
	 * @param x
	 * @param y
	 * @return if there's a connect 4
	 */
	public boolean checkDiagonal(int x, int y) {
		int cpt = 1;
		boolean stopTopLeft = false, stopBottomRight = false;
		Player owner = this.grid[x][y].getOwner();
		
		for (int i = 1; i <= 3 && (!stopTopLeft || !stopBottomRight); i++) {
			
			// check on the top left of the pawn
			if(x - i >= 0 && y - i >= 0 && !stopTopLeft) {
				if(this.grid[x - i][y - i] != null && this.grid[x - i][y - i].getOwner().equals(owner))
					cpt++;
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
		
		return (cpt >= 4);
	}
	
	/**
	 * This function checks if there's a connect4 with the pawn at (x, y) in the reversed diagonal.
	 * @param x
	 * @param y
	 * @return if there's a connect 4
	 */
	public boolean checkReversedDiagonal(int x, int y) {
		int cpt = 1;
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
				if(this.grid[x + i][y - i] != null && this.grid[x + i][y - i].getOwner().equals(owner))
					cpt++;
				else
					stopTopRight = true;
			}
		}
		
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
	 * Add a pawn at this x.
	 * @param x: the x value for the grid
	 * @param pawn: the pawn
	 */
	public void addPawn(int x, Pawn pawn) {
		// see if possible
		if(!getPossiblesX().contains(x))
			return;
		
		// add it
		this.grid[x][getYWithX(x)] = pawn;
	}
	
	/**
	 * This function returns the x that are possibles to play.
	 * @return the x that are possible (from 0 to ... (the indexes)).
	 */
	private List<Integer> getPossiblesX() {
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
				else
					result += grid[i][j].getOwner().getPawnLabel();
					
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
	 * Getter to know if a game is running.
	 * @return: if a game is running
	 */
	public boolean isGameRunning() {
		return gameIsRunning;
	}
}
