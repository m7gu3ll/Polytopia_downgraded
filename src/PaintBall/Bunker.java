package PaintBall;

public class Bunker implements Tile {
    private Player occupier;
    private int x;
    private int y;
    private int treasure;
    private String name;
    private Team owner;

    public Bunker(int x, int y, int treasure, String name) {
        this.x = x;
        this.y = y;
        this.treasure = treasure;
        this.name = name;
        owner = null;
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

    public int getTreasure() {
        return treasure;
    }

    public void setTreasure(int treasure) {
        this.treasure = treasure;
    }

    public String toString() {
        return name;
    }

    public Team getOwner() {
        return owner;
    }

    public void setOwner(Team team) {
        owner = team;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void increaseTreasure() {
        treasure++;
    }
}
