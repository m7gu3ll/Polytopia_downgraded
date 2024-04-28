package PaintBall;

public class GameClass implements Game {
    public static final int INVALID_ID = -1;
    public static final char EMPTY_TILE = '.';
    final static int GREENPRICE = 2;
    final static int REDPRICE = 2;
    final static int BLUEPRICE = 4;
    final static String NORTH = "north";
    final static String SOUTH = "south";
    final static String EAST = "east";
    final static String WEST = "west";
    final String GREEN = "green";
    final String RED = "red";
    final String BLUE = "blue";
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
        map = new Tile[ht][wd];
        for (int i = 0; i < ht; i++) {
            for (int j = 0; j < wd; j++) {
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
        Tile tile = map[x][y];
        if (!(tile instanceof Bunker bunker))
            if (players.get(tile.getOccupier()).ownerIdIs(currentTeamId)) return 'P';
            else return '.';

        if (bunker.getOwnerId() == -1) return '.';
        if (bunker.getOwnerId() == currentTeamId && bunker.isOccupied()) return 'O';
        return 'B';
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
        int currentId = 0;
        currentId = players.len();
        switch (playerType) {

            case RED:

                tempPlayer = new RedPlayer(x, y, currentTeamId, currentId);
                players.add(tempPlayer);

                bunker.occupy(tempPlayer);
                break;
            case BLUE:
                tempPlayer = new BluePlayer(x, y, currentTeamId, currentId);
                players.add(tempPlayer);
                bunker.occupy(tempPlayer);
                break;
            case GREEN:
                tempPlayer = new GreenPlayer(x, y, currentTeamId, currentId);
                players.add(tempPlayer);
                bunker.occupy(tempPlayer);
                break;
        }
        tempTeam.addPlayer(playerType, x, y, currentTeamId, currentId);


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
    public int move(int x, int y, String directions, int numberOfMoves) {
        int output = 0;
        Player player = findPlayer(x, y);
        output = BeforeMoveError(x, y, directions, numberOfMoves, player);
        if (output == 0) {
            int PlayersX = x;
            int PlayersY = y;
            int dir_i = 0;
            switch (directions) {
                case NORTH -> {
                    dir_i = 1;
                }
                case WEST -> {
                    dir_i = 2;
                }
                case EAST -> {
                    dir_i = 3;
                }
                case SOUTH -> {
                    dir_i = 4;
                }
            }
            output = DuringMoveError(PlayersX, PlayersY, dir_i, player);
            if (output == 0) {
                player.move(dir_i);
            }
        }
        return output;

    }

    @Override
    public PlayerIterator getCurrentTeamsPlayers() {
        return teams.get(currentTeamId).getPlayerIterator();
    }

    @Override
    public void attack() {
        Player player;
        for (int i = 0; i < players.len(); i++) {
            player = players.get(i);
            if (player.ownerIdIs(currentTeamId)) {
                switch (player.getType()) {
                    case "blue" -> attackBlue(i, currentTeamId);
                    case "green" -> attackGreen(i, currentTeamId);
                    case "red" -> attackRed(i, currentTeamId);
                }
            }
        }
    }

    //TODO types
    private void attackBlue(int attackerId, int currentTeamId) {
        int i;
        int sign;
        int n = 1;
        int iterationsSkipped = 0;
        Player attacker = players.get(attackerId);
        boolean attackerDied = false;

        while (iterationsSkipped < 2 && !attackerDied) {
            sign = (n % 2 == 0) ? 1 : -1;
            i = sign * n + attacker.getX();
            if (isInBounds(i, attacker.getY())) {
                iterationsSkipped = 0;
                Tile target = map[i][attacker.getY()];
                Player defender = target.getOccupier();
                // kill defender or die
                if (target.isOccupied() && !defender.ownerIdIs(currentTeamId)) {
                    //kill
                    target.free();
                    players.remove(players.find(defender));
                    //die
                    players.remove(attackerId);
                    attackerDied = true;
                }
            } else {
                iterationsSkipped++;
            }
            n++;
        }
    }


    private void attackGreen(int playerId, int currentTeamId) {
    }

    private void attackRed(int playerId, int currentTeamId) {
    }

    //TODO types
    private boolean attackInMap(int x, int y, String type, Player defender) {
        Tile target = map[x][y];
        defender = target.getOccupier();
        if (target.isOccupied() && !defender.ownerIdIs(currentTeamId)) {
            target.free();
            return true;
        }
        return false;
    }

    private boolean isInBounds(int x, int y) {
        return (x < 0 || x > wd) && (y < 0 || y > ht);
    }

    @Override
    public int BeforeMoveError(int x, int y, String directions, int numberOfMoves, Player player) {
        int output = 0;
        if (isInBounds(x, y)) {
            output = 1;
        } else if (!directions.equals(NORTH) && !directions.equals(WEST) && !directions.equals(EAST) && !directions.equals(SOUTH)) {
            output = 2;
        } else if (player == null) {
            output = 3;
        } else if (!player.getType().equals(RED) && numberOfMoves >= 1) {
            output = 4;
        }
        return output;
    }

    @Override
    public int DuringMoveError(int x, int y, int dir_i, Player player) {
        //* Todo ver se a equipa ganho depois de um ataque
        //  Todo ataque com move
        //  Todo erro depois
        int PX = x;
        int PY = y;
        int output = 0;
        switch (dir_i) {
            case 1 -> PX--;
            case 2 -> PY--;
            case 3 -> PY++;
            case 4 -> PX++;
        }
        Player Ranplayer = null;
        Ranplayer = map[PX][PY].getOccupier();
        if (!map[PX][PY].isOccupied() && Ranplayer != null && Ranplayer.ownerIdIs(getTeamId(getCurrentTeam())))
            return 6;

        if (!map[PX][PY].isOccupied() && map[PX][PY] instanceof Bunker)
            return 9;

        if (!map[PX][PY].isOccupied()) {
            switch (player.getType()) {
                case RED ->
                case BLUE ->
                case GREEN ->
            }
            return 8;
        }

        if (map[PX][PY] instanceof Bunker) {
            map[x][y].free();
            map[PX][PY].occupy(player);
            Bunker bunker = (Bunker) map[PX][PY];
            if (bunker.isOccupied() && !player.ownerIdIs(bunker.getOwnerId())) {
                Team tempTeam = teams.get(bunker.getOwnerId());
                tempTeam.loseBunker(bunker);
            }
            Team atkTeam = teams.get(getTeamId(getCurrentTeam()));
            atkTeam.addBunker(bunker);
            bunker.occupy(player);
            return 7;
        }
        return 0;

    }
}
