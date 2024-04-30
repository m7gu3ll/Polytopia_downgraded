package PaintBall;

public class BluePlayer implements Player {
    int x;
    int y;
    Team id;
    String type = "blue";

    public BluePlayer(int x, int y, Team id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    @Override
    public String getType() {
        return type;
    }

    public boolean ownerIs(Team currentTeamId) {
        return id == currentTeamId;
    }


    @Override
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Team getOwner() {

        return id;
    }


}