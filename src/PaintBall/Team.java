package PaintBall;

public interface Team {
    public boolean exists();

    public Iterator<Tile> getBunkerIterator();

    public int getBunkerLen();

    public void addPlayer(String type, int x, int y, int ownerId,int pId);


    PlayerIterator getPlayerIterator();

    void removePlayer(Player playerAttacking);
    void loseBunker(Bunker bunker);
    void addBunker(Bunker bunker);
}
