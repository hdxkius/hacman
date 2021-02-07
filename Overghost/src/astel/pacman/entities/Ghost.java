package astel.pacman.entities;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import astel.pacman.assets.Assets;
import astel.pacman.main.GamePanel;
import astel.pacman.main.World;
import astel.pacman.util.Tile;
import astel.pacman.util.Utils;

public class Ghost extends Entity{
    
	public static int passedGhost = 0;
	private boolean touched = false;
	private boolean killedAfterTimeOut = false;
	
	public static boolean stopMoving = false;
	private boolean returning = false;
	
	private static int timeCounter;
	private static boolean doneCountingForDeath = false;
	
	
	public static boolean playerHasPressedKey= false;
	private boolean wasKilled;
	private boolean wasExtended;
	private boolean hasStarted = false;
	
	private boolean wasInNest;
	private boolean blueMode;
	public static enum COLOR {RED, ORANGE, TEAL, ROSE};
	private Rectangle ghostTangle;
	private COLOR color;
    private boolean left = false, right = false, up = false, down = false;
    private int leftPos = 0, rightPos = 0, upPos = 0, downPos = 0;
    private boolean [][] map;
    private int ghostX , ghostY ;
    private int randomInt;
    boolean noOccassions = true;
	private int ranNum;
    private Player player;
    private Timer time;
    private Thread thread;

    private boolean shouldRightCageTrapped = false;
    private boolean shouldLeftCageTrapped = false;
    
    private World world;
    private int direction;
    private int oldXLeft, oldXRight, oldYUp, oldYDown;
    private int x, y;
    private boolean cycler = false;
    private Random random;
    
    private boolean coinFlipper = false;
    
    private int oldDirection;
    private Timer deathTimer;
    Runnable run = () -> {gateWatcher();};
    
    private boolean shouldDown_LeftUpBlocked = false;
    private boolean shouldRight_LeftUpBlocked = false;
    private boolean shouldRight_LeftDownBlocked = false;
    private boolean shouldUp_LeftDownBlocked = false;
    private boolean shouldDown_RightUpBlocked = false;
    private boolean shouldLeft_RightUpBlocked = false;
    private boolean shouldUp_RightDownBlocked = false;
    private boolean shouldLeft_RightDownBlocked = false;
    private boolean shouldUp_RightDownBlocked_HCase = false;
    
    private boolean shouldLeft_VCase = false;
    private boolean shouldRight_VCase = false;
    
    private boolean shouldUp_HCase = false;
    private boolean shouldDown_HCase = false;
    private boolean running = false;
    private boolean shouldRight_AllignCaseH = false;
    private boolean shouldUp_AllignCaseV = false;
    private boolean shouldDown = false;
    private boolean shouldRight = false;
    private boolean shouldLeft = false;
    private boolean shouldUp = false;
    private boolean doneBouncing = false;;
    private int bouncing = 0;
    
    public Ghost(int x, int y, Player player, int direction, COLOR color) {
	Assets.initGhosts();
	
	this.color = color;
	this.direction = direction;
	this.player = player;
	this.world = player.getWorld();
	this.x = x;
	this.y = y;

	map = Utils.copyTileArray(world.getTileMap());
	this.ghostTangle = new Rectangle(this.x, this.y, Tile.TILE_SIZE, Tile.TILE_SIZE);
	
	random = new Random();
	cycle();
	
	thread = new Thread(run);

	thread.start();
    }
    
    public void setWasExtended(boolean ex) {
    	this.wasExtended = ex;
    }
    
    public boolean wasExtended() {
    	return this.wasExtended;
    }


    private boolean isOutOfBounds() {
    	return (x/Tile.TILE_SIZE < 1 && y/Tile.TILE_SIZE == 13) ||
    			(x >= 20 * Tile.TILE_SIZE && y/Tile.TILE_SIZE == 13); 
    }
    
    private void escapeBounds() {
    	if(direction == 0) {
    		moveLeft();
    		
    		if(x == -32) {
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
    
    public Rectangle getGhostTangle() {
    	return ghostTangle;
    }
    
    public void setRectanglePosition(int x, int y) {
    	ghostTangle.setLocation(x, y);
    }
    
    private BufferedImage leftImage(boolean cycler) {
    	switch(this.color) {
    	
    	case RED:
    		if(cycler)
    			return Assets.redGhostLeft[0];
    		else
    			return Assets.redGhostLeft[1];
	
    	case ORANGE:
    		if(cycler)
    		    return Assets.orangeGhostLeft[0];
    		else
    		    return Assets.orangeGhostLeft[1];
    	case TEAL:
    		if(cycler)
    			return Assets.tealGhostLeft[0];
    		else
    			return Assets.tealGhostLeft[1];
    	case ROSE:
    		if(cycler)
    			return Assets.roseGhostLeft[0];
    		else
    			return Assets.roseGhostLeft[1];
    		default:
    			return null;
    	}
    }
    
    private BufferedImage rightImage(boolean cycler) {
    	switch(this.color) {
    	
    	case RED:
    		if(cycler)
    			return Assets.redGhostRight[0];
    		else
    			return Assets.redGhostRight[1];
	
    	case ORANGE:
    		if(cycler)
    		    return Assets.orangeGhostRight[0];
    		else
    		    return Assets.orangeGhostRight[1];
    	case TEAL:
    		if(cycler)
    			return Assets.tealGhostRight[0];
    		else
    			return Assets.tealGhostRight[1];
    	case ROSE:
    		if(cycler)
    			return Assets.roseGhostRight[0];
    		else
    			return Assets.roseGhostRight[1];
    		default:
    			return null;
    	}
    }
    
    private BufferedImage upImage(boolean cycler) {
    	switch(this.color) {
    	
    	case RED:
    		if(cycler)
    			return Assets.redGhostUp[0];
    		else
    			return Assets.redGhostUp[1];
	
    	case ORANGE:
    		if(cycler)
    		    return Assets.orangeGhostUp[0];
    		else
    		    return Assets.orangeGhostUp[1];
    	case TEAL:
    		if(cycler)
    			return Assets.tealGhostUp[0];
    		else
    			return Assets.tealGhostUp[1];
    	case ROSE:
    		if(cycler)
    			return Assets.roseGhostUp[0];
    		else
    			return Assets.roseGhostUp[1];
    		default:
    			return null;
    	}
    }
    
    private void cycle() {
	time = new Timer();
	time.schedule(new TimerTask() {

	    @Override
	    public void run() {
		cycler = !cycler;
	    }
	    
	}, 0, 100);
    }
    
    private BufferedImage blueGhost(boolean cycler) {
    	if(cycler) 
    		return Assets.blueModeGhosts[0];
    	else 
    		return Assets.blueModeGhosts[1];
    }
    
    private BufferedImage downImage(boolean cycler) {
    	switch(this.color) {
    	
    	case RED:
    		if(cycler)
    			return Assets.redGhostDown[0];
    		else
    			return Assets.redGhostDown[1];
	
    	case ORANGE:
    		if(cycler)
    		    return Assets.orangeGhostDown[0];
    		else
    		    return Assets.orangeGhostDown[1];
    	case TEAL:
    		if(cycler)
    			return Assets.tealGhostDown[0];
    		else
    			return Assets.tealGhostDown[1];
    	case ROSE:
    		if(cycler)
    			return Assets.roseGhostDown[0];
    		else
    			return Assets.roseGhostDown[1];
    		default:
    			return null;
    	}
    }
    

    
    
    
    private boolean isOnBlueMode() {
    	return blueMode;
    }
    
    private void locateNest(int x, int y) {
    	if(isOnXRemainder()) {
    		remainderXMoving();
    		return;
    	} else if(isOnYRemainder()) {
    		remainderYMoving();
    		return;
    	
    	} else if(isInFrontOfGate()) {
    		moveDown();
    	}
    	else if(isBlockedLeftUp()) {
    		if(direction == 0) {
    			moveDown();
    			return;
    		} else if(direction == 1) {
    			moveRight();
    			return;
    		}
    		
    	} else if(isBlockedLeftDown()) {
    		if(direction == 0) {
    			moveUp();
    			return;
    		} else if(direction == 3) {
    			moveRight();
    			return;
    		}
    	} else if(isBlockedRightUp()) {
    		if(direction == 2) {
    			moveDown();
    			return;
    		} else if(direction == 1) {
    			moveLeft();
    			return;
    		}
    	} else if(isBlockedRightDown()) {
    		if(direction == 2) {
    			moveUp();
    			return;
    		} else if(direction == 3) {
    			moveLeft();
    			return;
    		}
    	} else if(direction == 0 && isLeftBlocked()) {
    		if(isGateUpper() && !isUpBlocked()) {
    			moveUp();
    			return;
    		} else if(isGateDowner() && !isDownBlocked()) {
    			moveDown();
    			return;
    		} else if(!isUpBlocked()){
    			moveUp();
    		}
    	} else if(direction == 2 && isRightBlocked()) {
    		if(isGateUpper() && !isUpBlocked()) {
    			moveUp();
    			return;
    		} else if(isGateDowner() && !isDownBlocked()) {
    			moveDown();
    			return;
    		} else if(!isUpBlocked()){
    			moveUp();
    		}
    	} else if(direction == 1 && isUpBlocked()) {
    		if(isGateLefter() && !isLeftBlocked()) {
    			moveLeft();
    			return;
    		} else if(isGateRighter() && !isRightBlocked()) {
    			moveRight();
    			return;
    		} else {
    		
    			int k = random.nextInt(4);
    			if(k % 2 == 0 && !isLeftBlocked()) {
    				moveLeft();
    			} else if(!isRightBlocked()){
    				moveRight();
    			}
    		}
    		
    	} else if(direction == 3 && isDownBlocked()) {
    		if(isGateLefter()) {
    			moveLeft();
    			return;
    		} else if(isGateRighter()) {
    			moveRight();
    			return;
    		} else {
    			int k = random.nextInt(4);
    			if(k % 2 == 0 && !isLeftBlocked()) {
    				moveLeft();
    			} else if(!isRightBlocked()){
    				moveRight();
    			}
    			
    		}
    	} else if(direction == 1) {
    		if(!isLeftBlocked() && isGateLefter()) {
    			moveLeft();
    			return;
    		} else if(!isRightBlocked() && isGateRighter()) {
    			moveRight();
    			return;
    		} else if(isAlligningWithGateVertical()) {
    			moveUp();

    		} else if(!isUpBlocked()) {
    			moveUp();
    			return;
    		}
    	} else if(direction == 3) {
    		if(!isLeftBlocked() && isGateLefter()) {
    			moveLeft();
    			return;
    		} else if(!isRightBlocked() && isGateRighter()) {
    			moveRight();
    			return;
    		} else if(isAlligningWithGateVertical()) {
    			moveDown();
    			return;

    		} else if(!isDownBlocked()) {
    			moveDown();
    			return;
    		}
    	} else if(direction == 0) {
    		if(!isUpBlocked() && isGateUpper()) {
    			moveUp();
    			return;
    		} else if(!isDownBlocked() && isGateDowner()) {
    			moveDown();
    			return;
    		} else if(isAlligningWithGateHorizontal()) {
    			moveLeft();
    			return;
    		} else if(!isLeftBlocked()) {
    			moveLeft();
    			return;
    		}
    	} else if(direction == 2) {
    		if(!isUpBlocked() && isGateUpper()) {
    			moveUp();
    			return;
    		} else if(!isDownBlocked() && isGateDowner()) {
    			moveDown();
    			return;
    		} else if(isAlligningWithGateHorizontal()) {
    			moveRight();
    			return;
    		} else if(!isRightBlocked()) {
    			moveRight();
    			return;
    		}
    	}
    	else {
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
    	}
    }
    
    private boolean hasTouched() {
    	return playerIntersects()
    			&& !touched;
    }
    
    public synchronized void counter(int counter) {
    	if(touched) {
    		return;
    	}
    	timeCounter = counter;
    	deathTimer = new Timer();
    	
    	
    	
    	TimerTask task = new TimerTask() {
			@Override
			public void run() {
		
			
			
				if(timeCounter >= 0) {
					timeCounter--;
				
					stopMoving = true;
					player.playerShouldStopMoving(true);
					touched = false;
					
				}
				
		
				
				if(timeCounter == -1) {
					
					player.playerShouldStopMoving(false);
						stopMoving = false;
						player.setPlayerHasTouched(false);
						touched = true;
						running = false;
						deathTimer.cancel();
				}
		
			}
    		
    	};
    	
    	deathTimer.schedule(task, 0, 1000);
    	
    }
    
    private boolean isGateLefter() {
    	return getX()/Tile.TILE_SIZE > 10;
    }
    
    private boolean isGateRighter() {
    	return getX()/Tile.TILE_SIZE < 10;
    }
    
    private boolean isGateUpper() {
    	return getY()/Tile.TILE_SIZE > 11;
    }
    
    private boolean isGateDowner() {
    	return getY()/Tile.TILE_SIZE < 11;
    }
    
    private boolean isInFrontOfGate() {
    	return getX()/Tile.TILE_SIZE == 10 && getY()/Tile.TILE_SIZE == 11;
    }
    
    
    
    private boolean wasKilled() {
    	return player.getPlayerRectangle().intersects(getGhostTangle()) && blueMode;
    }
    
    private boolean isAlligningWithGateHorizontal() {
    	return getX() == 11;
    }
    
    private boolean isAlligningWithGateVertical() {
    	return getY() == 10;
    }
    
   
    
    private void closeSidePaths() {
    	map[13][4] = false;
    	map[16][4] = false;
    }
    
    private void openSidePaths() {
    	map[13][4] = true;
    	map[16][4] = true;
    }
    
    
    private void returnToNest() {
    	
    	
    	int destX, destY;
    	if(color == COLOR.TEAL) {
    		destX = 9;
    		destY = 13;
    		locateNest(destX, destY);
    	} else if(color == COLOR.ORANGE) {
    		destX = 11;
    		destY = 13;
    		locateNest(destX, destY);
    	} else if(color == COLOR.ROSE) {
    		destX = 10;
    		destY = 12;
    		locateNest(destX, destY);
    	} else if(color == COLOR.RED) {
    		destX = 10;
    		destY = 11;
    		locateNest(destX, destY);
    	} 

    		touched = false;
    		returning = true;
    		
	    	
    	
    }
    
    private boolean isCounting() {
    	return player.getTimeCounter() > 0;
    }
    

    private void blueModeCalculation() {
    	
    	if((blueMode && wasKilled) || isInNest()) {
    		openEntrance();
    	
    	} else {
    		closeEntrance();
    		
    	}

    	
    	if(hasStarted()) {
    		
    		

    		if((blueMode && wasKilled) || isInNest()) {
        		openEntrance();
        	
        	} else {
        		closeEntrance();
        	}
    		
    		if(isInNest()) {
    			wasInNest = true;
    			
    		} else {
    			wasInNest = false;
    		}
    		
    		if(!wasInNest && !isInNest()) {
    			blueMode = true;
    		} 
    		else 
    		if(wasKilled && isInNest()){
    			blueMode = false;
    			wasInNest = true;
    		}
    		
    		
        		hasStarted = true;
        		
        	
    	} 
    	
    	
    	else if(wasExtended()) {
    		
    		

    		if((blueMode && wasKilled) || isInNest()) {
        		openEntrance();
        	} else {
        		closeEntrance();
        	}
    	
    		
    		if(isInNest()) {
    			wasInNest = true;
    			
    		} else {
    			wasInNest = false;
    		}
    		
    		if(!wasInNest && !isInNest()) {
    			blueMode = true;
    		} 
    		else 
    	
    		
    		
    			setWasExtended(false);
    	}
    	
    	if(wasKilled && isInNest()){
			blueMode = false;
			wasInNest = true;
		
		}
    	
    	if(isOutOfBounds() && blueMode) {
    		openSidePaths();
    	} 
    	
    	/*if((isInNest() &&  (wasExtended() || hasStarted()))) {
    		wasInNest = true;
    		System.out.println("was in nest");
    		if(wasExtended()) {
    			System.out.println("was extended 1, was in nest");
    			player.setExtended(false);
    		}
    			
    		
    	} else {
    		wasNotInNest = true;
    		if(wasExtended()) {
    			System.out.println("was extended 1, was not in nest");
    			player.setExtended(false);
    		}
    	}
    	
    	if(((wasExtended() || hasStarted()) && !isInNest())) {
    			
    			player.setExtended(false);
    			wasNotInNest = true;
    			System.out.println("was extended 2");
    		

    	} else {

			player.setExtended(false);
			wasNotInNest = false;
    	}
    	
    	
    	if((wasNotInNest && !isInNest()) ) {
    		
    		if(wasExtended()) {
    			player.setExtended(false);
    			System.out.println("was extended 3");
    		}
    		
    		blueMode = true;
    		
    	} else if((isInNest() && wasKilled) || (wasInNest && wasExtended())) {
    		
    		if(wasExtended()) {
    			player.setExtended(false);
    			System.out.println("was extended 4");
    		}
    		blueMode = false;
    		wasKilled = false;
    		wasInNest = true;
    		
    	}*/
    	
    
    	
    	/*else
    	if((isInNest() && wasKilled) || isOver() || (wasInNest && !isInNest()) ||
    			(wasKilled && isInNest()) || isInNest() || wasInNest) {
    		blueMode = false;
    		wasKilled = false;
    	} */

   
    	
    
    }


    
    private boolean isOver() {
    	return player.getTimeCounter() <= 0;
    }
    
    private boolean hasStarted() {
    	return player.getTimeCounter() == 9 && !hasStarted;
    }
    
    
    
    public void drawGhost(Graphics g) {
    	

    	
    //	Graphics2D g2d = (Graphics2D) g;	
  //  	g2d.draw(ghostTangle);
    	
    	
    	if(!Player.gameOver && (!Player.finishedBread || player.getTimeCounter() == 0 || isInNest() || !isOnBlueMode())) {
	switch(getDirection()) {
	case 2:
	    g.drawImage(leftImage(cycler), x - 2, y - 2, 20, 20, null);
	    break;
	case 1:
	    g.drawImage(upImage(cycler), x - 2, y - 2, 20, 20, null);
	    break;
	case 0:
	    g.drawImage(rightImage(cycler), x - 2, y - 2, 20, 20, null);
	    break;
	case 3:
	    g.drawImage(downImage(cycler), x - 2, y - 2, 20, 20, null);
	    break;
	    default:
		System.out.println("Error with direction");
		break;
			} 
    	} else if((wasKilled || killedAfterTimeOut) && !stopMoving) {
    		g.drawImage(getEyes(), x - 2, y - 2, 20, 20, null);
    	
    	} 
    	
    	else if(!Player.gameOver && (player.getTimeCounter() < 3)) {
    	
    	g.drawImage(this.getWhiteGhost(cycler), x - 2, y - 2, 20, 20, null);	
    	
    	} 
    	
    	else if(isOnBlueMode() && !Player.gameOver) {
    		g.drawImage(blueGhost(cycler), x - 2, y - 2, 20, 20, null);
    	}
    	
	
    }
   
    
  
    

    private boolean isBlockedLeft_R(boolean higher) {
    	if(higher)
    	return !world.canAccess(map, (x - Tile.TILE_SIZE) - Tile.TILE_SIZE, y
    			- Tile.TILE_SIZE/2);
    	else
    		return !world.canAccess(map, (x - Tile.TILE_SIZE) - Tile.TILE_SIZE, y
        			+ Tile.TILE_SIZE/2);
    }
    
    private boolean isBlockedRight_R(boolean higher) {
    	if(higher)
    	return !world.canAccess(map, (x + Tile.TILE_SIZE) + Tile.TILE_SIZE, y
    			- Tile.TILE_SIZE/2);
    	else
    		return !world.canAccess(map, (x + Tile.TILE_SIZE) + Tile.TILE_SIZE, y
        			+ Tile.TILE_SIZE/2);
    }
    
    private boolean isBlockedUp_R(boolean righter) {
    	if(righter)
    		return !world.canAccess(map, x + Tile.TILE_SIZE/2, 
    				(y - Tile.TILE_SIZE/2) - Tile.TILE_SIZE);
    	else
    		return !world.canAccess(map, x - Tile.TILE_SIZE/2, 
    				(y - Tile.TILE_SIZE/2) - Tile.TILE_SIZE);
    }

    
    private boolean isBlockedLeftUp() {

    	
	return 	isLeftBlocked() && isUpBlocked();
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
    
    private boolean isUpBlocked() {
    	
    	if(y % Tile.TILE_SIZE != 0) {
    		return false;
    	}
    	
	return !world.canAccess(map, y - Tile.TILE_SIZE, x);
    }
    
    private boolean isDownBlocked() {
    	if(y % Tile.TILE_SIZE != 0) {
    		return false;
    	}
    	
	return !world.canAccess(map,  y + Tile.TILE_SIZE, x);
    }
    
    private boolean isLeftBlocked() {
    	
    	if(x % Tile.TILE_SIZE != 0) {
    		return false;
    	}
    	
 	return !world.canAccess(map, y, x - Tile.TILE_SIZE );
     }
    
    private boolean isRightBlocked() {
    	
    	
    	if(x % Tile.TILE_SIZE != 0) {
    		return false;
    	}
    	
 	return !world.canAccess(map,y , x + Tile.TILE_SIZE );
     }
    
    private boolean isPlayerLefter() {
	return player.getX() < getX();
    }
    
    private boolean isPlayerRighter() {
	
	return player.getX() > getX();
    }
    
    private boolean isPlayerUpper() {
	return player.getY() < getY();
    }
    
    private boolean isPlayerDowner() {
	return player.getY() > getY();
    }
    
    private void moveLeft() {
    	if(stopMoving) {
    		return;
    	}
    	
    	if(isOnBlueMode() && (x % Tile.TILE_SIZE == 0) && wasKilled && !isInNest()) {
    	  		this.oldXLeft = this.x;
    	  		x -= Tile.TILE_SIZE;
    	  		if(x < oldXLeft) {
    	  		    setDirection(0);
    	  		    left = true;
    	  		}
    	  	    
    	  	
    	  	return;
    	} else {
    	
    	if(!player.hasEatenBread()) {
	this.oldXLeft = this.x;
	x -= 2;
	if(x < oldXLeft) {
	    setDirection(0);
	    left = true;
	}
    	} else {
    		this.oldXLeft = this.x;
    		x -= 2;
    		if(x < oldXLeft) {
    		    setDirection(0);
    		    left = true;
    		}
    	}
    	}
    }
    
    private void moveRight() {
    	
    	if(stopMoving) {
    		return;
    	}
    	
    	if(isOnBlueMode() && (x % Tile.TILE_SIZE == 0) && wasKilled && !isInNest()) {
    	  		this.oldXRight = this.x;
    	  		x += Tile.TILE_SIZE;
    	  		if(x > oldXRight) {
    	  		    setDirection(2);
    	  		    right = true;
    	  		}
    	  	    	
    	  	
    	  	return;
    	
    	} else {
    	if(!player.hasEatenBread()) {
	this.oldXRight = this.x;
	x += 2;
	if(x > oldXRight) {
	    setDirection(2);
	    right = true;
	}
    	} else {
    		this.oldXRight = this.x;
    		x += 2;
    		if(x > oldXRight) {
    		    setDirection(2);
    		    right = true;
    		}
    	}
    	}
    }
    
    private void moveUp() {
    	
    	if(stopMoving) {
    		return;
    	}
    	if(isOnBlueMode() && (y % Tile.TILE_SIZE == 0) && wasKilled && !isInNest()) {
    			this.oldYUp = this.y;
    			y -=Tile.TILE_SIZE;
    			if(y < oldYUp) {
    			    setDirection(1);
    			    up = true;
    			}
    		return;
    	}
    	else {
    	if(!player.hasEatenBread()) {
	this.oldYUp = this.y;
	y -=2;
	if(y < oldYUp) {
	    setDirection(1);
	    up = true;
	}
    	} else {
    		this.oldYUp = this.y;
    		y -=2;
    		if(y < oldYUp) {
    		    setDirection(1);
    		    up = true;
    		}
    	}
    	}
    }
    
    private void moveDown() {
    	
    	if(stopMoving) {
    		return;
    	}
    	if(isOnBlueMode() && (y % Tile.TILE_SIZE == 0) && wasKilled && !isInNest()) {
    			this.oldYDown = this.y;
    			y += Tile.TILE_SIZE;
    			if(y > oldYDown) {
    			    setDirection(3);
    			    down = true;
    			 
    		    	}
    		return;
    	} else {
    		
    	
    	if(!player.hasEatenBread()) {
	this.oldYDown = this.y;
	y += 2;
	if(y > oldYDown) {
	    setDirection(3);
	    down = true;
	}
    	} else {
    		this.oldYDown = this.y;
    		y += 2;
    		if(y > oldYDown) {
    		    setDirection(3);
    		    down = true;
    		}
    	}
    	}
    }
    
    
    

    
    private void moveNormal() {
    	
    	
    
 	   /* boolean canLeft = world.canAccess(world.getTileMap(), ghostX - Tile.TILE_SIZE,
	    ghostY);
    boolean canRight = world.canAccess(world.getTileMap(), ghostX + Tile.TILE_SIZE,
	    ghostY);
    boolean canUp = world.canAccess(world.getTileMap(), ghostX, 
	    ghostY - Tile.TILE_SIZE);
    boolean canDown = world.canAccess(world.getTileMap(), ghostX,
	    ghostY + Tile.TILE_SIZE);*/
    	
    	
    	 if(isOnXRemainder()) {
    		 remainderXMoving();
    		 return;
    	 } else if(isOnYRemainder()) {
    		 remainderYMoving(); 
    		 return;
    	 }/* else if(allignsVertical()) {
    		 if(direction == 0) {
    			 if(!isLeftBlocked())
    			 moveLeft();
    			 return;
    		 } else if(direction == 2) {
    			 if(!isRightBlocked())
    			 moveRight();
    			 return;
    		 } else if(isBlockedLeftUp()) {
    			 
    			 if(direction == 0) {
    				 moveDown();
    				 return;
    			 } else if(direction == 1) {
    				 moveRight();
    				 return;
    			 }
    		 } else if(isBlockedLeftDown()) {
    			 if(direction == 0) {
    				 moveUp();
    			 } else if(direction == 3) {
    				 moveRight();
    			 }
    		 } else if(isBlockedRightUp()) {
    			 if(direction == 2) {
    				 moveDown();
    				 return;
    			 } else if(direction == 1) {
    				 moveLeft();
    				 return;
    			 }
    		 } else if(isBlockedRightDown()) {
    			 if(direction == 2) {
    				 moveUp();
    				 return;
    			 } else if(direction == 3) {
    				 moveLeft();
    				 return;
    			 }
    		 }
    	 }*/
    	 else if(isBlockedLeftUp()) {
    		 if(direction == 0) {
    			 moveDown();
    			 return;
    		 } else if(direction == 1) {
    			 moveRight();
    			 return;
    		 } 
    	 } else if(isBlockedLeftDown()) {
    		 if(direction == 3) {
    			 moveRight();
    			 return;
    		 } else if(direction == 0) {
    			 moveUp();
    			 return;
    		 }
    		 
    	 } else if(isBlockedRightUp()) {
    		 
    		 if(direction == 2) {
    			 moveDown();
    			 return;
    		 } else if(direction == 1) {
    			 moveLeft();
    			 return;
    		 }
    		 
    	 } else if(isBlockedRightDown()) {
    		 if(direction == 2) {
    			 moveUp();
    			 return;
    		 } else if(direction == 3) {
    			 moveLeft();
    			 return;
    		 }
    	 }
    	 
    	 else 
    	if(!isLeftBlocked() && isPlayerLefter()) {
    		moveLeft();
    		return;
    	} else if(!isRightBlocked() && isPlayerRighter()) {
    		moveRight();
    		return;
    	}
    	
    	if(!isUpBlocked() && isPlayerUpper()) {
    		moveUp();
    		return;
    	} else if(!isDownBlocked() && isPlayerDowner()) {
    		moveDown();
    		return;
    		}
    	
    }
    
    //LeftUpBlocked occasion
    
    
    private boolean isLeftUpBlocked_PlayerRighterUp() {
    	return isBlockedLeftUp() && (isPlayerUpper() && isPlayerRighter())
    			|| this.shouldRight_LeftUpBlocked;
    }
    
    private boolean allignsVertical() {
    	return getX()/Tile.TILE_SIZE == player.getX()/Tile.TILE_SIZE;
    }
    
    private boolean allignsHorizontal() {
    	return getY()/Tile.TILE_SIZE == player.getY()/Tile.TILE_SIZE;
    }
    
    //LeftDownBlocked occasion
    private boolean isLeftDownBlocked_PlayerLefterDown() {
    	return isBlockedLeftDown() && (isPlayerDowner() && 
    			isPlayerLefter()) || this.shouldUp_LeftDownBlocked;
    }
    
    private boolean isLeftDownBlocked_PlayerRighterDown() {
    	return isBlockedLeftDown() && (isPlayerDowner() && isPlayerRighter())
    			|| this.shouldRight_LeftDownBlocked;
    }
    
    //RightUpBlocked occasion
   private boolean isRightUpBlocked_PlayerRighterUp() {
	   return isBlockedRightUp() && (isPlayerUpper() && 
   			isPlayerRighter()) || this.shouldDown_RightUpBlocked;
   }
   
   private boolean isRightUpBlocked_PlayerLefterUp() {
	   return isBlockedRightUp() && (isPlayerUpper() && isPlayerLefter())
   			|| this.shouldLeft_RightUpBlocked;
   }
   
   //RightDownBlocked occasion
   private boolean isRightDownBlocked_PlayerRighterDown() {
	   return isBlockedRightDown() && (isPlayerDowner() && 
   			isPlayerRighter()) || this.shouldUp_RightDownBlocked;
   }
   
   private boolean isRightDownBlocked_PlayerLefterDown() {
	 return  isBlockedRightDown() && (isPlayerDowner() && isPlayerLefter())
		|| this.shouldLeft_RightDownBlocked;
   }
    
    
    public void checkLeftUpBlocked() {
    	if(isLeftUpBlocked()) {
    	//Left
    		shouldDown_LeftUpBlocked = true;
    	
    			if(!isDownBlocked()) {
    				
    				noOccassions = false;
    				moveDown();
    				
    			} else {
    				this.shouldDown_LeftUpBlocked = false;
    				noOccassions = true;
    			}
    			
    			
    	
    	} else if(isLeftUpBlocked_PlayerRighterUp()) {
    		
    		//Right
    		shouldRight_LeftUpBlocked = true;
    		if(!isRightBlocked()) {
    			noOccassions = false;
    			moveRight();
    		} else {
    			this.shouldRight_LeftUpBlocked = false;
    			noOccassions = true;
    		}
    		
    		//LeftDownBlocked
    		//Right
    	} 
    
    }
    
    public void checkLeftDownBlocked() {
    	
    	if(isLeftDownBlocked_PlayerLefterDown()) {
    	//Left
    		shouldUp_LeftDownBlocked = true;
    	
    			if(!isUpBlocked()) {
    				
    				noOccassions = false;
    				moveUp();
    				
    			} else {
    				this.shouldUp_LeftDownBlocked = false;
    				noOccassions = true;
    			}
    			
    			
    	
    	} else if(isLeftDownBlocked_PlayerRighterDown()) {
    		
    		//Right
    		shouldRight_LeftDownBlocked = true;
    		if(!isRightBlocked()) {
    			noOccassions = false;
    			moveRight();
    		} else {
    			this.shouldRight_LeftDownBlocked = false;
    			noOccassions = true;
    		}
    		
    		//LeftDownBlocked
    		//Right
    	} 
    	
    }
    
    public void checkRightUpBlocked() {
    	if(isRightUpBlocked_PlayerRighterUp()) {
    	//Left
    		shouldDown_RightUpBlocked = true;
    	
    			if(!isDownBlocked()) {
    				
    				noOccassions = false;
    				moveDown();
    				
    			} else {
    				this.shouldDown_RightUpBlocked = false;
    				noOccassions = true;
    			}
    			
    			
    	
    	} else if(isRightUpBlocked_PlayerLefterUp()) {
    		
    		//Right
    		shouldLeft_RightUpBlocked = true;
    		if(!isLeftBlocked()) {
    			noOccassions = false;
    			moveLeft();
    		} else {
    			this.shouldLeft_RightUpBlocked = false;
    			noOccassions = true;
    		}
    		
    		//LeftDownBlocked
    		//Right
    	} 
    }
    
    public void checkRightDownBlocked() {
    	if(isRightDownBlocked_PlayerRighterDown()) {
    	//Left
    		shouldUp_RightDownBlocked = true;
    	
    			if(!isUpBlocked()) {
    				
    				noOccassions = false;
    				moveUp();
    				
    			} else {
    				this.shouldUp_RightDownBlocked = false;
    				noOccassions = true;
    			}
    			
    			
    	
    	} else if(isRightDownBlocked_PlayerLefterDown()) {
    		
    		//Right
    		shouldLeft_RightDownBlocked = true;
    		if(!isLeftBlocked()) {
    			noOccassions = false;
    			moveLeft();
    		} else {
    			this.shouldLeft_RightDownBlocked = false;
    			noOccassions = true;
    		}
    		
    		//LeftDownBlocked
    		//Right
    	} 
    }
    
  
    

    
    private void remainderMoving() {
    	switch(direction) {
		case 0:
			moveLeft();
			break;
		case 1:
			moveUp();
			break;
		case 2:
			moveRight();
			break;
		case 3:
			moveDown();
			break;
    	}
    }
    
    private void remainderXMoving() {
    	switch(direction) {
		case 0:
			moveLeft();
			break;
		case 2:
			moveRight();
			break;
    	}
    }
    
    private void remainderYMoving() {
    	switch(direction) {
		case 1:
			moveUp();
			break;
		case 3:
			moveDown();
			break;
    	}
    }
    
    public boolean wasKilledGhost() {
    	return wasKilled;
    }
    
    public boolean doneCountingDeathGhost() {
    	return doneCountingForDeath;
    }
 
    
    private synchronized void count() {
    	
    	
    	if(running) {
    		return;
    	}
    	
    	running = true;
    	counter(1);
    	
    	
    	
    }
    
    private BufferedImage getEyes() {
    	switch(direction) {
    	case 0:
    		return Assets.eyes[0];
    	case 1:
    		return Assets.eyes[1];
    	case 2:
    		return Assets.eyes[2];
    	case 3:
    		return Assets.eyes[3];
    		default:
    			return null;
    	}
    }
    
    public void setPosition(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    public static void resetPosition() {
    	
    	GamePanel.getGhosts()[0].
    	setPosition(10 * Tile.TILE_SIZE, 10 * Tile.TILE_SIZE);
    	GamePanel.getGhosts()[1].
    	setPosition(11 * Tile.TILE_SIZE, 13 * Tile.TILE_SIZE);
    	GamePanel.getGhosts()[2].
    	setPosition(9 * Tile.TILE_SIZE, 13 * Tile.TILE_SIZE);
    	GamePanel.getGhosts()[3].
    	setPosition(10 * Tile.TILE_SIZE, 12 * Tile.TILE_SIZE);
		
    }
    
    private void resetPos() {
    	switch(color) {
    	case RED:
        	setPosition(10 * Tile.TILE_SIZE, 10 * Tile.TILE_SIZE);
        	break;
    	case ORANGE:
        	setPosition(11 * Tile.TILE_SIZE, 13 * Tile.TILE_SIZE);
        	break;
    	case TEAL:
    		setPosition(9 * Tile.TILE_SIZE, 13 * Tile.TILE_SIZE);
    		break;
    	case ROSE:
        	setPosition(10 * Tile.TILE_SIZE, 12 * Tile.TILE_SIZE);
        	break;
    	}
    }
    
    public void moveHardMode() {
    	
	if(player.getDoneAnimating()) {

    		
    		player.setPlayerDoneAnimating(false);
    		Player.isDead = false;
    		Ghost.playerHasPressedKey = false;
    	}
    	/*if(!isInNest()) {
    		
    		closeEntrance();
    	} else {
    		escapeNest();
    		
    		}
    		
    	*/
    	
    	
    	if((!Player.finished && !Player.isDead) && Ghost.playerHasPressedKey) {
    	
    		
    	if(wasKilled()) {
    		
    		wasKilled = true;

    	}
    	
    	if(playerIntersects() && !blueMode && !Player.isDead && !isInNest()) {
    		
    		Player.isDead = true;
    		
    	}
    	
    	if(Player.restarted) {
    		doneBouncing = false;
    		Player.restarted = false;
    	}
    

    	if(hasTouched() && wasKilled && !running && !touched && !returning && playerIntersects()) {
    		count();
    	}
    	
    	
    	
    	
  
    	if((isCounting() || wasExtended()) && !isOver()) {
    		blueModeCalculation();
    		
    		
    	} else if(wasKilled && isOver()) {
    		blueMode = true;
    	} else if(blueMode && wasKilled) {
    		returnToNest();
    		
    	}
    		else if(isOver()){
    			
    		player.setExtended(false);
    		wasKilled = false;
    		blueMode = false;
    		hasStarted = false;
    	}
    	
    	
    		
    	if(!isOutOfBounds()) {
    	
    	//if(!playerIntersects()) {
    		
    		if(!isOnRemainder()) {
    			if(getX()/Tile.TILE_SIZE == 0 && getY()/Tile.TILE_SIZE == 13) {
    				this.x = Tile.TILE_SIZE * 20;
    				return;
    			} else if(getX()/Tile.TILE_SIZE == 0 && getY()/Tile.TILE_SIZE == 13) {
    				this.x = 0;
    			}
    			} else if(wasKilled || (isOver() && touched)) {
    				returnToNest();
    				killedAfterTimeOut = true;
    			}
    	if(!isOnXRemainder() && !isOnYRemainder()) {
    		hardMode();
    	} else if(isOnXRemainder()){
    		
    		switch(direction) {
    		case 0:
    			moveLeft();
    			break;
    		case 2:
    			moveRight();
    			break;
    		
    		}
    	} else if(isOnYRemainder()) {
    		
    		switch(direction) {
    		case 1:
    			moveUp();
    			break;
    		case 3:
    			moveDown();
    			break;
    		}
    	}
    	
    	setRectanglePosition(getX(), getY());
    	//}
    	
    	
    	} else {
    		escapeBounds();
    			}
    		}
    }
    
    private void hardMode() {
    	
    
    	if(wasKilled) {
    		openEntrance();
    		 returnToNest();
    		 if(isInNest()) {
    			 openSidePaths();
    			 blueMode = false;
    			 wasKilled = false;
    			 player.setPlayerHasTouched(false);
    		 }
    		 return;
    	} else
    	if(!isDoneBouncing() && isInNest()) {
    		touched = false;
    		flipBouncingMovement();
    		return;
    	} 
    	else if(isInNest()) {
    		openSidePaths();
    		openEntrance();
    		escapeNest();
    		returning = false;
    		killedAfterTimeOut = false;
    		 player.setPlayerHasTouched(false);
    		return;
    	} 
    		else
    	if(isPlayerNear()) {
    		if(isOnXRemainder()) {
    			remainderXMoving();
    			return;
    		} else if(isOnYRemainder()) {
    			remainderYMoving();
    			return;
    		} else {
    			moveNormal();
    			return;
    		}
    	}
    	
    	if(isBlockedLeftUp()) {

    		if(direction == 1) {
    			moveRight();
    			return;
    		} else if(direction == 0) {
    			moveDown();
    			return;
    		}
    	
    	} else
    	
    	if(isBlockedLeftDown()) {

    		if(direction == 3) {
    			moveRight();
    			return;
    		} else if(direction == 0) {
    			moveUp();
    			return;
    		}
    	
    	}
    	else
    	if(isBlockedRightUp()) {

    		if(direction == 1) {
    			moveLeft();
    			return;
    		} else if(direction == 2) {
    			moveDown();
    			return;
    		}
    	
    	}
    	else
    	if(isBlockedRightDown()) {

    		if(direction == 3) {
    			moveLeft();
    			return;
    		} else if(direction == 2) {
    			moveUp();
    			return;
    		}
    	}else if(allignsVertical()) {
    		if(direction == 1 && !isUpBlocked()) {
    			moveUp();
    			return;
    		} else if(direction == 3 && !isDownBlocked()) {
    			moveDown();
    			return;
    		} else if(direction == 2 && !isRightBlocked()) {
    			moveLeft();
    			return;
    		} else if(direction == 0 && !isLeftBlocked()) {
    			moveLeft();
    			return;
    		}
    	} 
        	
        
        else if(allignsHorizontal()) {
    		if(direction == 1 && !isUpBlocked()) {
    			moveUp();
    			return;
    		} else if(direction == 3 && !isDownBlocked()) {
    			moveDown();
    			return;
    		} else if(direction == 2 && !isRightBlocked()) {
    			moveLeft();
    			return;
    		} else if(direction == 0 && !isLeftBlocked()) {
    			moveLeft();
    			return;
    		}
    	}  
        
    	
    	
    	else

    if(direction == 0 && !isLeftBlocked()) {    	
    	if(isPlayerUpper() && !isUpBlocked()) {
    		moveUp();
    		return;
    	} else if(isPlayerDowner() && !isDownBlocked()) {
    		moveDown();
    		return;
    	} else {
    		if(!isLeftBlocked())
    	moveLeft();
    	return;
    	}
    } else if(direction == 1 && !isUpBlocked()) {
        	
       	if(isPlayerLefter() && !isLeftBlocked()) {
    		moveLeft();
    		return;
    	} else if(isPlayerRighter() && !isRightBlocked()) {
    		moveRight();
    		return;
    	} else {
    	if(!isUpBlocked())
    	moveUp();
    	return;
    	}
    } else if(direction == 2 && !isRightBlocked()) {
    	
       	if(isPlayerUpper() && !isUpBlocked()) {
    		moveUp();
    		return;
    	} else if(isPlayerDowner() && !isDownBlocked()) {
    		moveDown();
    		return;
    	} else {
    	if(!isRightBlocked())
    	moveRight();
    	return;
    	}
    } else if(isPlayerDowner() && !isDownBlocked()) {
    	
    	if(isPlayerLefter() && !isLeftBlocked()) {
    		moveLeft();
    		return;
    	} else if(isPlayerRighter() && !isRightBlocked()) {
    		moveRight();
    		return;
    	} else {
    	if(!isDownBlocked())
    	moveDown();
    	return;
    	}
    	
    } else if(isLeftBlocked()) {
    	
    	if(isPlayerUpper() && !isUpBlocked()) {
    		moveUp();
    		return;
    	} else if(isPlayerDowner() && !isDownBlocked()) {
    		moveDown();
    		return;
    	}
    } else if(isRightBlocked()) {
    	
    	if(isPlayerUpper() && !isUpBlocked()) {
    		moveUp();
    		return;
    	} else if(isPlayerDowner() && !isDownBlocked()) {
    		moveDown();
    		return;
    	}
    } else if(isUpBlocked()) {

    	if(isPlayerRighter() && !isRightBlocked()) {
    		moveRight();
    		return;
    	} else if(isPlayerLefter() && !isLeftBlocked()) {
    		moveLeft();
    		return;
    	}
    } else if(isDownBlocked()) {
    
    	if(isPlayerRighter() && !isRightBlocked()) {
    		moveRight();
    		return;
    	} else if(isPlayerLefter() && !isLeftBlocked()) {
    		moveLeft();
    		return;
    	} 
    }
    
    else if(player.isOutOfBounds()) {
    	if(direction == 1 && !isUpBlocked()) {
			moveUp();
			return;
		} else if(direction == 3 && !isDownBlocked()) {
			moveDown();
			return;
		}
    }
    

    	
    }
    
    public void moveGhostFriendly() { 
    
    	    	
    	if(player.getDoneAnimating()) {

    		
    		player.setPlayerDoneAnimating(false);
    		Player.isDead = false;
    		Ghost.playerHasPressedKey = false;
    	}
    	/*if(!isInNest()) {
    		
    		closeEntrance();
    	} else {
    		escapeNest();
    		
    		}
    		
    	*/
    	
    	
    	if((!Player.finished && !Player.isDead) && Ghost.playerHasPressedKey) {
    	
    		
    	if(wasKilled()) {
    		
    		wasKilled = true;

    	}
    	
    	if(playerIntersects() && !blueMode && !Player.isDead && !isInNest()) {
    		
    		Player.isDead = true;
    		
    	}
    	
    	if(Player.restarted) {
    		doneBouncing = false;
    		Player.restarted = false;
    	}
    

    	if(hasTouched() && wasKilled && !running && !touched && !returning && playerIntersects()) {
    		count();
    	}
    	
    	
    	
    	
  
    	if((isCounting() || wasExtended()) && !isOver()) {
    		blueModeCalculation();
    		
    		
    	} else if(wasKilled && isOver()) {
    		blueMode = true;
    	} else if(blueMode && wasKilled) {
    		returnToNest();
    		
    	}
    		else if(isOver()){
    			
    		player.setExtended(false);
    		wasKilled = false;
    		blueMode = false;
    		hasStarted = false;
    	}
    	
    	
    		
    	if(!isOutOfBounds()) {
    	
    	//if(!playerIntersects()) {
    		
    		if(!isOnRemainder()) {
    			if(getX()/Tile.TILE_SIZE == 0 && getY()/Tile.TILE_SIZE == 13) {
    				this.x = Tile.TILE_SIZE * 20;
    				return;
    			} else if(getX()/Tile.TILE_SIZE == 0 && getY()/Tile.TILE_SIZE == 13) {
    				this.x = 0;
    			}
    			} else if(wasKilled || (isOver() && touched)) {
    				returnToNest();
    				killedAfterTimeOut = true;
    			}
    	if(!isOnXRemainder() && !isOnYRemainder()) {
    	moveGhostNew();
    	
    	} else if(isOnXRemainder()){
    		
    		switch(direction) {
    		case 0:
    			moveLeft();
    			break;
    		case 2:
    			moveRight();
    			break;
    		
    		}
    	} else if(isOnYRemainder()) {
    		
    		switch(direction) {
    		case 1:
    			moveUp();
    			break;
    		case 3:
    			moveDown();
    			break;
    		}
    	}
    	
    	setRectanglePosition(getX(), getY());
    	//}
    	
    	
    	} else {
    		escapeBounds();
    			}
    		}
    	}
    	
    
    public void setWasTouched(boolean b) {
    	this.touched = b;
    }
    	
    
    	
    
    
    public boolean playerIntersects() {
    	return player.getPlayerRectangle().intersects(this.getGhostTangle());
    }
    

    
    
 
   
 
	
	private void gateWatcher() {
		time = new Timer();
		time.schedule(new TimerTask() {

			@Override
			public void run() {
				
				if(Ghost.this.hasPassedGate()) {
					closeEntrance();
					
				} else {
					return;
				}
			}
			
		}, 0, 1000);
	}
	
	private boolean isOnRemainder() {
		return getX() % Tile.TILE_SIZE != 0 && getY() % Tile.TILE_SIZE != 0;
	}
	
	private boolean isOnXRemainder() {
		return getX() % Tile.TILE_SIZE != 0;
	}
	
	private boolean isOnYRemainder() {
		return getY() % Tile.TILE_SIZE != 0;
	}

	

    
    private boolean isLeftUpBlocked() {
    	return isLeftBlocked() && isUpBlocked();
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



    @Override
    public int getDirection() {
	// TODO Auto-generated method stub
	return direction;
    }



    @Override
    public void setDirection(int direction) {
    this.oldDirection = this.direction;
	this.direction = direction;
    }
    
 
    private boolean isDoneBouncing() {
    	return doneBouncing;
    }
    
    private void moveFromDirection() {
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
    		default:
    			return;
    	}
    }
    
    private void moveGhostNew() {
    	
   
    	int j = random.nextInt(3); 
    	if(wasKilled) {
    		openEntrance();
    		 returnToNest();
    		 if(isInNest()) {
    			 openSidePaths();
    			 blueMode = false;
    			 wasKilled = false;
    			 player.setPlayerHasTouched(false);
    		 }
    		 return;
    	} else
    	if(!isDoneBouncing() && isInNest()) {
    		touched = false;
    		flipBouncingMovement();
    		return;
    	} 
    	else if(isInNest()) {
    		openSidePaths();
    		openEntrance();
    		escapeNest();
    		returning = false;
    		killedAfterTimeOut = false;
    		 player.setPlayerHasTouched(false);
    		return;
    	} 
    		else
    	if(isPlayerNear()) {
    		if(isOnXRemainder()) {
    			remainderXMoving();
    			return;
    		} else if(isOnYRemainder()) {
    			remainderYMoving();
    			return;
    		} else {
    			moveNormal();
    			return;
    		}
    	}
    	
    	if(isBlockedLeftUp()) {

    		if(direction == 1) {
    			moveRight();
    			return;
    		} else if(direction == 0) {
    			moveDown();
    			return;
    		}
    	
    	} else
    	
    	if(isBlockedLeftDown()) {

    		if(direction == 3) {
    			moveRight();
    			return;
    		} else if(direction == 0) {
    			moveUp();
    			return;
    		}
    	
    	}
    	else
    	if(isBlockedRightUp()) {

    		if(direction == 1) {
    			moveLeft();
    			return;
    		} else if(direction == 2) {
    			moveDown();
    			return;
    		}
    	
    	}
    	else
    	if(isBlockedRightDown()) {

    		if(direction == 3) {
    			moveLeft();
    			return;
    		} else if(direction == 2) {
    			moveUp();
    			return;
    		}
    	}
    	else

    if(direction == 0 && !isLeftBlocked()) {    	
    	if(j == 1 && !isUpBlocked()) {
    		moveUp();
    		return;
    	} else if(j == 2 && !isDownBlocked()) {
    		moveDown();
    		return;
    	} else {
    		if(!isLeftBlocked())
    	moveLeft();
    	return;
    	}
    } else if(direction == 1 && !isUpBlocked()) {
        	
       	if(j == 1 && !isLeftBlocked()) {
    		moveLeft();
    		return;
    	} else if(j == 2 && !isRightBlocked()) {
    		moveRight();
    		return;
    	} else {
    	if(!isUpBlocked())
    	moveUp();
    	return;
    	}
    } else if(direction == 2 && !isRightBlocked()) {
    	
       	if(j == 1 && !isUpBlocked()) {
    		moveUp();
    		return;
    	} else if(j == 2 && !isDownBlocked()) {
    		moveDown();
    		return;
    	} else {
    	if(!isRightBlocked())
    	moveRight();
    	return;
    	}
    } else if(direction == 3 && !isDownBlocked()) {
    	
    	if(j == 1 && !isLeftBlocked()) {
    		moveLeft();
    		return;
    	} else if(j == 2 && !isRightBlocked()) {
    		moveRight();
    		return;
    	} else {
    	if(!isDownBlocked())
    	moveDown();
    	return;
    	}
    	
    } else if(isLeftBlocked()) {
    	
    	if(j % 2 == 0 && !isUpBlocked()) {
    		moveUp();
    		return;
    	} else if(j % 2 != 0 && !isDownBlocked()) {
    		moveDown();
    		return;
    	}
    } else if(isRightBlocked()) {
    	
    	if(j % 2 == 0 && !isUpBlocked()) {
    		moveUp();
    		return;
    	} else if(j % 2 != 0 && !isDownBlocked()) {
    		moveDown();
    		return;
    	}
    } else if(isUpBlocked()) {

    	if(j % 2 == 0 && !isRightBlocked()) {
    		moveRight();
    		return;
    	} else if(j % 2 != 0 && !isLeftBlocked()) {
    		moveLeft();
    		return;
    	}
    } else if(isDownBlocked()) {
    
    	if(j % 2 == 0 && !isRightBlocked()) {
    		moveRight();
    		return;
    	} else if(j % 2 != 0 && !isLeftBlocked()) {
    		moveLeft();
    		return;
    	}
    } else {
		System.out.println(color + " in nothing");

    }
    	
    	
    	
    	
    }
  
   
    
  
    

    
  

    
    private void closeEntrance() {
    	
    		this.map[11][10] = false;
    }
    
    private void openEntrance() {
    	
    	this.map[11][10] = true;
    }
    
    
    
    public static boolean allGhostsPassed(Ghost[] ghosts) {
    	int pass = 0;
    	for(int i = 0; i < ghosts.length; i++) {
    		if(ghosts[i].getY()/Tile.TILE_SIZE < 11) {
    			++pass;
    		}
    	}
    	
    	return pass == 4;
    }
    
    private boolean isGateClosed() {
    	return map[10][11] = false;
    }
    
    private final boolean hasPassedGate() {
    	return !isInNest() && (getY()/Tile.TILE_SIZE < 11);
    }
    
    
    private boolean isPlayerNear() {
    	int playerX = player.getX()/Tile.TILE_SIZE;
    	int playerY = player.getY()/Tile.TILE_SIZE;
    	int gX = getX() /Tile.TILE_SIZE;
    	int gY = getY() / Tile.TILE_SIZE;
    	if(isPlayerLefter()) {
    		if((gX - playerX) == 5) {
    			return true;
    		}
    	} else if(isPlayerRighter()) {
    		if((playerX - gX) == 5) {
    			return true;
    		}
    	} else if(isPlayerUpper()) {
    		if((gY - playerY) == 5) {
    			return true;
    		}
    	} else if(isPlayerDowner()) {
    		if((playerY - gY == 5)) {
    			return true;
    		}
    	}
    	
    	return false;
     }
    
    private boolean isInNest() {
    	return (getX()/Tile.TILE_SIZE == 9 || getX()/Tile.TILE_SIZE == 10 ||
				getX()/Tile.TILE_SIZE == 11) && 
				(getY()/Tile.TILE_SIZE == 12 || getY()/Tile.TILE_SIZE == 13);
    }
    
    public static boolean areGhostsInNest(Ghost[] ghosts) {
    	for(int i = 0; i < ghosts.length; i++) {
    		if((ghosts[i].getX()/Tile.TILE_SIZE == 9 || ghosts[i].getX() == 10 ||
    				ghosts[i].getX()/Tile.TILE_SIZE == 11) && 
    				(ghosts[i].getY() == 11 || ghosts[i].getY() == 12)) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public void escapeNest() {
    	
    	openEntrance();
    	
    	
    	if(isOnXRemainder()) {
    		System.out.println(color + " on x remainder");
    		remainderXMoving();
    	} else if(isOnYRemainder()) {
    		System.out.println(color + " on y remainder");
    		remainderYMoving();
    	} else {
    		if(isBlockedLeftUp()) {
    		
    			if(direction == 0 || direction == 1) {
    				moveRight();
    				return;
    			} else if(direction == 2 || direction == 3) {
    				moveRight();
    				return;
    			}
    		
    		} else if(isBlockedLeftDown()) {
    			
    			if(direction == 0 || direction == 3) {
    				moveUp();
    				return;
    			}else if(direction == 2 || direction == 1) {
    				moveUp();
    				return;
    			}
    		
    		} else if(isBlockedRightUp()) {
    			
    			if(direction == 2 || direction == 1) {
    				moveLeft();
    				return;
    			} else if(direction == 3 || direction == 0) {
    				moveLeft();
    				return;
    			}
    			
    		} else if(isBlockedRightDown()) {
    		
    			if(direction == 3 || direction == 2) {
    				moveUp();
    				return;
    			} else if(direction == 0 || direction == 1) {
    				moveUp();
    				return;
    			}
    			
    			return;
    		} else if(!isUpBlocked() || direction == 1) {
    			moveUp();
    			return;
    		} else if(direction == 0 && !isLeftBlocked()) {
    			moveLeft();
    			return;
    		} else if(direction == 2 && !isRightBlocked()) {
    			moveRight();
    			return;
    		} else if(direction == 1 && !isUpBlocked()) {
    			moveUp();
    			return;
    		} else if(direction == 3 && !isDownBlocked()) {
    			moveDown();
    			return;
    		} else {
    			System.out.println(color + " on else");
    		}
    	}
    	
    } 
    
    private BufferedImage getWhiteGhost(boolean cycler) {
    	if(cycler)
    		return Assets.whiteModeGhosts[0];
    	else
    		return Assets.blueModeGhosts[1];
    }
    
    private void flipBouncingMovement() {
    	
    	if(!isInNest()) {
    		doneBouncing = true;
    		return;
    	} else
    	if(isLeftGhost()) {
    		if(bouncing > 30) {
    			doneBouncing = true;
    			return;
    		} else {
    	if(isOnXRemainder()) {
    		remainderXMoving();
    	} else if(isOnYRemainder()) {
    		remainderYMoving();
    	} else {
    		if(isBlockedLeftUp()) {
    			moveDown();
    			bouncing++;
    			return;
    		} else if(isBlockedLeftDown()) {
    			moveUp();
    			bouncing++;
    			return;
    		}
    		}
    		}
    	} else if(isMiddleGhost()) {
    		if(bouncing > 10) {
    			doneBouncing = true;
    			return;
    		} else {
    	if(isOnXRemainder()) {
    		remainderXMoving();
    	} else if(isOnYRemainder()) {
    		remainderYMoving();
    	} else {
    		if(getY()/Tile.TILE_SIZE - 1 == 11) {
    			moveDown();
    		} else 
    		if(!isUpBlocked()) {
    			moveUp();
    			bouncing++;
    			return;
    		} else if(!isDownBlocked()) {
    			moveDown();
    			bouncing++;
    			return;
    		}
    		}
    		}
    		
    	} else if(isRightGhost()) {
    		if(bouncing > 40) {
    			doneBouncing = true;
    			return;
    		} else {
    	if(isOnXRemainder()) {
    		remainderXMoving();
    	} else if(isOnYRemainder()) {
    		remainderYMoving();
    	} else {
    		if(isBlockedRightUp()) {
    			moveDown();
    			bouncing++;
    			return;
    		} else if(isBlockedRightDown()) {
    			moveUp();
    			bouncing++;
    			return;
    		}
    		}
    		}
    	}
    	
    }
    
    
    
    private boolean isLeftGhost() {
    	return getX()/Tile.TILE_SIZE == 9;
    }
    
    private boolean isRightGhost() {
    	return getX()/Tile.TILE_SIZE == 11;
    }
    
    private boolean isMiddleGhost() {
    	return getX()/Tile.TILE_SIZE == 10;
    }
    

}
