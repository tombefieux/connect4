package entity;

/**
 * Class that represents a pawn in the game.
 */
public class Pawn {
	
	private Player owner;	/** The owner of the pawn. */
	
	/**
	 * Constructor.
	 * @param owner: the owner of the pawn
	 */
	public Pawn(String label, Player owner) {
		super();
		this.owner = owner;
	}

	/**
	 * Getter for the owner.
	 * @return
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Setter for the owner
	 * @param owner: the new owner
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	
	
}
