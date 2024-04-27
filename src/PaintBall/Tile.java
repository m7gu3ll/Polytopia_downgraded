package PaintBall;

public interface Tile {
    char toChar(int currentTeamId);
    boolean isOccupied();

    void occupy(Player e);

    void free();
}
