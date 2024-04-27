package PaintBall;

public interface Player {


   String getType();
    boolean ownerIdIs(int currentTeamId);
    boolean checkIsPlayer(int x,int y);
    void moveX(int i);
    void moveY(int i);

    int getX();

 int getY();
}
