package entity;

/**
 * Class that represents a pawn in the game.
 */
public class Pawn {
	
	private String label;	/** The label for the pawn to render with string. */
	private Player owner;	/** The owner of the pawn. */
	
	/**
	 * Constructor.
	 * @param label: the label
	 * @param owner: the owner of the pawn
	 */
	public Pawn(String label, Player owner) {
		super();
		this.label = label;
		this.owner = owner;
	}

	/**
	 * Getter for the label.
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Setter for the label
	 * @param label: the new label
	 */
	public void setLabel(String label) {
		this.label = label;
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
