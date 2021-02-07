package astel.pacman.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Tile {
    
    public final static int TILE_SIZE = 16;
    protected BufferedImage image;
    protected int x;
    protected int y;
    
    public abstract int getX();
    public abstract int getY();
    public abstract int getTileID();
    public abstract void setTileID(int id);
    public abstract void setX(int x);
    public abstract void setY(int y);
    public abstract void drawTile(Graphics g, int id);
    public abstract boolean isSolid(int x, int y);
    
}
