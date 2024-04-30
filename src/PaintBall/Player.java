package PaintBall;

public interface Player {


    String getType();

    boolean ownerIdIs(Team currentTeamId);

    void move(int x, int y);

    int getX();

    int getY();

    Team getOwnerId();

}
