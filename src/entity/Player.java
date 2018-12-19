package entity;

/**
 * Class that represents a player in the game.
 */
public class Player {
	
	private String name; 		/** The name of the player. */
	private String pawnLabel; 	/** The label for the pawns of the player */

	
	/**
	 * The constructor.
	 * @param name: its name
	 */
	public Player(String name, String pawnLabel) {
		super();
		this.name = name;
		this.pawnLabel = pawnLabel;
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
	public String getPawnLabel() {
		return pawnLabel;
	}

	/**
	 * Setter for the pawn label.
	 * @param pawnLabel: the new pawn label
	 */
	public void setPawnLabel(String pawnLabel) {
		this.pawnLabel = pawnLabel;
	}
	
	
	
}
