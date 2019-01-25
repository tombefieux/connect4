package graphics;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Connect4.Config;
import entity.PawnName;


/**
 * This class represents a panel to select a pawn.
 */
public class PawnSelector extends JPanel implements MouseListener {
	
	private static final long serialVersionUID = 1L;
	
	private static final int pawnXStart = 97;
	private static final int pawnYStart = 201;
	private static final int widthSpace = 101;
	private static final int heightSpace = 85;
	private static final int nbOfCol = 5;
	private static final int paddingYForChain = 18;
	private static final int marginBottom = 25;
	
	private static final Image chainImage = new ImageIcon(Config.chainImagePath).getImage();
	private static final Image backgroundImage = new ImageIcon(Config.shopBackgroundImagePath).getImage();
	
	private List<PawnName> notPossible;						/** This list is useful to know which pawn can't be selected or not. */
	
	/**
	 * Constructor.
	 */
	public PawnSelector() {
		super();
		
		this.setLayout(null);
		this.addMouseListener(this);
	}

	
	/**
	 * This function update the panel to permit to choose the good pawns.
	 * @param notPossible: the pawns not possible to select
	 */
	public void update(List<PawnName> notPossible) {
		this.notPossible = notPossible;
		repaint();
	}
	
	/**
	 * Override the function to render the panel.
	 */
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // draw background
        g.drawImage(backgroundImage, 0, -marginBottom, this);
        
        // paint the pawns
        paintPawns(g);
    }
	
	/**
	 * This function paints the pawns in the panel.
	 * @param g: swing graphics stuff
	 */
	private void paintPawns(Graphics g) {
		for (int i = 0; i < PawnName.values().length; i++)
			paintPawn(PawnName.values()[i], i, g);
	}
	
	/**
	 * This function draws a pawn in the shop with its index.
	 * @param name: the name of the pawn to draw
	 * @param index: the index of the pawn
	 * @param g: swing graphics stuff
	 */
	private void paintPawn(PawnName name, int index, Graphics g) {
		if(index > PawnName.values().length)
			return;
		
		int x = getXwithIndex(index);
		int y = getYwithIndex(index);
				
		g.drawImage(Config.getImageOfPawn(name, this.notPossible.contains(name)), x, y, this);
		
		// if not possible, draw chain 
		if(this.notPossible.contains(name))
			g.drawImage(chainImage, x + 1, y + paddingYForChain, this);
	}
	
	/**
	 * This function returns the x coordinate for a pawn with the index.
	 * @param index: the index
	 * @return the x position
	 */
	private int getXwithIndex(int index) {
		return pawnXStart + Config.pawnSize * (index % nbOfCol) + widthSpace * ((index % nbOfCol));
	}
	
	/**
	 * This function returns the y coordinate for a pawn with the index.
	 * @param index: the index
	 * @return the y position
	 */
	private int getYwithIndex(int index) {
		return pawnYStart + Config.pawnSize * ((int)(index / nbOfCol)) + heightSpace * ((int)(index / nbOfCol)) - marginBottom;
	}
	
	/**
	 * This function returns the index of a pawn at one point. If this point
	 * is not on a pawn, it returns -1.
	 * @param p: the point
	 * @return the index or -1
	 */
	private int getIndexWithPosition(Point p) {
		int result = -1;
		
		for (int i = 0; i < PawnName.values().length && result == -1; i++)
			if(
				getXwithIndex(i) <= p.x && getXwithIndex(i) + Config.pawnSize >= p.x &&
				getYwithIndex(i) <= p.y && getYwithIndex(i) + Config.pawnSize >= p.y
					)
				result = i;
		
		return result;
	}

	/**
	 * If the mouse is clicked.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		int index = getIndexWithPosition(e.getPoint());
		
		// not on a pawn
		if(index == -1)
			return;
		
		// not possible to choose this pawn
		if(this.notPossible.contains(PawnName.values()[index]))
			return;
		
		// give that to the menu
		Connect4.Connect4.menu.handlePawnSelection(PawnName.values()[index]);
	}


	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
