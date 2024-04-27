package PaintBall;

public interface Game {
    int getNumberOfBunkers();

    int getNumberOfTeams();

    String getCurrentTeam();
    boolean verifyBunkerParameters(int x, int y, int treasure, String name);

    boolean verifyTeamParameters(int bunkerId, String name);

    void addTeam(int bunkerId, String name);

    int getX();

    int getY();

    int getBunkerId(String name);
    int getTeamId(String name);
    Iterator<Tile> getBunkerIterator();
    Iterator<Team> getTeamIterator();
    public String getTeamName(int id);

    char getTileAt(int i, int j);

    void createPlayer(String playerType,String bunkerName);

    void addBunker(int x, int y, int treasure, String name);
    int playerPrice(String playerType);
    Bunker findCurrentTeamBunker(String bunkerName);
    void movePlayer();
    boolean checkPlayerType(String playerType);
    void move(int x,int y,String[] directions);
    Player findPlayer(int x, int y );

    PlayerIterator getCurrentTeamsPlayers();
}


