package entity;

/**
 * Class that represents a pawn in the game.
 */
public class Pawn {
		
	private Player owner;			/** The owner of the pawn. */
	private boolean highlighted; 	/** If the pawn is highlighted or not. */
	
	/**
	 * Constructor.
	 * @param owner: the owner of the pawn
	 */
	public Pawn(Player owner) {
		super();
		this.owner = owner;
		this.highlighted = false;
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

	/**
	 * Getter for the highlight.
	 * @return
	 */
	public boolean isHighlighted() {
		return highlighted;
	}

	/**
	 * Setter for the highlight
	 * @param highlighted: the new value
	 */
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
}
