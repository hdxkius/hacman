package astel.pacman.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import astel.pacman.assets.Assets;
import astel.pacman.entities.Ghost;
import astel.pacman.entities.Player;
import astel.pacman.entities.Wall;
import astel.pacman.util.Tile;
import astel.pacman.util.Utils;

public class GamePanel extends JLabel implements KeyListener, Runnable{
    
    public static final int FPS = 60;
    public static final long maxLoopTime = 1000 /  FPS;
    private World world;
    private Player player;
    private Thread thread;
    boolean running = false;
    private Ghost ghost, ghost1;
    private Wall wall;
    private Font font;
    JFrame frame;
    public static boolean onPause = false;
    private static Ghost[] ghosts = new Ghost[4];
    public GamePanel(JFrame frame) {
    	this.frame = frame;
    	try {
    		Assets.aster = Utils.loadBufferedImage("src/asterchib.png");
			Assets.pauseGround = Utils.loadBufferedImage("src/back.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    Assets.initGameFont();
	player = new Player(10 * Tile.TILE_SIZE, 20 * Tile.TILE_SIZE, 1, ghosts, frame);
	this.wall = new Wall(player);
	world = new World(player, wall);
	ghosts[0] = new Ghost(10 * Tile.TILE_SIZE, 10 * Tile.TILE_SIZE, player, 0, Ghost.COLOR.RED);
	ghosts[1] = new Ghost(11 * Tile.TILE_SIZE, 13 * Tile.TILE_SIZE, player, 1, Ghost.COLOR.ORANGE);
	ghosts[2] = new Ghost(9 * Tile.TILE_SIZE, 13 * Tile.TILE_SIZE, player, 1, Ghost.COLOR.TEAL);
	ghosts[3] = new Ghost(10 * Tile.TILE_SIZE, 12 * Tile.TILE_SIZE, player, 3, Ghost.COLOR.ROSE);
    
    	}
    
    public Player getPlayer() {
	return player;
    }
    
    public static Ghost [] getGhosts() {
    	return ghosts;
    }
    

    
    public void paint(Graphics g) {
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, 400, 600);
	

	
	world.loadWorld(g, player);

	ghosts[0].drawGhost(g);
	ghosts[1].drawGhost(g);
	ghosts[2].drawGhost(g);
	ghosts[3].drawGhost(g);
	
	player.drawPlayer(g);
	if(onPause) {
		g.drawImage(Assets.pauseGround, 0,0,
				353, 600, null);
		g.drawString("on Pause", 7 * Tile.TILE_SIZE ,
				21 * Tile.TILE_SIZE);
		g.drawImage(Assets.aster, 9 * Tile.TILE_SIZE ,
				16 * Tile.TILE_SIZE, 64, 64, null );
	}
	repaint();
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void keyPressed(KeyEvent e) {
	player.keyPlayer(e);

    }
    
  

    @Override
    public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void run() {
	long timestamp;
	long oldTimestamp;
	
	int fps = 40;
	double timePerTick = 1000000000 / fps;
	double delta = 0;
	long now;
	long lastTime = System.nanoTime();
	long timer = 0;
	int ticks = 0;
	
	while(running) {
	    now = System.nanoTime();
	    delta += (now - lastTime) / timePerTick;
	    timer += now -lastTime;
	    lastTime = now;
	    if(delta >= 1) {

	    
	 if(!frame.hasFocus()) {
		 onPause = true;
	 } else if(frame.hasFocus() && !Player.gamePaused) {
		 onPause =false;
	 }
	 
	 
	   // if(!Ghost.stopMoving ) {
	     	if(!GamePanel.onPause) {
	    	
	    player.movePlayer();
	    player.eraseMunchedTiles(player.getErasedMunchedTiles());
		ghosts[0].moveGhostFriendly();
		ghosts[1].moveGhostFriendly();
		ghosts[2].moveGhostFriendly();
		ghosts[3].moveGhostFriendly();
	    //}
	
	    	}

		
	
		
	    ticks++;
	    delta--;
	}
	    if(timer >= 1000000000 * 2) {
		ticks = 0;
		timer = 0;
	    }
	    
	}
	stop();
	
    }
    
    
    public synchronized void start() {
	if(running) {
	    return;
	}
	running = true;
	thread = new Thread(this);
	thread.start();
	
	
    }
    
    public synchronized void stop() {
	try {
	    if(!running) {
		return;
	    }
	    running = false;
	    thread.join();
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
