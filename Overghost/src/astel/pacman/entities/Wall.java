package astel.pacman.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import astel.pacman.assets.Assets;
import astel.pacman.main.GamePanel;
import astel.pacman.util.*;


public class Wall extends Tile{
	
	Thread thread;
    boolean running = false;
    boolean wasCalled = false;
    
    private final int DELAY = 10;
    
    private boolean wasLeftUp = false;
    private boolean wasRightUp = false;
    private boolean wasLeftDown = false;
    private boolean wasRightDown = false;
    
    private boolean done = true;
    //private Player player = new Player();
    private int x;
    private int y;
  //  Color c = new Color(245, 152, 66);
   Color c = Color.white;
    // private GamePanel panel = new GamePanel();
   private Player player;
    
    public Wall(int x, int y, Player player) {
	this.x = x;
	this.y = y;
	this.player = player;
	try {
	Assets.initWalls();
	} catch(Exception e) {
	    System.err.println("Something went wrong!");
	}
	
    }

    public void resetAllBreads() {
    	wasLeftUp = false;
    	wasRightUp = false;
    	wasLeftDown = false;
    	wasRightDown = false;
    	System.out.println("resetAllBreads: breads have been reset");
    }

    public boolean wasLeftUp() {
    	return wasLeftUp;
    }
    
    public boolean wasLeftDown() {
    	return wasLeftDown;
    }
    
    public boolean wasRightUp() {
    	return wasRightUp;
    }
    
    public boolean wasRightDown() {
    	return wasRightDown;
    }
    
    public Wall(Player player) {
    	this.player = player;
	try {
	Assets.initWalls();
	} catch(Exception e) {
	    System.err.println("Something went wrong!");
	}
	
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
    public int getTileID() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
	public
    void setTileID(int id) {
	// TODO Auto-generated method stub
	
    }

    @Override
	public
    void setX(int x) {
	// TODO Auto-generated method stub
	
    }

    @Override
	public
    void setY(int y) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public boolean isSolid(int x, int y) {
	// TODO Auto-generated method stub
	return false;
    }
    
    public boolean thereIsWall(int x, int y, Entity e) {
	if(this.x == e.getX() && this.y == e.getY()) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
	public void drawTile(Graphics g, int id) {
    	

	switch(id) {
	case 1:
	    g.drawImage(Assets.WALL_01, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 2:
	    g.drawImage(Assets.WALL_02, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 3:
	    g.drawImage(Assets.WALL_03, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 4:
	    g.drawImage(Assets.WALL_04, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 5:
	    g.drawImage(Assets.WALL_05, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 6:
	    g.drawImage(Assets.WALL_06, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 7:
	    g.drawImage(Assets.WALL_07, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 8:
	    g.drawImage(Assets.WALL_08, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 9:
	    g.drawImage(Assets.WALL_09, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 10:
	    g.drawImage(Assets.WALL_10, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 11:
	    g.drawImage(Assets.WALL_11, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 12:
	    g.drawImage(Assets.WALL_12, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 13:
	    g.drawImage(Assets.WALL_13, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 14:
	    g.drawImage(Assets.WALL_14, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 15:
	    g.drawImage(Assets.WALL_15, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 16:
	    g.drawImage(Assets.WALL_16, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 17:
	    g.drawImage(Assets.WALL_17, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 18:
	    g.drawImage(Assets.WALL_18, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 19:
	    g.drawImage(Assets.WALL_19, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 20:
	    g.drawImage(Assets.WALL_20, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 21:
	    g.drawImage(Assets.WALL_21, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 22:
	    g.drawImage(Assets.WALL_22, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 23:
	    g.drawImage(Assets.WALL_23, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 24:
	    g.drawImage(Assets.WALL_24, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 25:
	    g.drawImage(Assets.WALL_25, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 26:
	    g.drawImage(Assets.WALL_26, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 27:
	    // g.drawImage(Assets.WALL_27, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
		g.drawImage(Assets.WALL_27, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    
	    break;
	case 28:
	    g.drawImage(Assets.WALL_28, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 29:
	    g.drawImage(Assets.WALL_29, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 30:
	    g.drawImage(Assets.WALL_30, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 31:
	    g.drawImage(Assets.WALL_31, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	default:
		
		System.out.println("No such wall!");
		break;
	}
    }
    
    
    public void drawTile(Graphics g, int id, int x, int y, Player p) {
    	    	
    	Graphics2D g2d = (Graphics2D) g;
    

	switch(id) {
	case 1:
	    g.drawImage(Assets.WALL_01, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 2:
	    g.drawImage(Assets.WALL_02, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 3:
	    g.drawImage(Assets.WALL_03, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 4:
	    g.drawImage(Assets.WALL_04, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 5:
	    g.drawImage(Assets.WALL_05, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 6:
	    g.drawImage(Assets.WALL_06, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 7:
	    g.drawImage(Assets.WALL_07, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 8:
	    g.drawImage(Assets.WALL_08, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 9:
	    g.drawImage(Assets.WALL_09, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 10:
	    g.drawImage(Assets.WALL_10, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 11:
	    g.drawImage(Assets.WALL_11, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 12:
	    g.drawImage(Assets.WALL_12, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 13:
	    g.drawImage(Assets.WALL_13, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 14:
	    g.drawImage(Assets.WALL_14, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 15:
	    g.drawImage(Assets.WALL_15, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 16:
	    g.drawImage(Assets.WALL_16, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 17:
	    g.drawImage(Assets.WALL_17, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 18:
	    g.drawImage(Assets.WALL_18, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 19:
	    g.drawImage(Assets.WALL_19, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 20:
	    g.drawImage(Assets.WALL_20, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 21:
	    g.drawImage(Assets.WALL_21, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 22:
	    g.drawImage(Assets.WALL_22, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 23:
	    g.drawImage(Assets.WALL_23, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 24:
	    g.drawImage(Assets.WALL_24, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 25:
	    g.drawImage(Assets.WALL_25, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 26:
	    g.drawImage(Assets.WALL_26, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 27:
	   // g.drawImage(Assets.WALL_27, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);    
	    	if(!p.wasOnTile(p.getErasedMunchedTiles(), x, y)) {
		g.drawImage(Assets.WALL_27, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
		g.setColor(c);
		g.fillRect(x + 8, y + 8, 2, 2);
		
	    	} else {
	 
	    	 g.drawImage(Assets.WALL_27, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);    
	    	}
	  
	    break;
	case 28:
	    g.drawImage(Assets.WALL_28, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 29:
	    g.drawImage(Assets.WALL_29, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 30:
	    g.drawImage(Assets.WALL_30, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 31:
	    g.drawImage(Assets.WALL_31, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
	    break;
	case 88:
		
		if(thread != null) {
			System.out.println(thread.getState());
		}
		
		
		if(!p.wasOnTile(p.getErasedMunchedTiles(), x, y)) {
			if(Player.wasReset) {
				resetAllBreads();
				Player.wasReset = false;
			}
		g.setColor(c);
		g.fillOval(x + 4, y + 4, 8, 8);
	
		} 
		else {
					
			if(wasPlayerOnLeftUpBread(p.getErasedMunchedTiles()) && !wasLeftUp) {
				Player.finishedBread = true;
				count(GamePanel.getGhosts());
				wasLeftUp = true;
				
				break;
			} else if(wasPlayerOnLeftDownBread(p.getErasedMunchedTiles())
					&& !wasLeftDown) {
				Player.finishedBread = true;
				count(GamePanel.getGhosts());
				wasLeftDown = true;
			
				break;
			} else if(wasPlayerOnRightUpBread(p.getErasedMunchedTiles())
					&& !wasRightUp) {

				Player.finishedBread = true;
				count(GamePanel.getGhosts());
				wasRightUp = true;
			} else if(wasPlayerOnRightDownBread(p.getErasedMunchedTiles())
					&& !wasRightDown) {

				Player.finishedBread = true;
				count(GamePanel.getGhosts());
				wasRightDown = true;
				break;
			}
		}
		break;
		
	case 90:
		 g.drawImage(Assets.GATE, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);
		break;
	
		
		/*if(p.wasOnTile(p.getErasedMunchedTiles(), x, y)) {
		g.setColor(c);
		g.fillOval(x + 4, y + 4, 8, 8);
		
		return;
		} else if(done || !p.wasOnTile(p.getErasedMunchedTiles(), x, y)){
			 g.drawImage(Assets.WALL_27, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);   
			
			 	if(running) {
			 		break;
			 	}
			 	
			 	running = true;
			  	done = false;
			  	p.setWasOnTile(p.getErasedMunchedTiles(), x, y, false);
			 	Thread thread = new Thread(p.getRunnable());
		    	thread.start();
		   
		    	System.out.println("done");
		    	
		    	
		   
	    	
		} else if(!done) {
			 g.drawImage(Assets.WALL_27, x, y, Tile.TILE_SIZE, Tile.TILE_SIZE, null);   
			 System.out.println("this path");
		}*/
	
	default:
		break;
	}
    }
    
    private int[] xCoords(){
    	int[] cords = {1, 19};
    	return cords;
    }
    
    
    //1 3 / 1 20 / 19 3 / 19 20
    private int[] yCoords() {
    	int[] cords = {3, 20};
    	return cords;
    }
    
    public boolean wasPlayerOnLeftUpBread(boolean[][] tileMap) {
    	return tileMap[yCoords()[0]][xCoords()[0]] == false;
    }
    
    public boolean wasPlayerOnLeftDownBread(boolean[][] tileMap) {
    	return tileMap[yCoords()[1]][xCoords()[0]] == false;
    }
    
    public boolean wasPlayerOnRightUpBread(boolean[][] tileMap) {
    	return tileMap[yCoords()[0]][xCoords()[1]] == false;
    }

    public boolean wasPlayerOnRightDownBread(boolean[][] tileMap) {
    	return tileMap[yCoords()[1]][xCoords()[1]] == false;
    }
    
   
      
    private boolean wasPlayerOnTile(boolean[][] tileMap, int []x, int []y) {
    		return (tileMap[x[0]][y[0] ] == true)
    				|| (tileMap[x[0] / Tile.TILE_SIZE][y[1] / Tile.TILE_SIZE] == true)
    				||(tileMap[x[1] / Tile.TILE_SIZE][y[0] / Tile.TILE_SIZE] == true) ||
    				(tileMap[x[1] / Tile.TILE_SIZE][y[1] / Tile.TILE_SIZE] == true);
    	
    }
    
    public void setRunning(boolean run) {
    	this.running = run;
    }
    
 
    private synchronized void count(Ghost [] ghosts) {
    	if(ghosts.length > 4 || ghosts.length < 0) {
    		return;
    	}
    	
    	if(Player.doneCounting) {
    		running = false;
    	}
    	
    	if(running && !Ghost.stopMoving) {
    		ghosts[0].setWasExtended(true);
    		ghosts[1].setWasExtended(true);
    		ghosts[2].setWasExtended(true);
    		ghosts[3].setWasExtended(true);
    		player.setTimeCounter(player.getTimeCounter() + 5);
    		return;
    	} 
    	running = true;
    	player.counter(DELAY);
    	
    	
    	
    }
    
}
