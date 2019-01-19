package Connect4;

import java.util.Scanner;

import engine.GameEngine;
import entity.Player;
import graphics.MainWindow;

public class Connect4 {
	
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		
		// create the engine
		GameEngine engine = new GameEngine(Config.GRID_WIDTH, Config.GRID_HEIGHT);
		
		// create window
		MainWindow window = new MainWindow();

    	// we set the engine as the panel to draw
    	window.setCurrentPanel(engine);
        window.setVisible(true);
		
		// actualy start the game without menu
		engine.start(new Player("Jacques", Config.PLAYER1_LABEL), new Player("Paulette", Config.PLAYER2_LABEL));
		
		while(engine.isGameRunning()) {
			engine.update();
			
			try {
				Thread.sleep(1000 / (long) Config.gameFPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
