package Connect4;

import engine.GameEngine;
import engine.GameEngineOnline;
import entity.Player;
import graphics.MainWindow;
import graphics.Menu;

import java.util.Scanner;

public class Connect4 {
	
	// create what we need
	public static MainWindow window;
	public static GameEngine engine;
	public static Menu menu;
	
	/**
	 * This function starts a game with two players.
	 * @param player1: the first player.
	 * @param player2: the second player.
	 */
	public static void startAGameWithTwoPlayers(Player player1, Player player2) {
		window.switchToEngine();
		engine.start(player1, player2);
		
		// start the game in a thread
		Thread t = new Thread() {
			public void run() {
				while(engine.isGameRunning()) {
					engine.update();
					
					try {
						Thread.sleep(1000 / (long) Config.gameFPS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				window.switchToMenu();
			}
		};
		t.start();
	}

	/**
	 * Main function.
	 * @param args
	 */
	public static void main(String[] args) {
		
		// init
		engine = new GameEngine(Config.GRID_WIDTH, Config.GRID_HEIGHT);
		menu = new Menu();
		// window = new MainWindow(engine, menu);
			
		// we set the menu as panel to draw
		// window.switchToEngine();
		// window.setVisible(true);

        System.out.println("Serez vous l'host de cette partie ? (o/n)");
        Scanner sc = new Scanner(System.in);
        String result = sc.nextLine();
        if(result.equals("o"))
        {
            System.out.println("En attente d'un joueur...");
            
            // create the engine
            GameEngineOnline engineT = new GameEngineOnline(Config.GRID_WIDTH, Config.GRID_HEIGHT);
            
            window = new MainWindow(engineT, menu);
            window.switchToEngine();
    		window.setVisible(true);
            
    		engineT.start(new Player("Jacques", Config.PLAYER1_LABEL), new Player("Paulette", Config.PLAYER2_LABEL));
           
            while(engineT.isGameRunning()) {
            	engineT.update();
                
                try {
                        Thread.sleep(1000 / (long) Config.gameFPS);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
            }
        }
        else{
            GameEngineOnline engineT = new GameEngineOnline(Config.GRID_WIDTH, Config.GRID_HEIGHT, "localhost");
            
            window = new MainWindow(engineT, menu);
            window.switchToEngine();
    		window.setVisible(true);
            
    		engineT.start(new Player("Jacques", Config.PLAYER1_LABEL), new Player("Paulette", Config.PLAYER2_LABEL));
    		
             while(engineT.isGameRunning()) {
            	 engineT.update();
                
                try {
                        Thread.sleep(1000 / (long) Config.gameFPS);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
             }
        }
	}

}
