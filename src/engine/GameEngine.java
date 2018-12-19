package engine;

import java.util.ArrayList;
import java.util.List;

import entity.Pawn;
import entity.Player;

/**
 * This class represents the game engine of the connect 4.
 * It manages the games and the win and loose system. 
 */
public class GameEngine {

	private Pawn[][] grid;		/** The grid of the connect 4 */
	private int width;			/** The width of the grid */
	private int height;			/** The height of the grid */
	private Player player1;		/** The first player */
	private Player player2;		/** The second player */
	
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
		int y = 0;
		while (y < height && this.grid[x][y] == null)
			y++;
		
		if(y != 0)
			y--;
		this.grid[x][y] = pawn;
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
	 * Render function for the game engine.
	 */
	public void render() {
		System.out.println(this + "\n");
	}
}
