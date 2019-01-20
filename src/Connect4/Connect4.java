package Connect4;

import java.util.Scanner;

import engine.GameEngine;
import engine.GameEngineOnline;
import entity.Pawn;
import entity.Player;
import graphics.MainWindow;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connect4 {
	
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		
            System.out.println("voulez vous jouer en ligne ? (o/n)");
            String result=sc.nextLine();
            if(result.equals("n")){
            
            
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
            else{
                    
                    System.out.println("Serez vous l'host de cette partie ? (o/n)");
                    result=sc.nextLine();
                    if(result.equals("o"))
                    {
                        System.out.println("En attente d'un joueur...");
                        
                        
                        // create the engine
                        GameEngineOnline engine = new GameEngineOnline(Config.GRID_WIDTH, Config.GRID_HEIGHT);
                        
                         // create window
                        MainWindow window = new MainWindow();

                        // we set the engine as the panel to draw
                        window.setCurrentPanel(engine);
                        window.setVisible(true);
                        
                        engine.start(new Player("Jacques", Config.PLAYER1_LABEL), new Player("Paulette", Config.PLAYER2_LABEL));
                       
                        while(engine.isGameRunning()) {
                            engine.update();
                            
                            try {
                                    Thread.sleep(1000 / (long) Config.gameFPS);
                            } catch (InterruptedException e) {
                                    e.printStackTrace();
                            }
                            
                           
                            
                            
                            while(!engine.getCurrentPlayer().getName().equals(engine.getPlayer1().getName()))
                            { 
                                String Message=engine.Listen();
                            if(!Message.isEmpty())
                            {
                                int pawnColPosition =Integer.parseInt(Message);

                                engine.addPawn(pawnColPosition, new Pawn(engine.getCurrentPlayer()));
                            }
                            Message=null;
                                try {
                                    Thread.sleep(1000 / (long) Config.gameFPS);
                            } catch (InterruptedException e) {
                                    e.printStackTrace();
                            }
                            }
                    }
                        
                        
                    }
                    else{
                        
                        System.out.println("Vers quel joueur voulez-vous vous connecter ?");
                        result=sc.nextLine();
                        
                        GameEngineOnline engine = new GameEngineOnline(Config.GRID_WIDTH, Config.GRID_HEIGHT,result);
                        
                        // create window
                        MainWindow window = new MainWindow();

                        // we set the engine as the panel to draw
                        window.setCurrentPanel(engine);
                        window.setVisible(true);
                        
                        engine.start(new Player("Jacques", Config.PLAYER1_LABEL), new Player("Paulette", Config.PLAYER2_LABEL));
                        
                         while(engine.isGameRunning()) {
                            engine.update();
                            
                            try {
                                    Thread.sleep(1000 / (long) Config.gameFPS);
                            } catch (InterruptedException e) {
                                    e.printStackTrace();
                            }
                            
                            
                  
                            
                            while(engine.getCurrentPlayer().getName().equals(engine.getPlayer1().getName()))
                            {
                                String Message=engine.Listen();
                            if(!Message.isEmpty())
                            {
                                int pawnColPosition =Integer.parseInt(Message);

                                engine.addPawn(pawnColPosition, new Pawn(engine.getCurrentPlayer()));
                            }
                            Message=null;
                                try {
                                    Thread.sleep(1000 / (long) Config.gameFPS);
                            } catch (InterruptedException e) {
                                    e.printStackTrace();
                            }
                            }
                    }
                    }
                }
	}

}
