package Connect4;

import java.awt.Image;

import javax.swing.ImageIcon;

import entity.PawnName;

/**
 * A simple config file for consts.
 */
public class Config {
	
	public static final int gameFPS = 30;
	
	public static final int GRID_WIDTH = 7;
	public static final int GRID_HEIGHT = 7;
	
	public static final int windowWidth = 900;
	public static final int windowHeight = 600;
	
	public static final int maxWaitingTimeForTurn = 20000;
	public static final int onlineServerPort = 4866;
	
	public final static String imagePath = "images/";
	public final static String savingPath = "save/";
	public final static String pawnsImagePath = imagePath + "pawns/";
	
	public final static String mainBackgroundImagePath = imagePath + "mainBackground.png";
	public final static String shopBackgroundImagePath = imagePath + "shopBackgound.png";
	public final static String chainImagePath = imagePath + "chain.png";
	
	public final static String AccountsSerialFileName = "accounts.bin";
	
	public final static int pointsAddedWhenWin = 5;
	
	// the grid
	public final static String gridImagePath = imagePath + "grid.png";
	public final static int grigSize = 420;
	public final static int gridMarginLeft = 50;

	// pawn
	public final static int pawnSize = 60;
	public final static int nbOfGameToDeblockANewPawn = 3;
	public final static int nbOfBasicPawns = 2;
	
	/**
	 * This function returns the path of a pawn with its name and if it's highlighted or not.
	 * @param name: the name of the pawn
	 * @param highlited: if it's highlighted or not
	 * @return the full path of the pawn
	 */
	public static String getFullPathOfPawn(PawnName name, boolean highlighted) {
		String result = pawnsImagePath + "pawn1.png";
		
		switch (name) {
		case BasicPawn1:
			if(highlighted)
				result = pawnsImagePath + "highlightedPawn1.png";
			else
				result = pawnsImagePath + "pawn1.png";
			break;
			
		case BasicPawn2:
			if(highlighted)
				result = pawnsImagePath + "highlightedPawn2.png";
			else
				result = pawnsImagePath + "pawn2.png";
			break;
			
		case Pizza:
			if(highlighted)
				result = pawnsImagePath + "highlightedPawn3.png";
			else
				result = pawnsImagePath + "pawn3.png";
			break;
			
		case Melon:
			if(highlighted)
				result = pawnsImagePath + "highlightedPawn4.png";
			else
				result = pawnsImagePath + "pawn4.png";
			break;
			
		case Kiwi:
			if(highlighted)
				result = pawnsImagePath + "highlightedPawn5.png";
			else
				result = pawnsImagePath + "pawn5.png";
			break;

		case Orange:
			if(highlighted)
				result = pawnsImagePath + "highlightedPawn6.png";
			else
				result = pawnsImagePath + "pawn6.png";
			break;
		
		case Oreo:
			if(highlighted)
				result = pawnsImagePath + "highlightedPawn7.png";
			else
				result = pawnsImagePath + "pawn7.png";
			break;
			
		case Ball:
			if(highlighted)
				result = pawnsImagePath + "highlightedPawn8.png";
			else
				result = pawnsImagePath + "pawn8.png";
			break;
			
		case Smiley:
			if(highlighted)
				result = pawnsImagePath + "highlightedPawn9.png";
			else
				result = pawnsImagePath + "pawn9.png";
			break;
			
		case Captain:
			if(highlighted)
				result = pawnsImagePath + "highlightedPawn10.png";
			else
				result = pawnsImagePath + "pawn10.png";
			break;
		
		case Apple:
			if(highlighted)
				result = pawnsImagePath + "highlightedPawn11.png";
			else
				result = pawnsImagePath + "pawn11.png";
			break;
			
		default:
			break;
		}
		
		return result;
	}
	
	/**
	 * This function returns the image of a pawn with its name and if it's highlighted or not.
	 * @param name: the name of the pawn
	 * @param highlited: if it's highlighted or not
	 * @return the image
	 */
	public static Image getImageOfPawn(PawnName name, boolean highlighted) {
		ImageIcon iid = new ImageIcon(getFullPathOfPawn(name, highlighted));
        return iid.getImage();
	}
}
