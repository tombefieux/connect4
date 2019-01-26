package Connect4;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import account.AccountManager;
import engine.GameEngine;
import engine.GameEngineOnline;
import entity.Player;
import graphics.MainWindow;
import graphics.Menu;

public class Connect4 {
	
	// create what we need
	public static Menu menu;
	public static AccountManager accountManager;
	public static MainWindow window;
	
	public static boolean anEngineIsRunning = false;
	
        
        public static void startAGameWithOnePlayer(Player player1)
        {
            GameEngine engine = new GameEngine(Config.GRID_WIDTH, Config.GRID_HEIGHT);
            window.switchToPanel(engine);
            engine.startSolo(player1);
            
            // start the game in a thread
            runAnEngine(engine);
        }
        
	/**
	 * This function starts a game with two players.
	 * @param player1: the first player.
	 * @param player2: the second player.
	 */
	public static void startAGameWithTwoPlayers(Player player1, Player player2) {
		GameEngine engine = new GameEngine(Config.GRID_WIDTH, Config.GRID_HEIGHT);
		window.switchToPanel(engine);
		engine.start(player1, player2);
		
		// start the game in a thread
		runAnEngine(engine);
	}
	
	/**
	 * This function run the server to host a game.
	 * @param localPlayer: the local player.
	 */
	public static void startToHostAGame(Player localPlayer) {
		GameEngineOnline engine;
		try {
			engine = new GameEngineOnline(Config.GRID_WIDTH, Config.GRID_HEIGHT);
			
			window.switchToPanel(engine);
			
			((GameEngineOnline) engine).start(localPlayer);
			
			// start the server in a thread
			runAnEngine(engine);
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Impossible d'héberger une partie.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * This function go to an hosted game.
	 * @param localPlayer: the local player.
	 */
	public static void goToAnHostedGame(Player localPlayer, String host) {
		GameEngineOnline engine;
		try {
			engine = new GameEngineOnline(Config.GRID_WIDTH, Config.GRID_HEIGHT, host);
			
			window.switchToPanel(engine);
			
			((GameEngineOnline) engine).start(localPlayer);
			
			// start the server in a thread
			runAnEngine(engine);
			
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(new JFrame(), "L'hôte que vous avez renseigné n'héberge pas de partie.", "Erreur", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), "Impossible de rejoindre la partie.", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * This function runs an engine.
	 * @param engine: the engine to run.
	 */
	private static void runAnEngine(GameEngine engine) {
		if(anEngineIsRunning)
			return;
		
		anEngineIsRunning = true;
		
		Thread t = new Thread() {
			public void run() {
				while(!engine.isStopped()) {
					engine.update();
					
					try {
						Thread.sleep(1000 / (long) Config.gameFPS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				anEngineIsRunning = false;
				window.switchToPanel(menu);
			}
		};
		t.start();
	}

	/**
	 * Main function.
	 * @param args
	 */
	public static void main(String[] args) {
		// we set the menu as panel to draw
		accountManager = new AccountManager();
		menu = new Menu();
		window = new MainWindow();
		
		window.switchToPanel(menu);
		window.setVisible(true);
	}

}
