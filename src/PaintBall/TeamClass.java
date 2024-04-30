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
        return name != null;
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
    public int getPlayersLen() {
        return players.len();
    }

    @Override
    public int getBunkersLen() {
        return bunkers.len();
    }

    @Override
    public void addPlayer(Player e) {
        players.add(e);
    }

    @Override
    public PlayerIterator getPlayerIterator() {
        return new PlayerIterator(players);
    }

    @Override
    public void removePlayer(Player playerAttacking) {
        players.remove(playerAttacking);
    }

    @Override
    public void removeBunker(Bunker bunker) {
        bunkers.remove(bunker);
    }
}
