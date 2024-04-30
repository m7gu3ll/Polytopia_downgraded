package PaintBall;

public interface Team {
     boolean exists();

     Iterator<Tile> getBunkerIterator();

    int getBunkersLen();

   void addPlayer(Player e);


    PlayerIterator getPlayerIterator();

    void removePlayer(Player playerAttacking);
    void removeBunker(Bunker bunker);
    void addBunker(Bunker bunker);

    int getPlayersLen();
}
