package account;

import java.io.Serializable;

import entity.PawnName;

/**
 * This class represents an account for the online mode.
 */
public class Account implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String login;			/** The login of the account. */
	private String password;		/** The password of the account. */
	private int points;				/** The number of points owned by the account. */
	private PawnName pawnName;		/** The pawn name set to the account. */

	/**
	 * The constructor.
	 * @param login: the login
	 * @param password: the password
	 * @param points: the number of points
	 * @param pawnName: the pawn name of the account
	 */
	public Account(String login, String password, int points, PawnName pawnName) {
		super();
		this.login = login;
		this.password = password;
		this.points = points;
		this.pawnName = pawnName;
	}
	
	/**
	 * Getter for login.
	 * @return: the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Setter for the login.
	 * @param login: the new login
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * Getter for the points.
	 * @return: the number of points
	 */
	public int getPoints() {
		return points;
	}
	
	/**
	 * Add points to the account.
	 * @param points: the number of points to add
	 */
	public void addPoints(int points) {
		this.points = points;
	}
	
	/**
	 * Getter for the password.
	 * @return: the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Getter of the pawn name.
	 * @return the pawn name
	 */
	public PawnName getPawnName() {
		return pawnName;
	}

	/**
	 * Setter of the pawn name.
	 * @param pawnName: the new pawn name for the account
	 */
	public void setPawnName(PawnName pawnName) {
		this.pawnName = pawnName;
	}
}
