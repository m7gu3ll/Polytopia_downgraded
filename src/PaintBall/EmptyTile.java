package PaintBall;

public class EmptyTile implements Tile {

    public static final char EMPTY_TILE = '.';
    public static final char PLAYER = 'P';
    Player occupier;

    public EmptyTile() {
        occupier = null;
    }

    @Override
    public boolean isOccupied() {
        return occupier != null;
    }

    @Override
    public void occupy(Player player) {
        occupier = player;
    }

    @Override
    public Player getOccupier() {
        return occupier;
    }

    @Override
    public void free() {
        occupier = null;
    }
}
