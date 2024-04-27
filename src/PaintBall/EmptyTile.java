package PaintBall;

public class EmptyTile implements Tile {

    public static final char EMPTY_TILE = '.';
    public static final char PLAYER = 'P';
    Player occupier;

    @Override
    public char toChar(int currentTeamId) {
        if (occupier.ownerIdIs(currentTeamId)) {
            return PLAYER;
        }
        return EMPTY_TILE;
    }

    @Override
    public boolean isOccupied() {
        return occupier != null;
    }
    @Override
    public void occupy(Player e) {
        occupier = e;
    }
    @Override
    public void free() {
        occupier = null;
    }
}
