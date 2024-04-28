package PaintBall;

public class GreenPlayer implements Player {
    int x;
    int y;
    int OwnerId;
    String type = "green";
    int id;

    public GreenPlayer(int x, int y, int OwnerID, int id) {
        this.x = x;
        this.y = y;
        this.OwnerId = OwnerID;
        this.id =id;
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
    @Override
    public int getPlayerId() {
        return id;
    }
}
