package entity;


import Connect4.Connect4;

/**
 * Class that represents a player in the game.
 */
public class Player {
	
	private String name; 		/** The name of the player. */
	private char pawnLabel; 	/** The label for the pawns of the player */

	
	/**
	 * The constructor.
	 * @param name: its name
	 */
	public Player(String name, char pawnLabel) {
		super();
		this.name = name;
		this.pawnLabel = pawnLabel;
	}
	
	/**
	 * This function returns the x that the player want to play.
	 * @return the x to play.
	 */
	public int play() {
		int result =  Connect4.sc.nextInt();

		return result - 1; // because we use indexes
	}
	
	/**
	 * Returns if two players are equals.
	 * @return if they're equals
	 */
	public boolean equals(Object player) {
		return (this.name.equals(((Player) player).name));
	}

	/**
	 * Getter on the name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the name.
	 * @param name: the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter on the pawnLabel.
	 * @return: the pawnLabel
	 */
	public char getPawnLabel() {
		return pawnLabel;
	}

	/**
	 * Setter for the pawn label.
	 * @param pawnLabel: the new pawn label
	 */
	public void setPawnLabel(char pawnLabel) {
		this.pawnLabel = pawnLabel;
	}
}
