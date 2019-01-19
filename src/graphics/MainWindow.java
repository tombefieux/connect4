package graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Connect4.Config;

/**
 * This class represents the window of the game.
 * The window display only one panel, the engine or the menu.
 */
public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel currentPanel = null;
	
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
	}
	
	/**
	 * This function is a setter for the panel to draw.
	 * @param panel: the panel to draw
	 */
	public void setCurrentPanel(JPanel panel) {
		if(this.currentPanel != null)
			this.remove(this.currentPanel);
		
		this.currentPanel = panel;
		add(this.currentPanel);
	}
}
