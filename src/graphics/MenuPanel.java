package graphics;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Connect4.Config;

/**
 * This class represents a panel for the menu (mainly to display the background image).
 */
public class MenuPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;		/** Fix warning. */

	/**
	 * Override the function to display the background image.
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(new ImageIcon(Config.mainBackgroundImagePath).getImage(), 0, 0, this);
    }
	
}
