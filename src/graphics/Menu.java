package graphics;

import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
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
	private MenuPanel twoPlayersPanel; 					/** The panel when we choose 2 players. */
	private MenuPanel connectionPanel;					/** The panel to select an online account. */
	private MenuPanel createAccountPanel;				/** The panel to create an account. */
	private MenuPanel selectOnlineModePanel;			/** The panel to select the online mode. */
	
	// elements
	private JTextField tfPlayer1Name;					/** The text field to give the name of the player 1. */
	private JTextField tfPlayer2Name;					/** The text field to give the name of the player 2. */
	private JComboBox<Object> cbAccount;				/** The combo box to select an online account. */
	private JPasswordField tfPassword;					/** The text field for the password of the account. */
	private JTextField tfHostName;						/** The text field to give the name of the host. */
	private JTextField tfLoginCA;						/** The login in the create account panel. */
	private JPasswordField tfPasswordCA;				/** The password in the create account panel. */
	private JPasswordField tfPasswordConfCA;			/** The confirmation of the password in the create account panel. */
	private JLabel nbPoint;								/** The JLabel of the number of points. */
	private JLabel pawnImage;							/** The selected pawn image of the online player. */
	private JLabel p1PawnImage;							/** The selected pawn image of the player 1. */
	private JLabel p2PawnImage;							/** The selected pawn image of the player 2. */
	
	private PawnName player1Pawn = PawnName.BasicPawn1;			/** The pawn for the player 1. */
	private PawnName player2Pawn = PawnName.BasicPawn2;			/** The pawn for the player 2. */
	private boolean pawnSelectionForPlayer1 = true;				/** If we want to select the pawn of the player 1 or not. */
	private boolean pawnSelectionForOnline = false;				/** If we choose the pawn for the online mode. */
	

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
		this.add("selectPawnPanel", Connect4.selector);
		
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
		
		JLabel lbPawn1 = new JLabel("Pion sélectionné pour J1 :");
		lbPawn1.setBounds(20, 65, 400, 25);
		lbPawn1.setFont(lbPawn1.getFont().deriveFont(22.f));
		lbPawn1.setOpaque(false);
		
		JLabel lbPawn2 = new JLabel("Pion sélectionné pour J2 :");
		lbPawn2.setBounds(20, 135, 400, 25);
		lbPawn2.setFont(lbPawn1.getFont());
		lbPawn2.setOpaque(false);
		
		JLabel lbP1 = new JLabel("Nom du joueur 1 :");
		lbP1.setBounds(550, 110, 250, 25);
		lbP1.setForeground(new Color(253, 238, 215));
		lbP1.setOpaque(false);
		
		this.tfPlayer1Name = new JTextField();
		this.tfPlayer1Name.setBounds(550, 135, 250, 25);
		this.tfPlayer1Name.setText("Joueur1");
		
		Button btSelecPawn1 = new Button("Changer le pion de J1");
		btSelecPawn1.setBounds(550, 165, 250, 30);
		btSelecPawn1.addActionListener(this);
		
		JLabel lbP2 = new JLabel("Nom du joueur 2 :");
		lbP2.setBounds(550, 205, 250, 25);
		lbP2.setForeground(new Color(253, 238, 215));
		lbP2.setOpaque(false);
		
		this.tfPlayer2Name = new JTextField();
		this.tfPlayer2Name.setBounds(550, 230, 250, 25);
		this.tfPlayer2Name.setText("Joueur2");
		
		Button btSelecPawn2 = new Button("Changer le pion de J2");
		btSelecPawn2.setBounds(550, 260, 250, 30);
		btSelecPawn2.addActionListener(this);
		
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
		this.twoPlayersPanel.add(lbPawn1);
		this.twoPlayersPanel.add(lbPawn2);
		this.twoPlayersPanel.add(btSelecPawn1);
		this.twoPlayersPanel.add(btSelecPawn2);
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
		this.nbPoint.setBounds(20, 80, 400, 25);
		this.nbPoint.setFont(this.nbPoint.getFont().deriveFont(22.f));
		this.nbPoint.setOpaque(false);
		
		JLabel lbPawn = new JLabel("Pion sélectionné :");
		lbPawn.setBounds(20, 130, 250, 25);
		lbPawn.setFont(this.nbPoint.getFont().deriveFont(22.f));
		lbPawn.setOpaque(false);
		
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
		this.selectOnlineModePanel.add(lbPawn);
	}

	/**
	 * This function is triggered by the buttons of the menu.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Button button = ((Button) e.getSource());
		
		// -- in the first panel
                
                //Solo player
                 if(button.getLabel().equals("Un joueur"))
                {
                    Connect4.startAGameWithOnePlayer(new Player("Vous", PawnName.BasicPawn1));
                }
                
		// two players
		if(button.getLabel().equals("Deux joueurs")) {
			refreshPlayersPanel();
			((CardLayout)this.getLayout()).show(this, "twoPlayersPanel");
		}
		
		// menu
		else if (button.getLabel().equals("Retour au menu"))
			((CardLayout)this.getLayout()).show(this, "firstPanel");
		
		// change player 1 pawn
		else if (button.getLabel().equals("Changer le pion de J1")) {
			// add the return button
			Button btRetour = new Button("Retour");
			btRetour.setBounds(20, 20, 100, 30);
			btRetour.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					((CardLayout)getLayout()).show(Connect4.menu, "twoPlayersPanel");
				}
			});
			Connect4.selector.removeAll();
			Connect4.selector.add(btRetour);

			// impossible pawns
			List<PawnName> pawns = new ArrayList<PawnName>();
			pawns.add(this.player2Pawn);
			
			// go select the pawn 
			this.pawnSelectionForPlayer1 = true;
			Connect4.selector.update(pawns);
			((CardLayout)this.getLayout()).show(this, "selectPawnPanel");
		}
		
		// change player 2 pawn
		else if (button.getLabel().equals("Changer le pion de J2")) {
			// add the return button
			Button btRetour = new Button("Retour");
			btRetour.setBounds(20, 20, 100, 30);
			btRetour.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					((CardLayout)getLayout()).show(Connect4.menu, "twoPlayersPanel");
				}
			});
			Connect4.selector.removeAll();
			Connect4.selector.add(btRetour);

			// impossible pawns
			List<PawnName> pawns = new ArrayList<PawnName>();
			pawns.add(this.player1Pawn);
			
			// go select the pawn 
			this.pawnSelectionForPlayer1 = false;
			Connect4.selector.update(pawns);
			((CardLayout)this.getLayout()).show(this, "selectPawnPanel");
		}
		
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
			
			Connect4.startAGameWithTwoPlayers(new Player(player1Name, player1Pawn), new Player(player2Name, player2Pawn));
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
				refreshOnlineSelectionPanel();
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
						refreshOnlineSelectionPanel();
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
		
		// change pawn
		else if(button.getLabel().equals("Changer de pion")) {
			
			// add the return button
			Button btRetour = new Button("Retour");
			btRetour.setBounds(20, 20, 100, 30);
			btRetour.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					((CardLayout)getLayout()).show(Connect4.menu, "selectOnlineModePanel");
				}
			});
			Connect4.selector.removeAll();
			Connect4.selector.add(btRetour);
			
			// get the blocked pawns
			int nbOfPanwsDeblocked = Connect4.accountManager.getConnectedAccount().getPoints() / (Config.nbOfGameToDeblockANewPawn * Config.pointsAddedWhenWin) + Config.nbOfBasicPawns;
			
			List<PawnName> pawns = new ArrayList<PawnName>();
			for (int i = PawnName.values().length - 1; i >= nbOfPanwsDeblocked; i--)
				pawns.add(PawnName.values()[i]);
			
			// go select the pawn 
			this.pawnSelectionForOnline = true;
			Connect4.selector.update(pawns);
			((CardLayout)this.getLayout()).show(this, "selectPawnPanel");
		}
		
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
	public void refreshOnlineSelectionPanel() {
		this.nbPoint.setText("Nombre de points : " + Connect4.accountManager.getConnectedAccount().getPoints());
		
		// the pawn image
		if(this.pawnImage != null)
			this.selectOnlineModePanel.remove(this.pawnImage);
		
		this.pawnImage = new JLabel(new ImageIcon(Config.getFullPathOfPawn(Connect4.accountManager.getConnectedAccount().getPawnName(), false)));
		this.pawnImage.setBounds(175, 70, 150, 150);
		this.selectOnlineModePanel.add(this.pawnImage);
	}
	
	/**
	 * This function refresh the panel of selection of the two players.
	 */
	public void refreshPlayersPanel() {
		// pawn player 1
		if(this.p1PawnImage != null)
			this.twoPlayersPanel.remove(this.p1PawnImage);
		
		this.p1PawnImage = new JLabel(new ImageIcon(Config.getFullPathOfPawn(this.player1Pawn, false)));
		this.p1PawnImage.setBounds(260, 5, 150, 150);
		this.twoPlayersPanel.add(this.p1PawnImage);
		
		// pawn player 2
		if(this.p2PawnImage != null)
			this.twoPlayersPanel.remove(this.p2PawnImage);
		
		this.p2PawnImage = new JLabel(new ImageIcon(Config.getFullPathOfPawn(this.player2Pawn, false)));
		this.p2PawnImage.setBounds(260, 75, 150, 150);
		this.twoPlayersPanel.add(this.p2PawnImage);
	}
	
	/**
	 * This function can handle the return of the selection of a pawn by the pawn selector.
	 * @param pawn
	 */
	public void handlePawnSelection(PawnName pawn) {
		if(this.pawnSelectionForOnline) {
			Connect4.accountManager.setPawnForCurrentAccount(pawn);
			refreshOnlineSelectionPanel();
			this.pawnSelectionForOnline = false;
			((CardLayout)this.getLayout()).show(this, "selectOnlineModePanel");
		}
		
		else {
			if(this.pawnSelectionForPlayer1)
				this.player1Pawn = pawn;
			else
				this.player2Pawn = pawn;
			
			refreshPlayersPanel();
			((CardLayout)getLayout()).show(Connect4.menu, "twoPlayersPanel");
		}
	}
}
