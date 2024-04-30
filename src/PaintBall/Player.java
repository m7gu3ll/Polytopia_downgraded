package PaintBall;

public interface Player {


    String getType();

    boolean ownerIs(Team currentTeamId);

    void move(int x, int y);

    int getX();

    int getY();

    Team getOwner();

}
