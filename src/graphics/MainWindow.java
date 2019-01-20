package graphics;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Connect4.Config;
import engine.GameEngine;

/**
 * This class represents the window of the game.
 * The window display only one panel, the engine or the menu.
 */
public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel panel;	/** The main panel. */
		
	/**
	 * Constructor.
	 */
	public MainWindow(GameEngine engine, Menu menu) {
		super();
		
		setResizable(false);
		setTitle("Connect 4");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Config.windowWidth, Config.windowHeight);
        
        this.panel = new JPanel();
        this.panel.setLayout(new CardLayout());
        this.panel.add("engine", engine);
        this.panel.add("menu", menu);
        this.add(panel);
	}
	
	/**
	 * This function makes the frame drawn the engine.
	 */
	public void switchToEngine() {
		((CardLayout) this.panel.getLayout()).show(this.panel, "engine");
	}
	
	/**
	 * This function makes the frame drawn the menu.
	 */
	public void switchToMenu() {
		((CardLayout) this.panel.getLayout()).show(this.panel, "menu");
	}
}
