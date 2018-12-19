package entity;

/**
 * Class that represents a player in the game.
 */
public class Player {
	
	private String name; /** The name of the player. */

	
	/**
	 * The constructor.
	 * @param name: its name
	 */
	public Player(String name) {
		super();
		this.name = name;
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
	
}
