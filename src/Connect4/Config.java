package Connect4;

/**
 * A simple config file for consts.
 */
public class Config {
	
	public static final int gameFPS = 30;
	
	public static final int GRID_WIDTH = 7;
	public static final int GRID_HEIGHT = 7;
	
	public static final char PLAYER1_LABEL = 'X';
	public static final char PLAYER2_LABEL = 'Y';
	
	public static final int windowWidth = 900;
	public static final int windowHeight = 600;
	
	public static final int maxWaitingTimeForTurn = 20000; 
	
	public final static String imagePath = "src/images/";
	
	// the grid
	public final static String gridImagePath = imagePath + "grid.png";
	public final static int grigSize = 420;
	public final static int gridMarginLeft = 50;
	
	// pawn
	public final static String pawn1ImagePath = imagePath + "pawn1.png";
	public final static String pawn2ImagePath = imagePath + "pawn2.png";
	public final static String hightlightedPawn1ImagePath = imagePath + "highlightedPawn1.png";
	public final static String hightlightedPawn2ImagePath = imagePath + "highlightedPawn2.png";
	public final static int pawnSize = 60;
}
