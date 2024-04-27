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

    private void addBunker(Bunker bunker) {
        bunkers.add(bunker);
    }

    @Override
    public int getBunkerLen() {
        return bunkers.len();
    }

    @Override
    public void addPlayer(String playerType, int x, int y, int ownerId) {

        switch (playerType) {
            case RED:
                players.add(new RedPlayer(x, y, ownerId));
                break;
            case BLUE:
                players.add(new BluePlayer(x, y, ownerId));
                break;
            case GREEN:
                players.add(new GreenPlayer(x, y, ownerId));
                break;
        }

    }

    @Override
    public PlayerIterator getPlayerIterator() {
        return new PlayerIterator(players);
    }
}
