package PaintBall;

public class TeamClass implements Team {
    final String GREEN = "green";
    final String RED = "red";
    final String BLUE = "blue";
    String name;
    BunkerArrayList bunkers;
    PlayerArrayList players;

    public TeamClass(String name, Bunker bunker) {
        this.name = name;
        bunkers = new BunkerArrayList();
        bunkers.add(bunker);
        players = new PlayerArrayList();
    }

    public String toString() {
        return name;
    }

    @Override
    public boolean exists() {
        return name == null;
    }

    @Override
    public Iterator<Tile> getBunkerIterator() {
        return new BunkerIterator(bunkers);
    }

    @Override
    public void addBunker(Bunker bunker) {
        bunkers.add(bunker);
    }

    @Override
    public int getBunkerLen() {
        return bunkers.len();
    }

    @Override
    public void addPlayer(String playerType, int x, int y, int ownerId,int pId) {

        switch (playerType) {
            case RED:
                players.add(new RedPlayer(x, y, ownerId,pId));
                break;
            case BLUE:
                players.add(new BluePlayer(x, y, ownerId,pId));
                break;
            case GREEN:
                players.add(new GreenPlayer(x, y, ownerId,pId));
                break;
        }

    }

    @Override
    public PlayerIterator getPlayerIterator() {
        return new PlayerIterator(players);
    }

    @Override
    public void removePlayer(Player playerAttacking) {

    }

    @Override
    public void loseBunker(Bunker bunker) {
        Iterator<Tile> BunkerAR =getBunkerIterator();
        Bunker tempbunker;
        int id=0;
        while (BunkerAR.hasNext()){
            tempbunker= (Bunker) BunkerAR.next();
            int x=tempbunker.getX();
            int y=tempbunker.getY();
            int atkx=bunker.getX();
            int atky=bunker.getY();
            if (x==atkx&&y==atky){
                bunkers.remove(id);
            }
            id++;
        }
    }
}
