package PaintBall;

public interface Player {


    String getType();

    boolean ownerIdIs(int currentTeamId);

    boolean checkIsPlayer(int x, int y);

    void move(int i);

    int getX();

    int getY();
    int getPlayerId();
}
