package astel.pacman.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Utils {
	
    private Utils() {
	throw new RuntimeException("Cannot instantiate!");
    }
    
    public static BufferedImage loadBufferedImage(String s) throws IOException {

	    BufferedImage img = ImageIO.read(new File(s));
	    return img;
	
    }
    
    public static BufferedImage crop(BufferedImage img, int x, int y, int width, int height) {
    	return img.getSubimage(x, y, width, height);
    	
        }
    
    public static synchronized void threadSafeTask(Handler handler) {
    	handler.task();
    }
    
    public static void doTask(Handler handler) {
    	handler.task();
    }
    
    public static void printTileMap(boolean [][] map) {
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
    }
    
    public static boolean[][] copyTileArray(boolean[][] map) {
    	boolean[][] out = new boolean[map.length][map[0].length];
    	for(int row = 0; row < map.length; row++) {
    		for(int col = 0; col < map[0].length; col++) {
    			out[row][col] = map[row][col];
    		}
    	}
    	
    	return out;
    }
    
    public static String loadFileAsString(String dir) {
	StringBuilder builder = new StringBuilder();
	try {
	    BufferedReader br = new BufferedReader(new FileReader(dir));
	    String line;
	    while((line = br.readLine()) != null) {
		builder.append(line + "\n");
	    }
	    br.close();
	} catch(IOException e) {
	    e.printStackTrace();
	}
	
	return builder.toString();
    }
    
    public static int parseInt(String number) {
	try {
	    
	    return Integer.parseInt(number);
	    
	} catch(NumberFormatException e) {
	    e.printStackTrace();
	    return 0;
	}
    }

}
