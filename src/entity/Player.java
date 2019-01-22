package entity;

/**
 * Class that represents a player in the game.
 */
public class Player {
	
	private String name; 		/** The name of the player. */
	private PawnName pawnName;	/** The name of the pawn. */
	
	/**
	 * The constructor.
	 * @param name: its name
	 * @param pawnName: the name of his pawns
	 */
	public Player(String name, PawnName pawnName) {
		super();
		this.name = name;
		this.pawnName = pawnName;
	}
	
	/**
	 * Returns if two players are equals.
	 * @return if they're equals
	 */
	public boolean equals(Object player) {
		return (this.name.equals(((Player) player).name));
	}

	/**
	 * Getter of the name.
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
	 * Getter of the pawn name.
	 * @return the pawn name
	 */
	public PawnName getPawnName() {
		return pawnName;
	}

	/**
	 * Setter for the pawn name.
	 * @param name: the new pawn name
	 */
	public void setPawnName(PawnName pawnName) {
		this.pawnName = pawnName;
	}
}
