package astel.pacman.main;

import java.awt.Graphics;
import java.io.BufferedReader;

import astel.pacman.entities.Entity;
import astel.pacman.entities.Player;
import astel.pacman.entities.Wall;
import astel.pacman.util.Tile;

public class World {
    
    private String dir;
    private Graphics g;
    private int width, height;
    private int spawnX, spawnY;
    private int[][] tiles;
    Player player;
    
    private int [][] worldArray = { 
    		{03, 01, 01, 01, 01, 01, 01, 01, 01, 01, 23, 01, 01, 01, 01, 01, 01, 01, 01, 01, 04, 00, 00, 00, 00, 00, 00},//1
    		{02, 27, 27, 27, 27, 27, 27, 27, 27, 27, 02, 27, 27, 27, 27, 27, 27, 27, 27, 27, 02, 00, 00, 00, 00, 00, 00},//2
    		{02, 27, 03, 01, 04, 27, 03, 01, 04, 27, 02, 27, 03, 01, 04, 27, 03, 01, 04, 27, 02, 00, 00, 00, 00, 00, 00},//3
    		{02, 88, 02, 00, 02, 27, 02, 00, 02, 27, 02, 27, 02, 00, 02, 27, 02, 00, 02, 88, 02, 00, 00, 00, 00, 00, 00},//4
    		{02, 27, 06, 01, 05, 27, 06, 01, 05, 27, 21, 27, 06, 01, 05, 27, 06, 01, 05, 27, 02, 00, 00, 00, 00, 00, 00},//5
    		{02, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 02, 00, 00, 00, 00, 00, 00},//6
    		{02, 27, 03, 01, 04, 27, 19, 27, 03, 01, 01, 01, 04, 27, 19, 27, 03, 01, 04, 27, 02, 00, 00, 00, 00, 00, 00},//7
    		{02, 27, 06, 01, 05, 27, 02, 27, 06, 01, 23, 01, 05, 27, 02, 27, 06, 01, 05, 27, 02, 00, 00, 00, 00, 00, 00},//8
    		{02, 27, 27, 27, 27, 27, 02, 27, 27, 27, 02, 27, 27, 27, 02, 27, 27, 27, 27, 27, 02, 00, 00, 00, 00, 00, 00},//9
    		{06, 01, 01, 01, 04, 27, 26, 01, 20, 80, 21, 80, 22, 01, 24, 27, 03, 01, 01, 01, 05, 00, 00, 00, 00, 00, 00},//10
    		{00, 00, 00, 00, 02, 27, 02, 80, 80, 80, 80, 80, 80, 80, 02, 27, 02, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00},//11
    		{00, 00, 00, 00, 02, 27, 02, 80, 03, 20, 90, 22, 04, 80, 02, 27, 02, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00},//12
    		{22, 01, 01, 01, 05, 27, 21, 80, 02, 80, 80, 80, 02, 80, 21, 27, 06, 01, 01, 01, 20, 80, 80, 80, 80, 80, 80},//13
    		{80, 80, 80, 80, 80, 27, 80, 80, 02, 80, 80, 80, 02, 80, 80, 27, 80, 80, 80, 80, 80, 80, 80, 00, 00, 00, 00},//14
    		{22, 01, 01, 01, 04, 27, 19, 80, 06, 01, 01, 01, 05, 80, 19, 27, 03, 01, 01, 01, 20, 00, 00, 00, 00, 00, 00},//15
    		{00, 00, 00, 00, 02, 27, 02, 80, 80, 80, 80, 80, 80, 80, 02, 27, 02, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00},//16
    		{00, 00, 00, 00, 02, 27, 02, 80, 03, 01, 01, 01, 04, 80, 02, 27, 02, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00},//17
    		{03, 01, 01, 01, 05, 27, 21, 80, 06, 01, 23, 01, 05, 80, 21, 27, 06, 01, 01, 01, 04, 00, 00, 00, 00, 00, 00},//18
    		{02, 27, 27, 27, 27, 27, 27, 27, 27, 27, 02, 27, 27, 27, 27, 27, 27, 27, 27, 27, 02, 00, 00, 00, 00, 00, 00},//19
    		{02, 27, 22, 01, 04, 27, 22, 01, 20, 27, 21, 27, 22, 01, 20, 27, 03, 01, 20, 27, 02, 00, 00, 00, 00, 00, 00},//20
    		{02, 88, 27, 27, 02, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 02, 27, 27, 88, 02, 00, 00, 00, 00, 00, 00},//21
    		{26, 01, 04, 27, 02, 27, 19, 27, 03, 01, 01, 01, 04, 27, 19, 27, 02, 27, 03, 01, 24, 00, 00, 00, 00, 00, 00},//22
    		{26, 01, 05, 27, 21, 27, 02, 27, 06, 01, 23, 01, 05, 27, 02, 27, 21, 27, 06, 01, 24, 00, 00, 00, 00, 00, 00},//23
    		{02, 27, 27, 27, 27, 27, 02, 27, 27, 27, 02, 27, 27, 27, 02, 27, 27, 27, 27, 27, 02, 00, 00, 00, 00, 00, 00},//24
    		{02, 27, 22, 01, 01, 01, 25, 01, 20, 27, 21, 27, 22, 01, 25, 01, 01, 01, 20, 27, 02, 00, 00, 00, 00, 00, 00},//25
    		{02, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 02, 00, 00, 00, 00, 00, 00},//26
    		{06, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 01, 05, 00, 00, 00, 00, 00, 00} //27

    };
    
    private boolean [][] tileMap;
    
    private boolean [][] breadArray;
    Wall wall;
    BufferedReader br;
    public World(Player player, Wall wall) {
		this.player = player;
		this.wall = wall;
	   
	 
	  loadWalls();
	  
    }
    
    public World() {
	loadWalls();
    }
    
    public boolean canAccess(Entity e, boolean[][] tileMap) {
	if(tileMap[e.getX() / Tile.TILE_SIZE][e.getY() / Tile.TILE_SIZE] == false) {
	    System.out.println(tileMap[e.getX() / Tile.TILE_SIZE][e.getY() / Tile.TILE_SIZE]);
	    return false;
	} else {
	    System.out.println(tileMap[e.getX() / Tile.TILE_SIZE][e.getY() / Tile.TILE_SIZE]);
	    return true;
	}
    }
    
    
    public boolean canAccess(boolean[][] tileMap, int x, int y) {
	if(tileMap[x / Tile.TILE_SIZE][y / Tile.TILE_SIZE] == false) {
	 
	    return false;
	} else {
	
	    return true;
	}
    }
    
    public boolean [][] getBreadArray() {
    	return this.breadArray;
    }
    
    private void printMap(boolean [][] map) {
    	for(int row = 0; row < map.length; row++) {
    		for(int col = 0; col < map[0].length; col++) {
    			
    			if(map[row][col]) {
    				System.out.print("1 ");
    			} else {
    				System.out.print("0 ");
    			}
    		}
    		System.out.println();
    			
    		}
    	
    	System.out.println("world array: " + map.length + " " + map[0].length);

    }
    
    public void loadWalls(){

	
	tileMap = new boolean[worldArray.length][worldArray[0].length];
	breadArray = new boolean[worldArray.length][worldArray[0].length];

	for(int y = 0; y < worldArray.length; y++) {
		for(int x = 0; x < worldArray[0].length; x++) {
		
		if(worldArray[y][x] == 27 || worldArray[y][x] == 88) {
			breadArray[y][x] = true;
		} else {
			breadArray[y][x] = false;
		}
		
		if(worldArray[y][x] == 27 || worldArray[y][x] == 80
				||  worldArray[y][x] == 88 ||  worldArray[y][x] == 90) {
		    tileMap[y][x] = true;
			} else {
			    tileMap[y][x] = false;
			}
		}
		
	}
	
	
	/*for(int col = 0; col < tileMap.length; col++) {
	    for(int row = 0; row < tileMap[0].length; row++) {
		if(tileMap[row][col] == true) {
		    System.out.print("1 ");
		} else {
		    System.out.print("0 ");
		}
	    }
	    
	    System.out.println();
	}*/
	
	//TODO
	/*for(int i = 0; i < tokens.length; i++) {
	    System.out.println(tokens[i]);
	}*/
	  
	
	
    }
    
    public void setValueInTileMap(int x, int y, boolean b) {
    	this.tileMap[y][x] = b;
    }
     
    public boolean[][] getTileMap() {
	return this.tileMap;
    }
    
    public void loadWorld(Graphics g, Player p) {
	//String file = Utils.loadFileAsString(dir);
	//String [] tokens = file.split("\\s+");
	//width = Utils.parseInt(tokens[0]);
	//height = Utils.parseInt(tokens[1]);
	//spawnX = Utils.parseInt(tokens[2]);
	//spawnY = Utils.parseInt(tokens[3]);


	      for(int y = 0; y < worldArray.length; y++) {
	    	  for(int x = 0; x <  worldArray[0].length; x++) {
	    		  wall.drawTile(g, worldArray[y][x], x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, p);
	    	  }
	      }
	      
	      
		 // Wall wall = new Wall(i * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
		
		//  wall.drawTile(g, Integer.parseInt(tokens[i]), i * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
		//  wall.drawTile(g, Integer.parseInt(tokens[i]));
	
	 /*   String [] tokens = br.readLine().split(" ");
	    
	    for(int i = 0; i < tokens.length; i++) {
		       wall.drawTile(g, Integer.parseInt(tokens[i]),
			i * Tile.TILE_SIZE , 0);
	    	System.out.println(Integer.parseInt(tokens[i]));
	    	}*/
	  
	    
	   

    
	
	/*try {
	 
	tiles = new int[width][height];
	for(int y = 0; y < height; y++) {
	    for(int x = 0; x < width; x++) {
		//tiles[x][y] = Utils.parseInt(tokens[x/*(x  + y * width)]);
		tiles[x][y] = 
		 
		//if(x == width - 1)
		//	wall.drawTile(g, tiles[x][y], x , y * Tile.TILE_SIZE);
		
		wall.drawTile(g, tiles[x][y], x * Tile.TILE_SIZE, y);
		System.out.println(tiles[x][y]);
		
	    }
	    
	   
	    
	    
		} 
	}catch(Exception e) {
	   System.err.println("Error in loading world!"); 
	   e.printStackTrace();
	}*/
		       
	}
    
    }


