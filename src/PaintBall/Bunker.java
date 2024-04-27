package PaintBall;

public class Bunker implements Tile {


    public static final char BUNKER = 'B';
    public static final char BUNKER_OCCUPIED = 'O';
    public static final char EMPTY_TILE = '.';
    Player occupier;
    private int x;
    private int y;
    private int treasure;
    private String name;
    private int owner;
    boolean occupied=false;

    public Bunker(int x, int y, int treasure, String name) {
        this.x = x;
        this.y = y;
        this.treasure = treasure;
        this.name = name;
    }

    @Override
    public char toChar(int currentTeamId) {
        if (occupier.ownerIdIs(currentTeamId)) {
            return BUNKER_OCCUPIED;
        }
        if (owner == currentTeamId) {
            return BUNKER;
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

    public int getTreasure() {
        return treasure;
    }

    public void setTreasure(int treasure) {
        this.treasure = treasure;
    }

    public String toString() {
        return name;
    }

    public int getOwnerId() {
        return owner;
    }

    public void setOwnerId(int teamId) {
        owner = teamId;
    }

    public boolean exists() {
        return name == null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
