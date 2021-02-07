package astel.pacman.assets;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import astel.pacman.util.Utils;
public class Assets {
    
    public static BufferedImage WALL_01, WALL_02, WALL_03, WALL_04, WALL_05,
    WALL_06, WALL_07, WALL_08, WALL_09, WALL_10, WALL_11, WALL_12, WALL_13, WALL_14,
    WALL_15, WALL_16, WALL_17, WALL_18, WALL_19, WALL_20, WALL_21, WALL_22, WALL_23,
    WALL_24, WALL_25, WALL_26, WALL_27, WALL_28, WALL_29, WALL_30, WALL_31, GATE;
    
    public static final int TILE = 66;
    
    public static BufferedImage pauseGround;
    public static BufferedImage aster;
    public static BufferedImage pacClosed;
    public static BufferedImage [] pacOpen = new BufferedImage [4];	    
    public static BufferedImage [] pacOpenWider = new BufferedImage[4];
   
    public static BufferedImage [] leftPlayer = new BufferedImage [3];
    public static BufferedImage [] rightPlayer = new BufferedImage [3];
    public static BufferedImage [] upPlayer = new BufferedImage [3];
    public static BufferedImage [] downPlayer = new BufferedImage [3];
    
    public static BufferedImage [] pacManDead = new BufferedImage[10];
    
    public static BufferedImage [] redGhostLeft = new BufferedImage [2];
    public static BufferedImage [] redGhostRight = new BufferedImage [2];
    public static BufferedImage [] redGhostUp = new BufferedImage [2];
    public static BufferedImage [] redGhostDown = new BufferedImage [2];
    
    public static BufferedImage [] orangeGhostLeft = new BufferedImage [2];
    public static BufferedImage [] orangeGhostRight = new BufferedImage [2];
    public static BufferedImage [] orangeGhostUp = new BufferedImage [2];
    public static BufferedImage [] orangeGhostDown = new BufferedImage [2];
    
    public static BufferedImage [] tealGhostLeft = new BufferedImage [2];
    public static BufferedImage [] tealGhostRight = new BufferedImage [2];
    public static BufferedImage [] tealGhostUp = new BufferedImage [2];
    public static BufferedImage [] tealGhostDown = new BufferedImage [2];
    
    public static BufferedImage [] roseGhostLeft = new BufferedImage [2];
    public static BufferedImage [] roseGhostRight = new BufferedImage [2];
    public static BufferedImage [] roseGhostUp = new BufferedImage [2];
    public static BufferedImage [] roseGhostDown = new BufferedImage [2];
    
    public static BufferedImage [] blueModeGhosts = new BufferedImage [2];
    
    public static BufferedImage [] whiteModeGhosts = new BufferedImage [2];
    
    public static BufferedImage [] eyes = new BufferedImage [4];
    
    private static BufferedImage src;
    public static Font font;
    
    private Assets() {
	throw new RuntimeException("Cannot instantiate!");
    }
    
    public static void initGameFont() {
    	try {
    	    font = Font.createFont(Font.TRUETYPE_FONT, new File("src/ARCADE_I.ttf")).deriveFont(20f);
    	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    	    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/ARCADE_I.ttf")));
    	    
    	} catch(IOException | FontFormatException e) {
    	    System.err.println("Font could not be loaded or other error!");
    	    e.printStackTrace();
    	}
    }
    
    public static void initGhosts() {
    	
	try {
		src = Utils.loadBufferedImage("src/spritesheet.png");
	//red ghost
	redGhostLeft[0] = Utils.crop(src, TILE * 5, TILE * 7, TILE, TILE);
	redGhostLeft[1] = Utils.crop(src, TILE * 6, TILE * 7, TILE, TILE);
	
	redGhostRight[0] = Utils.crop(src, TILE * 3, TILE * 7, TILE, TILE);
	redGhostRight[1] = Utils.crop(src, TILE * 4, TILE * 7, TILE, TILE);

	redGhostUp[0] = Utils.crop(src, TILE * 7, TILE * 7, TILE, TILE);
	redGhostUp[1] = Utils.crop(src, TILE * 8, TILE * 7, TILE, TILE);

	redGhostDown[0] = Utils.crop(src, TILE , TILE * 7, TILE, TILE);
	redGhostDown[1] = Utils.crop(src, TILE * 2, TILE * 7, TILE, TILE);

	
	//orange ghost
	
	orangeGhostLeft[0] = Utils.crop(src, TILE * 1, TILE * 1, TILE, TILE);
	orangeGhostLeft[1] = Utils.crop(src, TILE * 2, TILE * 1, TILE, TILE);

	orangeGhostRight[0] = Utils.crop(src, TILE * 8, TILE * 0, TILE, TILE);
	orangeGhostRight[1] = Utils.crop(src, TILE * 0, TILE * 1, TILE, TILE);

	orangeGhostUp[0] = Utils.crop(src, TILE * 3, TILE * 1, TILE, TILE);
	orangeGhostUp[1] = Utils.crop(src, TILE * 4, TILE * 1, TILE, TILE);
	
	orangeGhostDown[0] = Utils.crop(src, TILE * 6, 0, TILE, TILE);
	orangeGhostDown[1] = Utils.crop(src, TILE * 7, 0, TILE, TILE);

	//teal ghost
	tealGhostLeft[0] = Utils.crop(src, TILE * 9, TILE * 3, TILE, TILE);
	tealGhostLeft[1] = Utils.crop(src, TILE * 9, TILE * 4, TILE, TILE);
	
	tealGhostRight[0] = Utils.crop(src, TILE * 9, TILE * 1, TILE, TILE);
	tealGhostRight[1] = Utils.crop(src, TILE * 9, TILE * 2, TILE, TILE);

	tealGhostUp[0] = Utils.crop(src, TILE * 9, TILE * 5, TILE, TILE);
	tealGhostUp[1] = Utils.crop(src, TILE * 9, TILE * 6, TILE, TILE);
	
	tealGhostDown[0] = Utils.crop(src, TILE * 9, TILE * 0, TILE, TILE);
	tealGhostDown[1] = Utils.crop(src, TILE * 8, TILE * 8, TILE, TILE);
	
	//rose ghost
	roseGhostLeft[0] = Utils.crop(src, TILE * 4, TILE * 8, TILE, TILE);
	roseGhostLeft[1] = Utils.crop(src, TILE * 5, TILE * 8, TILE, TILE);
	
	roseGhostRight[0] = Utils.crop(src, TILE * 2, TILE * 8, TILE, TILE);
	roseGhostRight[1] = Utils.crop(src, TILE * 3, TILE * 8, TILE, TILE);

	roseGhostUp[0] = Utils.crop(src, TILE * 6, TILE * 8, TILE, TILE);
	roseGhostUp[1] = Utils.crop(src, TILE * 7, TILE * 8, TILE, TILE);
	
	roseGhostDown[0] = Utils.crop(src, TILE * 0, TILE * 8, TILE, TILE);
	roseGhostDown[1] = Utils.crop(src, TILE * 1, TILE * 8, TILE, TILE);
	
	//blue mode ghosts
	blueModeGhosts[0] = Utils.crop(src, TILE * 0, TILE * 0, TILE, TILE);
	blueModeGhosts[1] = Utils.crop(src, TILE * 1, TILE * 0, TILE, TILE);

	//white mode ghosts
	whiteModeGhosts[0] = Utils.crop(src, TILE * 9, TILE * 7, TILE, TILE);
	whiteModeGhosts[1] = Utils.crop(src, TILE * 9, TILE * 8, TILE, TILE);
	
	//eyes
	 
	
	eyes[0] = Utils.crop(src, TILE * 3, TILE * 0, TILE, TILE);
	eyes[1] = Utils.crop(src, TILE * 5, TILE * 0, TILE, TILE);
	eyes[2] = Utils.crop(src, TILE * 4, TILE * 0, TILE, TILE);
	eyes[3] = Utils.crop(src, TILE * 2, TILE * 0, TILE, TILE);
	
	} catch(IOException e) {
	    e.printStackTrace();
	    System.err.println("Couldn't load ghosts!");
	}
	

	
    }
    
    public static void initPlayer() {
	try {
		src = Utils.loadBufferedImage("src/spritesheet.png");
	pacManDead[0] = Utils.crop(src, TILE * 5, TILE * 1, TILE, TILE);
	pacManDead[1] = Utils.crop(src, TILE * 6, TILE * 1, TILE, TILE);
	pacManDead[2] = Utils.crop(src, TILE * 7, TILE * 1, TILE, TILE);
	pacManDead[3] = Utils.crop(src, TILE * 8, TILE * 1, TILE, TILE);
	pacManDead[4] = Utils.crop(src, TILE * 0, TILE * 2, TILE, TILE);
	pacManDead[5] = Utils.crop(src, TILE * 1, TILE * 2, TILE, TILE);
	pacManDead[6] = Utils.crop(src, TILE * 2, TILE * 2, TILE, TILE);
	pacManDead[7] = Utils.crop(src, TILE * 3, TILE * 2, TILE, TILE);
	pacManDead[8] = Utils.crop(src, TILE * 4, TILE * 2, TILE, TILE);
	pacManDead[9] = Utils.crop(src, TILE * 5, TILE * 2, TILE, TILE);

		
	leftPlayer[0] = Utils.crop(src, TILE * 6, TILE * 2, TILE, TILE);
	leftPlayer[1] = Utils.crop(src, TILE * 0, TILE * 3, TILE, TILE);
	leftPlayer[2] = Utils.crop(src, TILE * 4, TILE * 3, TILE, TILE);
	
	rightPlayer[0] = Utils.crop(src, TILE * 6, TILE * 2, TILE, TILE);
	rightPlayer[1] = Utils.crop(src, TILE * 7, TILE * 2, TILE, TILE);
	rightPlayer[2] = Utils.crop(src, TILE * 2, TILE * 3, TILE, TILE);

	upPlayer[0] = Utils.crop(src, TILE * 6, TILE * 2, TILE, TILE);
	upPlayer[1] = Utils.crop(src, TILE * 1, TILE * 3, TILE, TILE);
	upPlayer[2] = Utils.crop(src, TILE * 5, TILE * 3, TILE, TILE);

	downPlayer[0] = Utils.crop(src, TILE * 6, TILE * 2, TILE, TILE);
	downPlayer[1] = Utils.crop(src, TILE * 8, TILE * 2, TILE, TILE);
	downPlayer[2] = Utils.crop(src, TILE * 3, TILE * 3, TILE, TILE);

	
	} catch(IOException e) {
	    e.printStackTrace();
	    System.err.println("Couldn't load player!");
	}
    }
    
    public static void initWalls() {
	try {
	GATE = Utils.loadBufferedImage("src/Gate.png"); 
	WALL_01 = Utils.crop(src, TILE * 6, TILE * 3, TILE, TILE);
	WALL_02 = Utils.crop(src, TILE * 7, TILE * 3, TILE, TILE);
	WALL_03 = Utils.crop(src, TILE * 8, TILE * 3, TILE, TILE);
	WALL_04 = Utils.crop(src, TILE * 0, TILE * 4, TILE, TILE);
	WALL_05 = Utils.crop(src, TILE * 1, TILE * 4, TILE, TILE);
	WALL_06 = Utils.crop(src, TILE * 2, TILE * 4, TILE, TILE);
	WALL_07 = Utils.crop(src, TILE * 3, TILE * 4, TILE, TILE);
	WALL_08 = Utils.crop(src, TILE * 4, TILE * 4, TILE, TILE);
	WALL_09 = Utils.crop(src, TILE * 5, TILE * 4, TILE, TILE);
	WALL_10 = Utils.crop(src, TILE * 6, TILE * 4, TILE, TILE);
	WALL_11 = Utils.crop(src, TILE * 7, TILE * 4, TILE, TILE);
	WALL_12 = Utils.crop(src, TILE * 8, TILE * 4, TILE, TILE); 
	WALL_13 = Utils.crop(src, TILE * 0, TILE * 5, TILE, TILE);
	WALL_14 = Utils.crop(src, TILE * 1, TILE * 5, TILE, TILE);
	WALL_15 = Utils.crop(src, TILE * 2, TILE * 5, TILE, TILE);
	WALL_16 = Utils.crop(src, TILE * 3, TILE * 5, TILE, TILE);
	WALL_17 = Utils.crop(src, TILE * 4, TILE * 5, TILE, TILE);
	WALL_18 = Utils.crop(src, TILE * 5, TILE * 5, TILE, TILE);
	WALL_19 = Utils.crop(src, TILE * 6, TILE * 5, TILE, TILE);
	WALL_20 = Utils.crop(src, TILE * 7, TILE * 5, TILE, TILE);
	WALL_21 = Utils.crop(src, TILE * 8, TILE * 5, TILE, TILE);
	WALL_22 = Utils.crop(src, TILE * 0, TILE * 6, TILE, TILE);
	WALL_23 = Utils.crop(src, TILE * 1, TILE * 6, TILE, TILE);
	WALL_24 = Utils.crop(src, TILE * 2, TILE * 6, TILE, TILE);
	WALL_25 = Utils.crop(src, TILE * 3, TILE * 6, TILE, TILE);
	WALL_26 = Utils.crop(src, TILE * 4, TILE * 6, TILE, TILE);
	WALL_27 = Utils.crop(src, TILE * 5, TILE * 6, TILE, TILE);
	WALL_28 = Utils.crop(src, TILE * 6, TILE * 6, TILE, TILE);
	WALL_29 = Utils.crop(src, TILE * 7, TILE * 6, TILE, TILE);
	WALL_30 = Utils.crop(src, TILE * 8, TILE * 6, TILE, TILE); 
	WALL_31 = Utils.crop(src, TILE * 9, TILE * 6, TILE, TILE);
	} catch(IOException e) {
	    System.err.println("Couldn't load walls!");
	    e.printStackTrace();
	}
	
	
    };
    
    
    

}
