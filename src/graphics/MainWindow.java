package graphics;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Connect4.Config;
import Connect4.Connect4;

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
	public MainWindow() {
		super();
		
		setResizable(false);
		setTitle("Connect 4");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Config.windowWidth, Config.windowHeight);
        
        this.panel = new JPanel();
        this.panel.setLayout(new CardLayout());
        this.add(panel);
        
        // to save the accounts when the window is closed
        this.addWindowListener(new java.awt.event.WindowAdapter() {
    	    @Override
    	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
    	        Connect4.accountManager.saveAccounts();
    	    }
    	});
	}
	
	/**
	 * This function changes the panel to draw in the frame.
	 */
	public void switchToPanel(JPanel panel) {
		this.panel.add("temp", panel);
		((CardLayout) this.panel.getLayout()).show(this.panel, "temp");
	}
}
