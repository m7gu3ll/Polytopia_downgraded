package PaintBall;

public class RedPlayer implements Player {
    int x;
    int y;
    int OwnerId;
    String type = "red";
    int id;


    public RedPlayer(int x, int y, int OwnerID,int id) {
        this.x = x;
        this.y = y;
        this.OwnerId = OwnerID;
        this.id =id;
    }

    @Override
    public String getType() {
        return type;
    }

    public boolean ownerIdIs(int currentTeamId) {
        return OwnerId == currentTeamId;
    }

    @Override
    public boolean checkIsPlayer(int x, int y) {
        return this.x == x && this.y == y;
    }

    @Override
    public void move(int i){
        switch (i){
            case 1->x--;
            case 2->y--;
            case 3->y++;
            case 4->x++;
        }
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