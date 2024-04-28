package PaintBall;

public interface Tile {
    boolean isOccupied();

    void occupy(Player player);
    Player getOccupier();

    void free();
}
