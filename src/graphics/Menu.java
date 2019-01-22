package graphics;

import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
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
	private JPanel firstPanel;						/** The first panel of the menu. */
	private JPanel twoPlayersPanel; 				/** The panel when we choose 2 players. */
	private JPanel selectOnlineModePanel;			/** The panel to select the online mode. */
	
	// elements
	private JTextField tfPlayer1Name;				/** The text field to give the name of the player 1. */
	private JTextField tfPlayer2Name;				/** The text field to give the name of the player 2. */
	private JTextField tfHostName;					/** The text field to give the name of the host. */
	

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
		buildSelectOnlineModePanel();
		
		// add the panels
		this.setLayout(new CardLayout());
		this.add("firstPanel", this.firstPanel);
		this.add("twoPlayersPanel", this.twoPlayersPanel);
		this.add("selectOnlineModePanel", this.selectOnlineModePanel);
		
		// set the first panel by default
		((CardLayout)this.getLayout()).show(this, "firstPanel");
	}
	
	/**
	 * This function builds the first panel.
	 */
	private void buildFirstPanel() {
		this.firstPanel = new JPanel();
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
		this.twoPlayersPanel = new JPanel();
		this.twoPlayersPanel.setPreferredSize(this.getSize());
		this.twoPlayersPanel.setLayout(null);
		
		// create the inputs
		Button btRetour = new Button("Retour au menu");
		btRetour.setBounds(20, 20, 150, 30);
		btRetour.addActionListener(this);
		
		Label lbP1 = new Label("Nom du joueur 1 :");
		lbP1.setBounds(550, 100, 250, 25);
		
		this.tfPlayer1Name = new JTextField();
		this.tfPlayer1Name.setBounds(550, 130, 250, 25);
		this.tfPlayer1Name.setText("Joueur1");
		
		Label lbP2 = new Label("Nom du joueur 2 :");
		lbP2.setBounds(550, 170, 250, 25);
		
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
	 * This function build the panel to select the online mode.
	 */
	private void buildSelectOnlineModePanel() {
		this.selectOnlineModePanel = new JPanel();
		this.selectOnlineModePanel.setPreferredSize(this.getSize());
		this.selectOnlineModePanel.setLayout(null);
		
		// create the inputs
		Button btRetour = new Button("Retour au menu");
		btRetour.setBounds(20, 20, 150, 30);
		btRetour.addActionListener(this);
		
		// TODO: display IP when we host a game
		Button btHost = new Button("Héberger une partie");
		btHost.setBounds(550, 100, 250, 40);
		btHost.addActionListener(this);
		
		Label lbP1 = new Label("Adresse IP de l'host :");
		lbP1.setBounds(550, 180, 250, 25);
		
		this.tfHostName = new JTextField();
		this.tfHostName.setBounds(550, 210, 250, 25);
		
		Button btPlay = new Button("Rejoindre la partie");
		btPlay.setBounds(550, 250, 250, 40);
		btPlay.addActionListener(this);
		
		// add them
		this.selectOnlineModePanel.add(btRetour);
		this.selectOnlineModePanel.add(btHost);
		this.selectOnlineModePanel.add(lbP1);
		this.selectOnlineModePanel.add(this.tfHostName);
		this.selectOnlineModePanel.add(btPlay);
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
			((CardLayout)this.getLayout()).show(this, "selectOnlineModePanel");
		
		// host a game 
		else if(button.getLabel().equals("Héberger une partie")) {
			// TODO: get the local player with the account
			Connect4.startToHostAGame(new Player("Foo", PawnName.BasicPawn1));
		}
		
		// play on an hosted game 
		else if(button.getLabel().equals("Rejoindre la partie")) {
			// TODO: get the local player with the account
			Connect4.startToHostAGame(new Player("Foo", PawnName.BasicPawn1), this.tfHostName.getText());
		}
	}
	
}
