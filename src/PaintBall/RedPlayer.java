package PaintBall;

public class RedPlayer implements Player {
    int x;
    int y;
    String type = "red";
    Team id;


    public RedPlayer(int x, int y, Team ownerID) {
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