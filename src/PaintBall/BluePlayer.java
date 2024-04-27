package PaintBall;

public class BluePlayer implements Player {
    int x;
    int y;
    int OwnerId;
    String type = "blue";

    public BluePlayer(int x, int y, int OwnerID) {
        this.x = x;
        this.y = y;
        this.OwnerId = OwnerID;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean ownerIdIs(int currentTeamId) {
        return OwnerId == currentTeamId;
    }

    @Override
    public boolean checkIsPlayer(int x, int y) {
        return this.x == x && this.y == y;
    }

    @Override
    public void moveX(int i) {
        x += i;
    }

    @Override
    public void moveY(int i) {
        y += i;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}