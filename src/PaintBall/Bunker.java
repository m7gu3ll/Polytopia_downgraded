package PaintBall;

public class Bunker implements Tile {


    public static final char BUNKER = 'B';
    public static final char BUNKER_OCCUPIED = 'O';
    public static final char EMPTY_TILE = '.';
    private Player occupier;
    private int x;
    private int y;
    private int treasure;
    private String name;
    private int owner;

    public Bunker(int x, int y, int treasure, String name) {
        this.x = x;
        this.y = y;
        this.treasure = treasure;
        this.name = name;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
