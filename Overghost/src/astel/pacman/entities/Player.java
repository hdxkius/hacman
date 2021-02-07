package astel.pacman.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import astel.pacman.assets.Assets;
import astel.pacman.main.GamePanel;
import astel.pacman.main.World;
import astel.pacman.util.Tile;
import astel.pacman.util.Utils;

public class Player extends Entity{
    private Point playerPoint;
    private Timer time;
    World world;
    
    private int round = 1;
    public static boolean gamePaused = false;
    public static boolean gameOver = false;
    private int lives = 3;
    private int x;
    public static boolean wasReset = false;
    private int y;
    private int index = 0;
    private boolean hasTouched;
    private boolean drawingDead = false;
    private boolean gameOverRunning = false;
    public static boolean restarted = false;
    
    public static boolean isDead = false;
    private boolean restarting = false;
    private int countingOver = 0;
    private boolean counting = false;
    
    private boolean[][] previous;
    public static int POINT_COUNTER = 0;
    
    
    private Timer deathTimer;
    private Timer deadCounter;
    private int timeCounter;
    private boolean doneCountingForDeath;
    private boolean running = false;
    
    private Timer breadTimer;

    public static boolean finishedBread = false;
    private boolean playerShouldStopMoving = false;
    private Rectangle playerTangle;
    private int shouldDirection;
    
    private boolean onWaitUp = false, onWaitDown = false, onWaitLeft = false,
    		onWaitRight = false;;
    
    private int playerAnime = 0;
    private int direction;
    
    private boolean ateBread = false;
    
    public static boolean doneCounting = false;
    public static boolean finished = false;
    private boolean isDone = false;
    private int xBeforeRight = 0;
    private int xBeforeLeft = 0;
    private int yBeforeUp = 0;
    private int yBeforeDown = 0;
    
    private Wall wall;
    
    
    private boolean doneAnimating = false;
    
    private boolean isExtended;
    private Ghost [] ghosts;
    private Color color = new Color(77, 94, 163);
    Thread thread, thread2;
    Runnable run = () -> {
    	
        	System.out.println("Started");    	
        	
        	checkForDone();
    	
    	};
    	
    	Runnable run2 = () -> {
    		while(true) {
    			calculateMunchable();
    		}
    	};
    
    
    private boolean up = false, down = false, left = false, right = false;
    private int oldDirection;
    private boolean[][] map;
    private boolean[][] munchedTiles;
    private JFrame frame;
    
	private boolean wasLeftUpP = false, wasLeftDownP = false
			, wasRightUpP = false, wasRightDownP = false;
    
    public Player(int x, int y, int direction, Ghost[] ghosts, JFrame frame) {
    	this.frame = frame;
	this.x = x;
	this.y = y;
	this.ghosts = ghosts;
	this.wall = new Wall(this);
	world = new World(this, wall);
	this.playerPoint = new Point(x / Tile.TILE_SIZE, y / Tile.TILE_SIZE);
	this.playerTangle = new Rectangle(this.x, this.y, Tile.TILE_SIZE, Tile.TILE_SIZE);
	checkForDone();
	thread = new Thread(run);
	thread2 = new Thread(run2);
	Assets.initPlayer();
	thread.start();
	thread2.start();
	animatePlayer();
	animateMovement();
	
	munchedTiles = Utils.copyTileArray(world.getBreadArray());
	map = Utils.copyTileArray(world.getTileMap());

	
	previous = new boolean[munchedTiles.length][munchedTiles[0].length];

	if(isDone) {
	    System.out.println("You Win!");
	}
	

	
	
    }
    public void setPlayerHasTouched(boolean touch) {
    	hasTouched = touch;
    }
    
    public boolean hasPlayerTouchedGhost() {
    	return hasTouched;
    }
    
    
    public void setIsExtended(boolean extended) {
    	this.isExtended = extended;
    }
    
    public boolean wasExtended() {
    	return isExtended;
    }
    
    public Runnable getRunnable() {
    	return run;
    }
    
    public boolean hasEatenBread() {
    	return ateBread;
    }
    
    public void setEatenBread(boolean ate) {
    	this.ateBread = ate;
    	
    }
    
    public void setTimeCounter(int count) {
    	this.timeCounter = count;
    }
    
    public int getTimeCounter() {
    	return timeCounter;
    }

    private void calculateMunchable() {      		
      		
    
    }
    
    public synchronized void counter(int counter) {
    	timeCounter = counter;
    	breadTimer = new Timer();
    	TimerTask task = new TimerTask() {
			@Override
			public void run() {
		
			
				if(timeCounter >= 0) {
					timeCounter--;
			
					doneCounting = false;
			
				
					
					
					
				}
				
				
				if(timeCounter == -1) {
					
						Player.finishedBread = false;
						doneCounting = true;
					
						breadTimer.cancel();
				}
		
			}
    		
    	};
    	
    	breadTimer.schedule(task, 0, 1000);
    	
    }
    

    
    public boolean isOutOfBounds() {
    	return (x/Tile.TILE_SIZE < 1 && y/Tile.TILE_SIZE == 13) ||
    			(x >= 20 * Tile.TILE_SIZE && y/Tile.TILE_SIZE == 13); 
    }
    
    private void escapeBounds() {
    	if(direction == 0) {
    		moveLeft();
    		
    		if(x == - Tile.TILE_SIZE * 2) {
    			this.x = 21 * Tile.TILE_SIZE;
    			return;
    		}
    		
    	} else if(direction == 2) {
    		moveRight();
    		
    		if(x >= Tile.TILE_SIZE * 23) {
    			this.x = -Tile.TILE_SIZE ;
    			return;
    		}	
    	} 
    }
    
 
    
    public void setRectanglePosition(int x, int y) {
    	playerTangle.setLocation(x, y);
    }
    
    public Rectangle getPlayerRectangle() {
    	return playerTangle;
    }
    
    public void keyPlayer(KeyEvent e) {
    	int key = e.getKeyCode();
    	
    	if(key == KeyEvent.VK_ESCAPE) {
    		GamePanel.onPause = !GamePanel.onPause;
    		gamePaused = !gamePaused;
    	}
    	
    	if(GamePanel.onPause) {
    		return;
    	}
    	
   
    
    	
    	
    	if(key == KeyEvent.VK_A) {

        	if(playerShouldStopMoving || gameOver || isDone) {
        		return;
        	}
    		
    		Ghost.playerHasPressedKey = true;

    		if(isOutOfBounds()) {
    			return;
    		} else
    		if(isBlockedLeftUp()) {
    			return;
    		} else if(isBlockedLeftDown()) {
    			return;
    		} 
    		if(isLeftBlocked() && ((!isUpBlocked() && direction == 1) 
    				|| (!isDownBlocked() && direction == 3)) ) {
       		onWaitRight = false;
    		onWaitUp = false;
    		onWaitDown = false;
    		onWaitLeft = true;
    		} else
    	    if(!isLeftBlocked() && !isOnYRemainder()) {
        		onWaitRight = false;
        		onWaitUp = false;
        		onWaitDown = false;
        		onWaitLeft = false;
    		setDirection(0);
    		setMovement(true, false, false, false);
    	    }
    	    
    	    

    	} else if(key == KeyEvent.VK_W ) {

        	if(playerShouldStopMoving  || gameOver || isDone) {
        		return;
        	}
    		Ghost.playerHasPressedKey = true;
    		
    		if(isOutOfBounds()) {
    			return;
    		}
    		if(isBlockedRightUp()) {
    			return;
    		} else if(isBlockedLeftUp()) {
    		return;
    		}
    		else if(isUpBlocked() && ((!isLeftBlocked()
    				&& direction == 0) || (!isRightBlocked() && 
    						direction == 2))) {
    			  onWaitUp = true;
    	    		onWaitLeft = false;
    	    		onWaitRight = false;
    	    		onWaitDown = false;
    		}
    		else
    	    if(!isUpBlocked() && !isOnXRemainder()) {
    	  	  onWaitUp = false;
	    		onWaitLeft = false;
	    		onWaitRight = false;
	    		onWaitDown = false;
    	    setDirection(1);
    	    setMovement(false, false, true, false);
    	    }

    	} else
    	if(key == KeyEvent.VK_D) {
    		

        	if(playerShouldStopMoving  || gameOver ||isDone) {
        		return;
        	}
    		Ghost.playerHasPressedKey = true;
    		
    		if(isOutOfBounds()) {
    			return;
    		}
    		
    		if(isOutOfBounds()) {
    			
    			return;
    		}
    		
    		if(isBlockedRightUp()) {
    			return;
    		} else if(isBlockedRightDown()) {
    			return;
    		} 
    		if(isRightBlocked() && ((!isUpBlocked() && direction == 1) 
    				|| (!isDownBlocked() && direction == 3))) {
    	    	onWaitRight = true;
        		onWaitUp = false;
        		onWaitDown = false;
        		onWaitLeft = false;
    			
    		} 
    	    if(!isRightBlocked() && !isOnYRemainder()) {
    	    	onWaitRight = false;
        		onWaitUp = false;
        		onWaitDown = false;
        		onWaitLeft = false;
    	    setDirection(2);
    	    setMovement(false, true, false, false);
    	    }
    	    
    	    
    	 
    	}
    	if(key == KeyEvent.VK_S) {

        	if(playerShouldStopMoving || gameOver || isDone) {
        		return;
        	}
    		Ghost.playerHasPressedKey = true;
    		if(isBlockedRightDown()) {
    			return;
    		} else if(isBlockedLeftDown()) {
    		return;
    		} else
    		
    		if(isDownBlocked() && ((!isLeftBlocked()
    				&& direction == 0) || (!isRightBlocked() && 
    						direction == 2))) {
    	
       		onWaitRight = false;
    		onWaitUp = false;
    		onWaitDown = true;
    		onWaitLeft = false;
    		} else
    	    if(!isDownBlocked() && !isOnXRemainder()) {
    	    
         		onWaitRight = false;
        		onWaitUp = false;
        		onWaitDown = false;
        		onWaitLeft = false;
    	    setDirection(3);
    	    setMovement(false, false, false, true);
    	    } 

    	} else if(key == KeyEvent.VK_C) {
    		clearBreadTiles();
    	}
	   
    	
    	/*else {
    	   /*switch(player.getOldDirection()) {
    	   case 0:
    	       player.setDirection(0);
    	       player.setMovement(true, false, false, false);
    	       break;
    	   case 1: 
    	       player.setDirection(1);
    	       player.setMovement(false, false, true, false);
    	       break;
    	   case 2:
    	       player.setDirection(2);
    	       player.setMovement(false, true, false, false);
    	       break;
    	   case 3:
    	       player.setDirection(3);
    	       player.setMovement(false, false, false, true);
    	       break;
    	       default:
    		   System.out.println("nothing");
    	       break;
    	   }
    	    

    	}*/
    }
    
    public boolean isDead() {
    	return isDead;
    }
    
    public void setDead(boolean dead) {
    	this.isDead = dead;
    }
    
    private boolean isBlockedLeftUp() {
    	return isLeftBlocked() && isUpBlocked();
    }
    
    private boolean isBlockedLeftDown() {
    	return isLeftBlocked() && isDownBlocked();
    }
    
    private boolean isBlockedRightUp() {
    	return isRightBlocked() && isUpBlocked();
    }
    
    private boolean isBlockedRightDown() {
    	return isRightBlocked() && isDownBlocked();
    }
    
    public boolean[][] copyTileArray(boolean[][] tileArray) {
	boolean[][] outArray = new boolean[tileArray.length][tileArray[0].length];
	for(int i = 0; i < tileArray.length; i++) {
	    for(int j = 0; j < tileArray[0].length; j++) {
		outArray[j][i] = tileArray[j][i];
	    }
	}
	
	return outArray;
    }
    
    public int getXBeforeLeft() {
	return xBeforeLeft;
    }
    
    public int getXBeforeRight() {
	return xBeforeRight;
    }
    
    public int getYBeforeUp() {
	return yBeforeUp;
    }
    
    public int getYBeforeDown() {
	return yBeforeDown;
    }
    
    public void setPlayerPoint(Point p) {
	this.playerPoint = p;
    }
    
    public Point getTileCoordinate() {
	return playerPoint;
    }
    
    public Player() {
	this(10 * Tile.TILE_SIZE, 5 * Tile.TILE_SIZE, 1, null, null);
	
    }
  
    
   
    
    public void setPosition(int x, int y) {
	this.x = x;
	this.y = y;
    }
    
    
    public void setMovement(boolean left, boolean right, boolean up, boolean down) {
	this.left = left;
	this.right = right;
	this.up = up;
	this.down = down;
    }


    public void setDirection(int direction) {
	this.oldDirection = this.direction;
	this.direction = direction;
	
    }
    
    public int getDirection() {
	return direction;
    }
    
    public void eraseVisitedTile(boolean[][] tileMap) {
	if(tileMap[y / Tile.TILE_SIZE][x / Tile.TILE_SIZE] == true) {
  	  
	
	    tileMap[y / Tile.TILE_SIZE][x / Tile.TILE_SIZE] = false;
	}
    }
    
    public boolean wasOnTile(boolean[][] tileMap, int x, int y) {
	    return !(tileMap[y / Tile.TILE_SIZE][x / Tile.TILE_SIZE] == true);
    }
    
    public void setWasOnTile(boolean[][] tileMap, int x, int y, boolean b) {
    	tileMap[x/Tile.TILE_SIZE][y/Tile.TILE_SIZE] = b;
    }
    
    public int getOldDirection() {
	return oldDirection;
    }
    
    public void drawPlayer(Graphics g) {
    	
    	

   		g.setColor(Color.WHITE);
   		if(!Ghost.playerHasPressedKey && !Player.gameOver) {

	    g.setFont(Assets.font.deriveFont(10f));
   		g.drawString("Ready", 9 * Tile.TILE_SIZE,
				16 * Tile.TILE_SIZE);

    	}
   	
    g.setFont(Assets.font.deriveFont(15f));
	
	    
    	if(gameOver) {
    	
			g.drawString("Game Over", 6 * Tile.TILE_SIZE + Tile.TILE_SIZE/2,
					21 * Tile.TILE_SIZE);

    		gameOver(2);
    	}
    
    	for(int i = 1; i <= lives; ++i) {
    		   g.drawImage(Assets.rightPlayer[1], Tile.TILE_SIZE * 3 + (i * Tile.TILE_SIZE), 
    				   Tile.TILE_SIZE * 27,
    				   Tile.TILE_SIZE, Tile.TILE_SIZE, null);
    	   }
    
   g.drawString("SCORE " + POINT_COUNTER, 10 * Tile.TILE_SIZE, 28 * Tile.TILE_SIZE);
   g.drawString("ROUND " + round, 10 * Tile.TILE_SIZE, 29 * Tile.TILE_SIZE);

	if(!isDead && !gameOver) {

   
   
   
		switch(direction) {
	case 0:
	    g.drawImage(Assets.leftPlayer[playerAnime], x, y, 16, 16, null);
	    break;
	case 1:
	    g.drawImage(Assets.upPlayer[playerAnime], x, y, 16, 16, null);
	    break;
	case 2:
	    g.drawImage(Assets.rightPlayer[playerAnime], x, y, 16, 16, null);
	    break;
	case 3:
	    g.drawImage(Assets.downPlayer[playerAnime], x, y, 16, 16, null);
	    break;
	    default:
		throw new IllegalStateException("!invalid direction!");
	}
		
		if(Ghost.stopMoving) {
		
			g.drawString("100", getX() - 10, getY() + 10);
			
		}
		
		   if(isDone) {
			   
			    g.drawString("You win",  7 * Tile.TILE_SIZE + Tile.TILE_SIZE/2,
						21 * Tile.TILE_SIZE);
			    Utils.threadSafeTask(() -> restartMap(3));
			}
		setRectanglePosition(x, y);
	//	g2d.draw(playerTangle);
	} else if(isDead){
		g.drawString("You died",  7 * Tile.TILE_SIZE ,
				21 * Tile.TILE_SIZE);
		Utils.threadSafeTask(() -> {counterDead(index);});
		g.drawImage(Assets.pacManDead[index], x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	
	} 
		
    }
    
    public int getRound() {
    	return round;
    }
    
    
    
    
 
    
    private void clearBreadTiles() {
    	for(int i = 0; i < munchedTiles.length; i++) {
    		for(int j = 0; j < munchedTiles[0].length; j++) {
    			munchedTiles[i][j] = false;
    		}
    	}
    }
    
    
    public void switchMoved(boolean up, boolean down, boolean left, boolean right) {
	if(right)
	    moveRight();
	else if(left)
	    moveLeft();
	else if(up)
	    moveUp();
	else if(down)
	    moveDown();
    }
    
    public int setNewDirectionGetOldDirection(int d) {
	this.oldDirection = this.direction;
	this.direction = d;
	

	
	return oldDirection;
    }
    
    public boolean isOnMunchable() {
    	return munchedTiles[x/Tile.TILE_SIZE][y/Tile.TILE_SIZE];
    }
    
    public boolean isDone() {
    	
	try {
  	for(int i = 0; i < munchedTiles.length; i++) {
  	    for(int j = 0; j < munchedTiles[0].length; j++) {
  		
  		if(munchedTiles[j][i] == true)
  		    return false;
  		
  	    }
  	}
  	
  	
  	return true;}
	catch(Exception e) {
		return false;
	}
      }
    
    public void checkForDone() {
	time = new Timer();
	time.schedule(new TimerTask() {
		
	

	    @Override
	    public void run() {
	    	
	 
		if(isDone()) {
		    isDone = true;
		    finished = true;
		} else {
			finished = false;
		    isDone = false;
		  
			}
		
		
		
		
		
	
	    }
	    
	}, 0, 500);
	
    }
    
    private boolean isOnXRemainder() {
    	return x % Tile.TILE_SIZE != 0;
    }
    
    private boolean isOnYRemainder() {
    	return y % Tile.TILE_SIZE != 0;
    }
    
    private boolean isLeftBlocked() {
    	
    	if(x % Tile.TILE_SIZE != 0) {
    		return false;
    	}
    	
    	return !world.canAccess(map,y , x - Tile.TILE_SIZE);
    }
    
    private boolean isRightBlocked() {
    	
    	if(x % Tile.TILE_SIZE != 0) {
    		return false;
    	}
    	
    	return !world.canAccess(map, y, x + Tile.TILE_SIZE);
    }
    
    private boolean isUpBlocked() {
    	
    	if(y % Tile.TILE_SIZE != 0) {
    		return false;
    	}
    	
    	return !world.canAccess(map,y - Tile.TILE_SIZE ,x);
    }
    
    private boolean isDownBlocked() {
    	
    	if(y % Tile.TILE_SIZE != 0) {
    		return false;
    	}
    	
    	return !world.canAccess(map, y + Tile.TILE_SIZE, x);
    	
    }
    
    private boolean isOnRemainder() {
    	return x % Tile.TILE_SIZE != 0 || y % Tile.TILE_SIZE != 0;
    }
    
    private boolean isAtBoarder() {
    	return getX()/Tile.TILE_SIZE == 0 && getY()/Tile.TILE_SIZE == 13;
    }
    
    
    public void start() {
    	if(running) {
    		return;
    	}
    	running = true;
    	thread.start();
    	
    }
    

    
    public synchronized void stop() {
    	try {
    	  if(!running) {
    			return;
    		    }
    		    running = false;
    		    thread.join();
    	} catch(InterruptedException e) {
    		System.err.println("something went wrong");
    	}
    }
    
    

    
    public synchronized void deathCounter(int counter) {
    	timeCounter = counter;
    	deathTimer = new Timer();
    	System.out.println("entered");
    	TimerTask task = new TimerTask() {
			@Override
			public void run() {
		
			
				if(timeCounter >= 0) {
					timeCounter--;
			
					doneCountingForDeath = false;
			
					System.out.println("counter: " + timeCounter);
					
					
					
				}
				
				
				if(timeCounter == -1) {
					
					
						doneCountingForDeath = true;
						
						deathTimer.cancel();
				}
		
			}
    		
    	};
    	
    	deathTimer.schedule(task, 0, 1000);
    	
    }
    
    public boolean getDoneAnimating() {
		return doneAnimating;
	}
	
	public void setPlayerDoneAnimating(boolean a) {
		this.doneAnimating = a;
	}
    public void playerShouldStopMoving(boolean b) {
    	this.playerShouldStopMoving = b;
    }
    
    public boolean shouldPlayerStopMoving() {
    	return playerShouldStopMoving;
    }
    
    public void moveRight() {
    	if(Ghost.stopMoving) {
    		return;
    	}
 	 this.x += 1;
     }
    
    public void moveLeft() {
    	if(Ghost.stopMoving) {
    		return;
    	}
	 this.x -= 1;
    }
    
    public void moveUp() {
    	if(Ghost.stopMoving) {
    		return;
    	}
	    this.y -= 1;
	
    }
    
    public void setExtended(boolean b) {
    	this.isExtended = b;
    }
    
    public void moveDown() {
    	if(Ghost.stopMoving) {
    		return;
    	}
	    this.y += 1;
    }
    
    public void setPlayerAnime(int a) {
	this.playerAnime = a;
    }
    
    public int getPlayerAnime() {
	return playerAnime;
    }
    
    public void animatePlayer() {
	time = new Timer();
	time.schedule(new TimerTask() {

	    @Override
	    public void run() {
		if(playerAnime < 2) {
		playerAnime++;
		}
		else {
		    playerAnime = 0;
		}
		
	
			    }
	    
	}, 0, 25);
    }
    
 
    
   
    private boolean isOnBreadTile() {
    	return ((x/Tile.TILE_SIZE == 1 && y/Tile.TILE_SIZE == 3) ||
    			(x/Tile.TILE_SIZE == 1 && y/Tile.TILE_SIZE == 20) ||
    			(x/Tile.TILE_SIZE == 19 && y/Tile.TILE_SIZE == 3) ||
    			(x/Tile.TILE_SIZE == 19 && y/Tile.TILE_SIZE == 20));
    }
    public void eraseMunchedTiles(boolean [][] tileMap) {
    	if(!isOutOfBounds()) {
	if(x % Tile.TILE_SIZE == 0 && y % Tile.TILE_SIZE == 0)
		if(isOnBreadTile() &&
				tileMap[y / Tile.TILE_SIZE][x / Tile.TILE_SIZE] == true) {
			POINT_COUNTER += 50;
		    tileMap[y / Tile.TILE_SIZE][x / Tile.TILE_SIZE] = false;
		}else
	if(tileMap[y / Tile.TILE_SIZE][x / Tile.TILE_SIZE] == true) {
		POINT_COUNTER += 10;
	    tileMap[y / Tile.TILE_SIZE][x / Tile.TILE_SIZE] = false;
	} 
    	}
    }
    
    public boolean[][] getErasedMunchedTiles() {
	return munchedTiles;
    }
    
    public void checkForNearPassage(Entity e, boolean[][] tileMap) {
	
	if(e.getX() / Tile.TILE_SIZE < tileMap.length && e.getY() / Tile.TILE_SIZE < tileMap[0].length) {
	    
	   
	    switch(direction) {
	    case 0:
		if(e.getX() / Tile.TILE_SIZE * 3 < tileMap.length) {
	
		if(world.canAccess(tileMap, e.getX() - Tile.TILE_SIZE, 
			e.getY() - Tile.TILE_SIZE
			)) {
		    setMovement(false, false, true, false);
		    
		}
		}
		
		break;
	    case 1:
		break;
	    case 2:
		break;
	    case 3:
		break;
	default:
		return;
	    }
	}
    }
    
    public void animateMovement() {
	time = new Timer();
	time.schedule(new TimerTask() {

	    @Override
	    public void run() {
	
		movePlayer();
		if(!isOutOfBounds()) {
		eraseMunchedTiles(munchedTiles);
		}
			    }
	    
	}, 0, 10);
    }

    @Override
    public int getX() {
	// TODO Auto-generated method stub
	return x;
    }

    @Override
    public int getY() {
	// TODO Auto-generated method stub
	return y;
    }
    
    public void movePlayer() {
    	if(GamePanel.onPause) {
    		return;
    	}

	if(!playerShouldStopMoving && !isDead && Ghost.playerHasPressedKey && !isDone) {
		
		
		if(!isOutOfBounds()) {
	if(getX() / Tile.TILE_SIZE == 0 && getY() / Tile.TILE_SIZE == 13 && getDirection() == 0) {
	    setPosition(21 * Tile.TILE_SIZE, 13 * Tile.TILE_SIZE);
	} else if(getX() / Tile.TILE_SIZE == 20 && getY() / Tile.TILE_SIZE == 13 && getDirection() == 2) {
	    setPosition(0, 13 * Tile.TILE_SIZE);
	}
	setNewDirectionGetOldDirection(this.direction);
	
	
	if(!isOnRemainder()) {
	if(getX()/Tile.TILE_SIZE == 23 && getY()/Tile.TILE_SIZE == 13 && direction == 0) {
		this.x = Tile.TILE_SIZE * 20;
		return;
	}else if(getX()/Tile.TILE_SIZE == 0 && getY()/Tile.TILE_SIZE == 13 && direction == 2) {
		this.x = 0;
		return;
	}
	}
	
	
	if(!isOnXRemainder() && isAtBoarder()) {
		this.x = 10;
		this.y = 3;
		return;
	} else
	
	if(isOnRemainder()) {
	
		switch(direction) {
		case 0:
				moveLeft();
				return;
			
		case 1:
				moveUp();
			return;
		case 2:
				moveRight();
				return;
		case 3:
				moveDown();
			return;
		}
	} else if(onWaitUp) {
		
		
		if(!isUpBlocked() && !isOnXRemainder()) {
		
			moveUp();
			setDirection(1);
			setMovement(false, false, true, false);
			onWaitUp = false;
			return;
		} else if(isUpBlocked()) {
			if(direction == 0) {
				if(!isLeftBlocked()) {
				moveLeft();
				setMovement(true, false, false, false);
				}
			} else if(direction == 2) {
				if(!isRightBlocked()) {
				moveRight();
				setMovement(false, true, false, false);
				}
			}
		}
	} else if(onWaitDown) {
	
		if(!isDownBlocked() && !isOnXRemainder()) {
			moveDown();
			setDirection(3);
			setMovement(false, false, false, true);
			onWaitDown = false;
			return;
		} else if(isDownBlocked()) {
			if(direction == 0) {
			if(!isLeftBlocked()) {
				moveLeft();
				setMovement(true, false, false, false);
			
			}
			} else if(direction == 2) {
				if(!isRightBlocked()) {
				moveRight();
				setMovement(false, true, false, false);
				
				}
			}
				
		} else {
		}
	} else if(onWaitLeft) {
	
			if(!isLeftBlocked() && !isOnYRemainder()) {
				
				moveLeft();
				setDirection(0);
				setMovement(true, false, false, false);
				onWaitLeft = false;
				return;
			} else if(isLeftBlocked()) {
				if(direction == 1) {
					
					if(!isUpBlocked()) {
					moveUp();
					setMovement(false, false, true, false);
					}
				} else if(direction == 3) {
					if(!isDownBlocked()) {
					moveDown();
					setMovement(false, false, false, true);
					}
				}
			}
		
	} else if(onWaitRight) {
	
		if(!isRightBlocked() && !isOnYRemainder()) {
			moveRight();
			setDirection(2);
			setMovement(false, true, false, false);
			onWaitRight = false;
			return;
		} else if(isRightBlocked()) {
			if(direction == 1) {
				if(!isUpBlocked()) {
				moveUp();
				setMovement(false, false, true, false);
				}
			} else if(direction == 3) {
				if(!isDownBlocked()) {
				moveDown();
				setMovement(false, false, false, true);
				}
			}
		}
		
	}
	else 
	if(left && !isLeftBlocked()) {
			moveLeft();
			setMovement(true, false, false, false);
	} else if(right && !isRightBlocked()) {
		moveRight();
		setMovement(false, true, false, false);
	} else if(up && !isUpBlocked()) {
		moveUp();
		setMovement(false, false, true, false);
	} else if(down && !isDownBlocked()) {
		moveDown();
		setMovement(false, false, false, true);
	}
	
	/*else
	
	{
		
		System.out.println("on last switcher");
	    //switch
	    switch(oldDirection) {
	    case 0: 
		if(!isLeftBlocked() && !isOnRemainder()) {
		   
		    moveLeft();
		    setMovement(true, false, false, false);
		}
		    break;
	    case 1:
		if(!isUpBlocked() && !isOnRemainder()) { 
		    
		
			moveUp();
			setMovement(false, false, true, false);
		}
		break;
	    case 2:
		if(!isRightBlocked() && !isOnRemainder()) {
			moveRight();
			setMovement(false, true, false, false);
		}
		break;
	    case 3:
		if(!isDownBlocked() && !isOnRemainder()) {
		
			moveDown();
			setMovement(false, false, false, true);
		}
		break;
		
		default:
		    System.out.println("Nothing");
		
	    }
	}*/
	
		} else {
			escapeBounds();
		}
	}
	}
    
    private void gameOver(int counter) {
    	deadCounter = new Timer();
		if(gameOverRunning) {
			return;
		}
		gameOverRunning = true;
    	TimerTask task = new TimerTask() {
	
			int timer = counter;
			@Override
			public void run() {
				if(countingOver < timer) {
				countingOver++;
				
				} else {
				
					deadCounter.cancel();
					System.exit(0);
				}
			}
			
		};
		
		deadCounter.schedule(task, 0, 1000);
    }
    
    private void resetMunchedTiles() {
    	munchedTiles = copyTileArray(world.getBreadArray());
    }
    

    
    private void restartMap(int counter) {
    	if(restarting) {
			return;
		}
		restarting = true;
		Timer restarter = new Timer();
		TimerTask task = new TimerTask() {
			
		
			int timer = counter;
			@Override
			public void run() {
				if(index < timer) {
				index++;
				
				} else {
					if(!gameOver || !isDead) {
						++round; 
					}
					doneAnimating = true;
					resetMunchedTiles();
					setPosition(Tile.TILE_SIZE * 10, Tile.TILE_SIZE * 20);
					restart();
					wall.resetAllBreads();
					isDone = false;
					GamePanel.getGhosts()[0].setWasTouched(false);
					GamePanel.getGhosts()[1].setWasTouched(false);
					GamePanel.getGhosts()[2].setWasTouched(false);
					GamePanel.getGhosts()[3].setWasTouched(false);
					
					restarting = false;
					finished = false;
					finishedBread = false;
					wall.setRunning(false);
					wasReset = true;
	
					restarter.cancel();
				}
			}
			
		};
		
		restarter.schedule(task, 0, 1000);
    }
    
	private void counterDead(int counter) {
		
		if(counting ) {
			return;
		}
		counting = true;
		deadCounter = new Timer();
		TimerTask task = new TimerTask() {
			
		
			@Override
			public void run() {
				if(index < 9) {
				index++;
			
				doneAnimating = false;
				} else {
					doneAnimating = true;
					restart();
					deadCounter.cancel();
				}
			}
			
		};
		
		deadCounter.schedule(task, 0, 100);
	
	}
	public synchronized void restart() {
		if(doneAnimating) {
			setPosition(10 * Tile.TILE_SIZE, 20 * Tile.TILE_SIZE);
			Ghost.resetPosition();
			
			
			
			
			if(lives >= 1 || isDone) {
				gameOver = false;
				
			} else  {
				gameOver = true;

			}
			
			if(isDead) {
				--lives;
			}
			Player.isDead = false;
			Ghost.playerHasPressedKey = false;
			direction = 0;
			doneAnimating = false;
			index = 0;
			
			
			
			counting = false;

			restarted = true;
			setTimeCounter(-1);
			
		
			
			
		}
	}
    
    
	public World getWorld() {
	return this.world;
    }
    
  /*  public boolean canWalkThrough(int x, int y) {
	
    }
    */
    

}
