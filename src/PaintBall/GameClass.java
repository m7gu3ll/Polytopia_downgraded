package PaintBall;

public class GameClass implements Game {
    public static final int INVALID_ID = -1;
    public static final char EMPTY_TILE = '.';
    final String GREEN = "green";
    final String RED = "red";
    final String BLUE = "blue";
    final static int GREENPRICE = 2;
    final static int REDPRICE = 2;
    final static int BLUEPRICE = 4;
    final static String NORTH="north";
    final static String SOUTH="south";
    final static String EAST="east";
    final static String WEST="west";
    int wd;
    int ht;
    Tile[][] map;
    TeamArrayList teams;
    BunkerArrayList bunkers;
    PlayerArrayList players;
    int currentTeamId;

    public GameClass(int width, int height, int teamsLength, int bunkersLength) {
        wd = width;
        ht = height;
        map = new Tile[wd][ht];
        for (int i = 0; i < wd; i++) {
            for (int j = 0; j < ht; j++) {
                map[i][j] = new EmptyTile();
            }
        }
        players = new PlayerArrayList();
        teams = new TeamArrayList();
        bunkers = new BunkerArrayList();
    }

    private void updateTeam() {
        currentTeamId++;
        if (currentTeamId >= teams.len()) {
            currentTeamId = 0;
        }
    }

    @Override
    public int getNumberOfBunkers() {
        return bunkers.len();
    }

    @Override
    public int getNumberOfTeams() {
        return teams.len();
    }

    @Override
    public String getCurrentTeam() {
        return teams.get(currentTeamId).toString();
    }

    @Override
    public boolean verifyBunkerParameters(int x, int y, int treasure, String name) {
        return x > 0 && x < wd && y > 0 && y < ht && treasure > 0 && getBunkerId(name) != INVALID_ID;
    }

    @Override
    public boolean verifyTeamParameters(int bunkerId, String name) {
        return bunkerId != INVALID_ID && getTeamId(name) != INVALID_ID;
    }

    @Override
    public void addBunker(int x, int y, int treasure, String name) {
        Bunker bunker = new Bunker(x, y, treasure, name);
        bunkers.add(bunker);
        map[x][y] = bunker;
    }

    @Override
    public void addTeam(int bunkerId, String name) {
        Team team = new TeamClass(name, bunkers.get(bunkerId));
        bunkers.get(bunkerId).setOwnerId(find(team));
    }

    private int find(Team team) {
        int i = 0;
        int id = INVALID_ID;
        while (i < teams.len() && id == INVALID_ID) {
            if (teams.get(i) == team) {
                id = i;
            }
            i++;
        }
        return id;
    }

    @Override
    public int getX() {
        return wd;
    }

    @Override
    public int getY() {
        return ht;
    }

    @Override
    public int getBunkerId(String name) {
        int id = INVALID_ID;
        int low = 0;
        int high = bunkers.len() - 1;

        while (low <= high && id == INVALID_ID) {
            int pivot = low + ((high - low) >> 1);
            int result = bunkers.get(pivot).toString().compareTo(name);
            if (result == 0)
                id = pivot;
            else if (result < 0)
                low = pivot + 1;
            else high = pivot - 1;
        }
        return id;
    }

    @Override
    public int getTeamId(String name) {
        int id = INVALID_ID;
        for (int i = 0; i < teams.len() && id == INVALID_ID; i++) {
            if (teams.get(i).toString().equals(name)) {
                id = i;
            }
        }
        return id;
    }

    @Override
    public String getTeamName(int id) {
        return teams.get(id).toString();
    }

    @Override
    public char getTileAt(int x, int y) {
        return map[x][y].toChar(currentTeamId);
    }

    @Override
    public Iterator<Tile> getBunkerIterator() {
        return new BunkerIterator(bunkers);
    }

    @Override
    public Iterator<Team> getTeamIterator() {
        return new TeamIterator(teams);
    }

    @Override
    public void createPlayer(String playerType, String bunkerName) {
        int currentTeamId = getTeamId(getCurrentTeam());
        Team tempTeam = teams.get(currentTeamId);
        int x, y;
        int price = playerPrice(playerType);
        Bunker bunker = findCurrentTeamBunker(bunkerName);
        x = bunker.getX();
        y = bunker.getY();
        bunker.setTreasure(bunker.getTreasure() - price);
        Player tempPlayer;
        switch (playerType) {
            case RED:
                tempPlayer =new RedPlayer(x, y, currentTeamId);
                players.add(tempPlayer);
                bunker.occupy(tempPlayer);
                break;
            case BLUE:
                tempPlayer=new BluePlayer(x, y, currentTeamId);
                players.add(tempPlayer);
                bunker.occupy(tempPlayer);
                break;
            case GREEN:
                tempPlayer=new GreenPlayer(x, y, currentTeamId);
                players.add(tempPlayer);
                bunker.occupy(tempPlayer);
                break;
        }
        tempTeam.addPlayer(playerType, x, y, currentTeamId);


    }


    @Override
    public int playerPrice(String playerType) {
        int returnValue = 0;
        switch (playerType) {
            case RED -> returnValue = REDPRICE;
            case BLUE -> returnValue = BLUEPRICE;
            case GREEN -> returnValue = GREENPRICE;
        }
        return returnValue;
    }

    @Override
    public Bunker findCurrentTeamBunker(String bunkerName) {
        int currentTeamId = getTeamId(getCurrentTeam());
        Team tempTeam = teams.get(currentTeamId);
        Iterator<Tile> teamBunker = tempTeam.getBunkerIterator();
        Bunker bunker = null;
        boolean isBunkerFound = false;

        while (teamBunker.hasNext() && !isBunkerFound) {
            bunker = (Bunker) teamBunker.next();
            if (bunker.toString().equals(bunkerName)) {
                isBunkerFound = true;
            }
        }
        return bunker;
    }

    @Override
    public void movePlayer() {

    }

    public boolean checkPlayerType(String playerType) {
        boolean returnValue;
        switch (playerType) {
            case RED, BLUE, GREEN -> returnValue = true;
            default -> returnValue = false;
        }
        return returnValue;
    }

    @Override
    public void move(int x,int y,String[] directions) {

        Player player =findPlayer(x,y);
       for (int i =0; i<directions.length;i++){
            switch (directions[i]) {
                case NORTH -> player.moveX(1);
                case WEST -> player.moveX(-1);
                case EAST -> player.moveX(1);
                case SOUTH -> player.moveX(-1);
            }
        }
    }

    @Override
    public Player findPlayer(int x, int y) {
        Iterator<Player> PIt =getCurrentTeamsPlayers();
        Player player = null;
        boolean playerFound=false;
        while(PIt.hasNext()&&!playerFound){
            player = PIt.next();
            if (player.checkIsPlayer(x,y)){
                playerFound=true;
            }
        }
        return  player;
    }

    @Override
    public PlayerIterator getCurrentTeamsPlayers() {
        return teams.get(currentTeamId).getPlayerIterator();
    }

}
