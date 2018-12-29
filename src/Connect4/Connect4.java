package Connect4;

import java.util.Scanner;

import engine.GameEngine;
import entity.Player;

public class Connect4 {
	
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		
		// create the engine and the scanner
		GameEngine engine = new GameEngine(Config.GRID_WIDTH, Config.GRID_HEIGHT);
		
		// TODO: bien dire les règles (comment jouer, dire que mettre indice colonne)
	
		boolean continueTheGame = true;
		while (continueTheGame) {
			
			// create the players
			String name;
			do {
				System.out.println("Rentrez un nom pour le joueur 1 :");
				name = sc.nextLine();
			} while (name.isEmpty());
			Player player1 = new Player(name, Config.PLAYER1_LABEL);
			
			System.out.println("");
			do {
				System.out.println("Rentrez un nom pour le joueur 2 :");
				name = sc.nextLine();
				
				if(name.equals(player1.getName()))
					System.out.println("Merci de choisir un nom différent du premier.\n");
				
			} while(name.isEmpty() || name.equals(player1.getName()));
			Player player2 = new Player(name, Config.PLAYER2_LABEL);
			
			// start the game!
			engine.start(player1, player2);
			
			// play
			engine.render();
			while(engine.isGameRunning()) {
				engine.newTurn();
				engine.render();
			}
			
			// ask if we want to start a new game
			 String result = sc.nextLine(); // fix scanner bug
			do {
				System.out.println("\nVoulez-vous recommencer ? (o/n)");
				result = sc.nextLine();
			} while(!result.equals("o") && !result.equals("n"));
			
			continueTheGame = result.equals("o");
		}
		
		sc.close();
	}

}
