package PaintBall;

public class GreenPlayer implements Player {
    int x;
    int y;
    Team id;
    String type = "green";

    String potato;

    public GreenPlayer(int x, int y, Team ownerID) {
        this.x = x;
        this.y = y;
        this.id = ownerID;
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
