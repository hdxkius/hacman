package astel.pacman.entities;

public abstract class Entity {

    protected int x, y, direction;
    public abstract int getX();
    public abstract int getY();
    public abstract int getDirection();
    public abstract void setDirection(int direction);
}
