package account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;

import Connect4.Config;
import entity.PawnName;
import entity.Player;

/**
 * This class allows to manage easily the different accounts saved.
 */
public class AccountManager {
	
	private List<Account> accounts;		/** The list of the accounts saved. */
	private Account connectedAccount;	/** The account currently logged. */
	
	/**
	 * The constructor.
	 */
	public AccountManager() {
		this.connectedAccount = null;
		loadAccounts();
	}
	
	/**
	 * This function logs-in an account. This account is now set as the current one in the manager.
	 * @param login: the login of the account
	 * @param password: the password of the account (not encrypted)
	 * @return if the account is logged-in or not
	 */
	public boolean connectToAccount(String login, String password) {
		Account account = getAccountByLogin(login);
		if(account == null)
			return false;
		
		if(getMD5Hash(password).equals(account.getPassword())) {
			this.connectedAccount = account;
			return true;
		}
		
		return false;	
	}
	
	/**
	 * This function create a new account.
	 * NOTE: the created account is automatically logged in the manager.
	 * @param login: the login
	 * @param password: the password (not encrypted)
	 */
	public void createNewAccount(String login, String password) {
		this.accounts.add(new Account(login, getMD5Hash(password), 0, PawnName.BasicPawn1));
	}
	
	/**
	 * This function returns all the logins known by the manager.
	 * @return the logins
	 */
	public List<String> getLogins() {
		List<String> logins = new ArrayList<String>();
		for(int i = 0; i < this.accounts.size(); i++)
			logins.add(this.accounts.get(i).getLogin());
		return logins;
	}
	
	/**
	 * This function returns the account with this login or null if it doesn't exist.
	 * @param login: the login of the account
	 * @return the account or null
	 */
	private Account getAccountByLogin(String login) {
		Account account = null;
		
		for(int i = 0; i < this.accounts.size() && account == null; i++)
			if(this.accounts.get(i).getLogin().equals(login))
				account = this.accounts.get(i);
		
		return account;
	}
	
	/**
	 * This function loads all the accounts, using the serialization.
	 */
	@SuppressWarnings("unchecked") // delete the warning
	private void loadAccounts() {
		try {
            ObjectInputStream file =  new ObjectInputStream (new FileInputStream(new File(Config.savingPath + Config.AccountsSerialFileName)));
            this.accounts = (ArrayList<Account>) file.readObject();
            file.close();
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(new JFrame(), "Erreur lors de la lecture des comptes en ligne, impossible de les charger.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	/**
	 * This function saves the accounts with serialization.
	 */
	public void saveAccounts() {
		try {
			ObjectOutputStream file =  new ObjectOutputStream (new FileOutputStream(new File(Config.savingPath + Config.AccountsSerialFileName)));
            file.writeObject(this.accounts);
            file.close();
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(new JFrame(), "Erreur lors de la sauvegarde des comptes en ligne.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	/**
	 * This function returns a player object for the engine basing on the current logged account.
	 * @return the player or null if nobody is logged-in
	 */
	public Player getPlayerWithTheCurrentAccount() {
		if(this.connectedAccount == null)
			return null;
		
		return new Player(this.connectedAccount.getLogin(), this.connectedAccount.getPawnName());
	}

	/**
	 * Getter of the currently logged account.
	 * @return the account
	 */
	public Account getConnectedAccount() {
		return this.connectedAccount;
	}
	
	/**
	 * This function returns the text parameter hashed in MD5.
	 * @param text: the text to hash
	 * @return the hashed text
	 */
	public static String getMD5Hash(String text) {
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
		    byte[] digest = md.digest();
		    
		    return DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    
	    return "";
	}
	
}
