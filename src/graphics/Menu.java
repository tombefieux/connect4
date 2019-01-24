package graphics;

import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Connect4.Config;
import Connect4.Connect4;
import entity.PawnName;
import entity.Player;

/**
 * This class represents the menu of the game.
 */
public class Menu extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	// panels
	private MenuPanel firstPanel;						/** The first panel of the menu. */
	private MenuPanel twoPlayersPanel; 				/** The panel when we choose 2 players. */
	private MenuPanel connectionPanel;					/** The panel to select an online account. */
	private MenuPanel createAccountPanel;				/** The panel to create an account. */
	private MenuPanel selectOnlineModePanel;			/** The panel to select the online mode. */
	
	// elements
	private JTextField tfPlayer1Name;				/** The text field to give the name of the player 1. */
	private JTextField tfPlayer2Name;				/** The text field to give the name of the player 2. */
	private JComboBox<Object> cbAccount;			/** The combo box to select an online account. */
	private JPasswordField tfPassword;				/** The text field for the password of the account. */
	private JTextField tfHostName;					/** The text field to give the name of the host. */
	private JTextField tfLoginCA;					/** The login in the create account panel. */
	private JPasswordField tfPasswordCA;			/** The password in the create account panel. */
	private JPasswordField tfPasswordConfCA;		/** The confirmation of the password in the create account panel. */
	private JLabel nbPoint;							/** The JLabel of the number of points. */	

	/**
	 * Constructor.
	 */
	public Menu() {
		super();
		
		setSize(Config.windowWidth, Config.windowHeight);
		setFocusable(true);
		
		buildMenu();
	}
	
	/**
	 * This function builds the panels.
	 */
	private void buildMenu() {
		
		// build all the panel
		buildFirstPanel();
		buildTwoPlayersPanel();
		buildConnectionPanel();
		buildCreateAccountPanel();
		buildSelectOnlineModePanel();
		
		// add the panels
		this.setLayout(new CardLayout());
		this.add("firstPanel", this.firstPanel);
		this.add("twoPlayersPanel", this.twoPlayersPanel);
		this.add("connectionPanel", this.connectionPanel);
		this.add("createAccountPanel", this.createAccountPanel);
		this.add("selectOnlineModePanel", this.selectOnlineModePanel);
		
		// set the first panel by default
		((CardLayout)this.getLayout()).show(this, "firstPanel");
	}
	
	/**
	 * This function builds the first panel.
	 */
	private void buildFirstPanel() {
		this.firstPanel = new MenuPanel();
		this.firstPanel.setPreferredSize(this.getSize());
		this.firstPanel.setLayout(null);

		// create the buttons
		Button bt1Player = new Button("Un joueur");
		bt1Player.setBounds(550, 100, 250, 40);
		bt1Player.addActionListener(this);
		Button bt2Player = new Button("Deux joueurs");
		bt2Player.setBounds(550, 200, 250, 40);
		bt2Player.addActionListener(this);
		Button btNetwork = new Button("Partie en ligne");
		btNetwork.setBounds(550, 300, 250, 40);
		btNetwork.addActionListener(this);
		
		// add them
		this.firstPanel.add(bt1Player);
		this.firstPanel.add(bt2Player);
		this.firstPanel.add(btNetwork);
	}
	
	/**
	 * This function builds the two players panel.
	 */
	public void buildTwoPlayersPanel() {
		this.twoPlayersPanel = new MenuPanel();
		this.twoPlayersPanel.setPreferredSize(this.getSize());
		this.twoPlayersPanel.setLayout(null);
		
		// create the inputs
		Button btRetour = new Button("Retour au menu");
		btRetour.setBounds(20, 20, 150, 30);
		btRetour.addActionListener(this);
		
		JLabel lbP1 = new JLabel("Nom du joueur 1 :");
		lbP1.setBounds(550, 100, 250, 25);
		lbP1.setForeground(new Color(253, 238, 215));
		lbP1.setOpaque(false);
		
		this.tfPlayer1Name = new JTextField();
		this.tfPlayer1Name.setBounds(550, 130, 250, 25);
		this.tfPlayer1Name.setText("Joueur1");
		
		JLabel lbP2 = new JLabel("Nom du joueur 2 :");
		lbP2.setBounds(550, 170, 250, 25);
		lbP2.setForeground(new Color(253, 238, 215));
		lbP2.setOpaque(false);
		
		this.tfPlayer2Name = new JTextField();
		this.tfPlayer2Name.setBounds(550, 200, 250, 25);
		this.tfPlayer2Name.setText("Joueur2");
		
		Button btPlay = new Button("Jouer !");
		btPlay.setBounds(550, 350, 250, 40);
		btPlay.addActionListener(this);
		
		// add them
		this.twoPlayersPanel.add(btRetour);
		this.twoPlayersPanel.add(lbP1);
		this.twoPlayersPanel.add(this.tfPlayer1Name);
		this.twoPlayersPanel.add(lbP2);
		this.twoPlayersPanel.add(this.tfPlayer2Name);
		this.twoPlayersPanel.add(btPlay);
	}
	
	/**
	 * This function builds the connection panel.
	 */
	private void buildConnectionPanel() {
		this.connectionPanel = new MenuPanel();
		this.connectionPanel.setPreferredSize(this.getSize());
		this.connectionPanel.setLayout(null);
		
		// create the inputs
		Button btRetour = new Button("Retour au menu");
		btRetour.setBounds(20, 20, 150, 30);
		btRetour.addActionListener(this);
		
		JLabel lbP1 = new JLabel("Choisissez un compte multijoueur :");
		lbP1.setBounds(550, 100, 250, 25);
		lbP1.setForeground(new Color(253, 238, 215));
		lbP1.setOpaque(false);
		
		this.cbAccount = new JComboBox<Object>(Connect4.accountManager.getLogins().toArray());
		this.cbAccount.setBounds(550, 130, 250, 25);
		
		JLabel lbMdp = new JLabel("Mot de passe du compte :");
		lbMdp.setBounds(550, 160, 250, 25);
		lbMdp.setForeground(new Color(253, 238, 215));
		lbMdp.setOpaque(false);
		
		this.tfPassword = new JPasswordField();
		this.tfPassword.setBounds(550, 190, 250, 25);
		
		Button btCo = new Button("Se connecter");
		btCo.setBounds(550, 230, 250, 30);
		btCo.addActionListener(this);
		
		Button btNew = new Button("Créer un compte");
		btNew.setBounds(550, 300, 250, 30);
		btNew.addActionListener(this);
		
		this.connectionPanel.add(btRetour);
		this.connectionPanel.add(lbP1);
		this.connectionPanel.add(this.cbAccount);
		this.connectionPanel.add(lbMdp);
		this.connectionPanel.add(tfPassword);
		this.connectionPanel.add(btCo);
		this.connectionPanel.add(btNew);
	}
	
	/**
	 * This function builds the panel to create an account.
	 */
	private void buildCreateAccountPanel() {
		this.createAccountPanel = new MenuPanel();
		this.createAccountPanel.setPreferredSize(this.getSize());
		this.createAccountPanel.setLayout(null);
		
		// create the inputs
		Button btRetour = new Button("Retour au menu multijoueur");
		btRetour.setBounds(20, 20, 200, 30);
		btRetour.addActionListener(this);
		
		JLabel lbP1 = new JLabel("Login :");
		lbP1.setBounds(550, 100, 250, 25);
		lbP1.setForeground(new Color(253, 238, 215));
		lbP1.setOpaque(false);
		
		this.tfLoginCA = new JTextField();
		this.tfLoginCA.setBounds(550, 125, 250, 25);
		
		JLabel lbMdp = new JLabel("Mot de passe :");
		lbMdp.setBounds(550, 155, 250, 25);
		lbMdp.setForeground(new Color(253, 238, 215));
		lbMdp.setOpaque(false);
		
		this.tfPasswordCA = new JPasswordField();
		this.tfPasswordCA.setBounds(550, 180, 250, 25);
		
		JLabel lbMdpConf = new JLabel("Confirmez le mot de passe :");
		lbMdpConf.setBounds(550, 210, 250, 25);
		lbMdpConf.setForeground(new Color(253, 238, 215));
		lbMdpConf.setOpaque(false);
		
		this.tfPasswordConfCA = new JPasswordField();
		this.tfPasswordConfCA.setBounds(550, 235, 250, 25);
		
		Button btCreate = new Button("Créer");
		btCreate.setBounds(550, 300, 250, 30);
		btCreate.addActionListener(this);
		
		// add them
		this.createAccountPanel.add(btRetour);
		this.createAccountPanel.add(lbP1);
		this.createAccountPanel.add(this.tfLoginCA);
		this.createAccountPanel.add(lbMdp);
		this.createAccountPanel.add(this.tfPasswordCA);
		this.createAccountPanel.add(lbMdpConf);
		this.createAccountPanel.add(this.tfPasswordConfCA);
		this.createAccountPanel.add(btCreate);
	}
	
	/**
	 * This function builds the panel to select the online mode.
	 */
	private void buildSelectOnlineModePanel() {
		this.selectOnlineModePanel = new MenuPanel();
		this.selectOnlineModePanel.setPreferredSize(this.getSize());
		this.selectOnlineModePanel.setLayout(null);
		
		// create the inputs
		Button btRetour = new Button("Retour au menu multijoueur");
		btRetour.setBounds(20, 20, 200, 30);
		btRetour.addActionListener(this);
		
		this.nbPoint = new JLabel();
		this.nbPoint.setBounds(20, 60, 400, 25);
		this.nbPoint.setFont(this.nbPoint.getFont().deriveFont(22.f));
		this.nbPoint.setOpaque(false);
		
		Button btHost = new Button("Héberger une partie");
		btHost.setBounds(550, 100, 250, 40);
		btHost.addActionListener(this);
		
		JLabel lbP1 = new JLabel("Adresse IP de l'host :");
		lbP1.setBounds(550, 160, 250, 25);
		lbP1.setForeground(new Color(253, 238, 215));
		lbP1.setOpaque(false);
		
		this.tfHostName = new JTextField();
		this.tfHostName.setBounds(550, 190, 250, 25);
		
		Button btPlay = new Button("Rejoindre la partie");
		btPlay.setBounds(550, 230, 250, 40);
		btPlay.addActionListener(this);
		
		Button btShop = new Button("Changer de pion");
		btShop.setBounds(550, 290, 250, 40);
		btShop.addActionListener(this);
		
		Button btDel = new Button("Supprimer ce compte");
		btDel.setBounds(550, 380, 250, 40);
		btDel.addActionListener(this);
		
		// add them
		this.selectOnlineModePanel.add(btRetour);
		this.selectOnlineModePanel.add(btHost);
		this.selectOnlineModePanel.add(lbP1);
		this.selectOnlineModePanel.add(this.tfHostName);
		this.selectOnlineModePanel.add(btPlay);
		this.selectOnlineModePanel.add(btShop);
		this.selectOnlineModePanel.add(btDel);
		this.selectOnlineModePanel.add(this.nbPoint);
	}

	/**
	 * This function is triggered by the buttons of the menu.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Button button = ((Button) e.getSource());
		
		// -- in the first panel
		// two players
		if(button.getLabel().equals("Deux joueurs"))
			((CardLayout)this.getLayout()).show(this, "twoPlayersPanel");
		
		// menu
		else if (button.getLabel().equals("Retour au menu"))
			((CardLayout)this.getLayout()).show(this, "firstPanel");
		
		// play with two players
		else if (button.getLabel().equals("Jouer !")) {
			String player1Name;
			if(this.tfPlayer1Name.getText().isEmpty())
				player1Name = "Joueur1";
			else if(this.tfPlayer1Name.getText().length() > 20)
				player1Name = this.tfPlayer1Name.getText().substring(0, 20);
			else
				player1Name = this.tfPlayer1Name.getText();
			
			String player2Name;
			if(this.tfPlayer2Name.getText().isEmpty())
				player2Name = "Joueur2";
			else if(this.tfPlayer2Name.getText().length() > 20)
				player2Name = this.tfPlayer2Name.getText().substring(0, 20);
			else
				player2Name = this.tfPlayer2Name.getText();
			if(player2Name.equals(player1Name))
				player2Name += "(2)";
			
			Connect4.startAGameWithTwoPlayers(new Player(player1Name, PawnName.BasicPawn1), new Player(player2Name, PawnName.BasicPawn2));
		}
		
		// go to online
		else if (button.getLabel().equals("Partie en ligne"))
			((CardLayout)this.getLayout()).show(this, "connectionPanel");
		
		// create an account
		else if (button.getLabel().equals("Créer un compte"))
			((CardLayout)this.getLayout()).show(this, "createAccountPanel");
		
		// connection to account
		else if (button.getLabel().equals("Se connecter")) {
			if(this.cbAccount.getSelectedItem() == null)
	        	JOptionPane.showMessageDialog(this, "Veuillez selectionner un compte pour jouer en ligne.", "Erreur", JOptionPane.ERROR_MESSAGE);
			else if(Connect4.accountManager.connectToAccount(this.cbAccount.getSelectedItem().toString(), this.tfPassword.getPassword())) {
				this.tfPassword.setText("");
				((CardLayout)this.getLayout()).show(this, "selectOnlineModePanel");
				refreshNbPoints();
			}
		}
		
		// create an account
		else if (button.getLabel().equals("Créer")) {
			if(this.tfLoginCA.getText().isEmpty())
				JOptionPane.showMessageDialog(this, "Veuillez rentrer un login.", "Erreur", JOptionPane.ERROR_MESSAGE);
			else {
				if(this.tfPasswordCA.getPassword().length < 6)
					JOptionPane.showMessageDialog(this, "Veuillez rentrer un mot de passe d'au moins 6 caractères.", "Erreur", JOptionPane.ERROR_MESSAGE);
				else {
					if (!(new String(this.tfPasswordConfCA.getPassword()).equals(new String(this.tfPasswordCA.getPassword()))))
						JOptionPane.showMessageDialog(this, "Les deux mots de passes ne sont pas identiques.", "Erreur", JOptionPane.ERROR_MESSAGE);
					else if (Connect4.accountManager.createNewAccount(this.tfLoginCA.getText(), this.tfPasswordCA.getPassword())) {
						refreshCBAccount();
						this.tfLoginCA.setText("");
						this.tfPasswordCA.setText("");
						this.tfPasswordConfCA.setText("");
						((CardLayout)this.getLayout()).show(this, "selectOnlineModePanel");
						refreshNbPoints();
					}
				}
			}
		}
		
		// return to online menu
		else if (button.getLabel().equals("Retour au menu multijoueur"))
			((CardLayout)this.getLayout()).show(this, "connectionPanel");
				
		// host a game 
		else if(button.getLabel().equals("Héberger une partie"))
			Connect4.startToHostAGame(Connect4.accountManager.getPlayerWithTheCurrentAccount());
		
		// play on an hosted game 
		else if(button.getLabel().equals("Rejoindre la partie"))
			Connect4.goToAnHostedGame(Connect4.accountManager.getPlayerWithTheCurrentAccount(), this.tfHostName.getText());
		
		// delete current account
		else if (button.getLabel().equals("Supprimer ce compte")) {
			if(JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer définitivement ce compte ?", "Attention !", JOptionPane.YES_NO_OPTION) == 0) {
				Connect4.accountManager.deleteCurrentAccount();
				refreshCBAccount();
				((CardLayout)this.getLayout()).show(this, "connectionPanel");
			}
		}
	}
	
	/**
	 * This function refresh the combo box with the accounts.
	 */
	public void refreshCBAccount() {
		this.cbAccount.removeAllItems();
		for(String s: Connect4.accountManager.getLogins())
			this.cbAccount.addItem(s);
	}
	
	/**
	 * This function refresh the JLabel of number of points with the current account. 
	 */
	public void refreshNbPoints() {
		this.nbPoint.setText("Nombre de points : " + Connect4.accountManager.getConnectedAccount().getPoints());
	}
}
