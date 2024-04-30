package PaintBall;

public class GameClass implements Game {
    public static final int INVALID_ID = -1;
    public static final int X = 0;
    public static final int Y = 1;
    public static final int N_DIRECTION = 1;
    public static final int W_DIRECTION = 2;
    public static final int E_DIRECTION = 3;
    public static final int S_DIRECTION = 4;
    final static int BLUEPRICE = 2;
    final static int GREENPRICE = 2;
    final static int REDPRICE = 4;
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

    public GameClass(int width, int height) {
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
        currentTeamId = 0;
    }

    private static int UBlue(int n, Player attacker) {
        int ux;
        int sign;
        sign = (n % 2 == 0) ? 1 : -1;
        ux = sign * n + attacker.getX();
        return ux;
    }

    private static void UGreen(int n, int[] result) {
        int signX = 0;
        int signY = 0;
        int mlt = (n >> 2) + 1;
        switch ((n - 1) % 4) {
            case 0 -> {
                signX = -1;
                signY = -1;
            }
            case 1 -> {
                signX = 1;
                signY = -1;
            }
            case 2 -> {
                signX = -1;
                signY = 1;
            }
            case 3 -> {
                signX = 1;
                signY = 1;
            }
        }
        result[X] = mlt * signX;
        result[Y] = mlt * signY;
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
        return x >= 0 && x < wd
                && y >= 0 && y < ht
                && treasure > 0
                && getBunkerId(name) == INVALID_ID
                && !(map[y][x] instanceof Bunker);
    }

    @Override
    public boolean verifyTeamParameters(int bunkerId, String name) {
        return bunkerId != INVALID_ID
                && bunkers.get(bunkerId).getOwner() == null
                && getTeamId(name) == INVALID_ID;
    }

    @Override
    public void addBunker(int x, int y, int treasure, String name) {
        Bunker bunker = new Bunker(x, y, treasure, name);
        bunkers.add(bunker);
        map[y][x] = bunker;
    }

    @Override
    public void addTeam(int bunkerId, String name) {
        teams.add(new TeamClass(name, bunkers.get(bunkerId)));
        bunkers.get(bunkerId).setOwner(teams.get(teams.len() - 1));
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
        return ht;
    }

    @Override
    public int getY() {
        return wd;
    }

    @Override
    public int getBunkerId(String name) {
        int id = INVALID_ID;
        int i = 0;
        while (i < bunkers.len() && id == INVALID_ID) {
            if (bunkers.get(i).toString().equals(name)) {
                id = i;
            }
            i++;
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
    public String getTeamName(Team id) {
        return teams.get(find(id)).toString();
    }

    @Override
    public char getTileAt(int x, int y) {
        Tile tile = map[y][x];
        if (!(tile instanceof Bunker bunker)) {
            if (tile.getOccupier() == null)
                return '.';
            if (!players.get(players.find(tile.getOccupier())).ownerIs(teams.get(currentTeamId)))
                return '.';
            else
                return 'P';
        }


        if (bunker.getOwner() == teams.get(currentTeamId))
            if (bunker.isOccupied())
                return 'O';
            else
                return 'B';
        return '.';
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
        Team tempTeam = teams.get(currentTeamId);
        int x, y;
        int price = playerPrice(playerType);
        Bunker bunker = findCurrentTeamBunker(bunkerName);
        x = bunker.getX();
        y = bunker.getY();
        bunker.setTreasure(bunker.getTreasure() - price);
        Player tempPlayer = null;
        switch (playerType) {
            case RED:
                tempPlayer = new RedPlayer(x, y, tempTeam);
                players.add(tempPlayer);
                bunker.occupy(tempPlayer);
                break;
            case BLUE:
                tempPlayer = new BluePlayer(x, y, tempTeam);
                players.add(tempPlayer);
                bunker.occupy(tempPlayer);
                break;
            case GREEN:
                tempPlayer = new GreenPlayer(x, y, tempTeam);
                players.add(tempPlayer);
                bunker.occupy(tempPlayer);
                break;
        }
        tempTeam.addPlayer(tempPlayer);
    }

    @Override
    public int playerPrice(String playerType) {
        switch (playerType) {
            case BLUE -> {
                return BLUEPRICE;
            }
            case GREEN -> {
                return GREENPRICE;
            }
            case RED -> {
                return REDPRICE;
            }
        }
        return 0;
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


    public boolean checkPlayerType(String playerType) {
        boolean returnValue;
        switch (playerType) {
            case RED, BLUE, GREEN -> returnValue = true;
            default -> returnValue = false;
        }
        return returnValue;
    }


    @Override
    public Player findPlayer(int x, int y) {
        if (map[y][x].getOccupier().ownerIs(teams.get(currentTeamId)))
            return map[y][x].getOccupier();
        return null;
    }

    @Override
    public PlayerIterator getCurrentTeamsPlayers() {
        return teams.get(currentTeamId).getPlayerIterator();
    }

    @Override
    public void attack() {
        Player player;
        int i = 0;
        int previousLen;
        while (i < players.len()) {
            previousLen = players.len();
            player = players.get(i);
            if (player.ownerIs(teams.get(currentTeamId))) {
                switch (player.getType()) {
                    case "blue" -> attackBlue(i);
                    case "green" -> attackGreen(i);
                    case "red" -> attackRed(i);
                }
            }
            if (previousLen == players.len()) {
                i++;
            }
        }
    }

    private void attackBlue(int attackerId) {
        int ux;
        int n = 1;
        int iterationsSkipped = 0;
        Player attacker = players.get(attackerId);
        boolean attackerDied = false;

        while (iterationsSkipped < 2 && !attackerDied) {
            ux = UBlue(n, attacker) + attacker.getX();
            if (isInBounds(ux, attacker.getY())) {
                iterationsSkipped = 0;
                Tile target = map[attacker.getY()][ux + attacker.getX()];
                Player defender = target.getOccupier();
                attackerDied = startDuel(target, defender, attacker);
            } else {
                iterationsSkipped++;
            }
            n++;
        }
    }

    // Un E R^2, Un = (n/4 + 1) * ((-1)^a, (-1)^b)
    private void attackGreen(int attackerId) {
        int[] u = new int[2];
        int n = 1;
        int iterationsSkipped = 0;
        Player attacker = players.get(attackerId);
        boolean attackerDied = false;
        while (iterationsSkipped < 4 && !attackerDied) {
            UGreen(n, u);
            u[X] += attacker.getX();
            u[Y] += attacker.getY();
            if (isInBounds(u[X], u[Y])) {
                iterationsSkipped = 0;
                Tile target = map[u[Y]][u[X]];
                Player defender = target.getOccupier();
                attackerDied = startDuel(target, defender, attacker);
            } else {
                iterationsSkipped++;
            }
            n++;
        }
    }

    private void attackRed(int attackerId) {
        int[] u = new int[2];
        int n = 1;
        int iterationsSkipped = 0;
        Player attacker = players.get(attackerId);
        u[X] = attacker.getX();
        u[Y] = attacker.getY();
        boolean attackerDied = false;
        while (iterationsSkipped < 2 && !attackerDied) {
            u[X]++;
            if (isInBounds(u[X], u[Y])) {
                iterationsSkipped = 0;
                Tile target = map[u[Y]][u[X]];
                Player defender = target.getOccupier();
                attackerDied = startDuel(target, defender, attacker);
            } else {
                iterationsSkipped++;
                u[X] = attacker.getX();
                u[Y]++;
            }
            n++;
        }
    }

    /**
     *
     * @param target tile do defender
     * @param defender player que esta a levar
     * @param attacker player que esta a dar
     * @return true if attacker lost, false if attacker won
     */
    private boolean startDuel(Tile target, Player defender, Player attacker) {
        if (target.isOccupied() && !defender.ownerIs(teams.get(currentTeamId))) {
            switch (attacker.getType()) {
                case "blue" -> {
                    if (defender.getType().equals("red")) {
                        map[attacker.getY()][attacker.getX()].free();
                        players.remove(attacker);
                        teams.get(currentTeamId).removePlayer(attacker);
                        return true;
                    } else {
                        target.free();
                        players.remove(players.find(defender));
                        teams.get(find(defender.getOwner())).removePlayer(defender);
                    }
                }
                case "green" -> {
                    if (defender.getType().equals("blue")) {
                        map[attacker.getY()][attacker.getX()].free();
                        players.remove(attacker);
                        teams.get(currentTeamId).removePlayer(attacker);
                        return true;
                    } else {
                        target.free();
                        players.remove(players.find(defender));
                        teams.get(find(defender.getOwner())).removePlayer(defender);
                    }
                }
                case "red" -> {
                    if (defender.getType().equals("green")) {
                        map[attacker.getY()][attacker.getX()].free();
                        players.remove(attacker);
                        teams.get(currentTeamId).removePlayer(attacker);
                        return true;
                    } else {
                        target.free();
                        players.remove(players.find(defender));
                        teams.get(find(defender.getOwner())).removePlayer(defender);
                    }
                }
            }
        }
        return false;
    }


    //TODO types
    private boolean attackInMap(int x, int y, String type, Player defender) {
        Tile target = map[y][x];
        defender = target.getOccupier();
        if (target.isOccupied() && !defender.ownerIs(teams.get(currentTeamId))) {
            target.free();
            return true;
        }
        return false;
    }

    private boolean isInBounds(int x, int y) {
        return (x >= 0 && x < wd) && (y >= 0 && y < ht);
    }

    @Override
    public int move(int x, int y, String directionName, int numberOfMoves) {
        if (!isInBounds(x, y))
            return 1;

        int direction = 0;
        switch (directionName) {
            case NORTH -> direction = N_DIRECTION;
            case WEST -> direction = W_DIRECTION;
            case EAST -> direction = E_DIRECTION;
            case SOUTH -> direction = S_DIRECTION;
        }
        if (direction == 0)
            return 2;

        Player playerMoving = findPlayer(x, y);
        if (playerMoving == null)
            return 3;

        if (!playerMoving.getType().equals("red") && numberOfMoves != 1)
            return 4;

        int newX = x;
        int newY = y;
        switch (direction) {
            case N_DIRECTION -> x--;
            case W_DIRECTION -> y--;
            case E_DIRECTION -> y++;
            case S_DIRECTION -> x++;
        }
        if (findPlayer(newX, newY) != null)
            return 5;

        if (!isInBounds(newX, newY))
            return 6;

        if (map[newY][newX] instanceof Bunker bunker) {
            if (!bunker.isOccupied()) {
                bunker.setOwner(playerMoving.getOwner());
                movePlayerTo(x, y, newY, newX, playerMoving);
                return 7;
            }
            Player defender = map[newY][newX].getOccupier();
            if (!startDuel(map[newY][newX], defender, playerMoving)) {
                bunker.setOwner(playerMoving.getOwner());
                movePlayerTo(x, y, newY, newX, playerMoving);
                return 10;
            }
        }
        if (map[newY][newX].isOccupied()) {
            Player defender = map[newY][newX].getOccupier();
            if (!defender.ownerIs(playerMoving.getOwner())) {
                if (!startDuel(map[newY][newX], defender, playerMoving)) {
                    movePlayerTo(x, y, newY, newX, playerMoving);
                    return 8;
                }
                return 9;
            }
        }
        movePlayerTo(x, y, newY, newX, playerMoving);
        return 0;
    }

    private void movePlayerTo(int x, int y, int newY, int newX, Player playerMoving) {
        map[newY][newX].occupy(playerMoving);
        playerMoving.move(newX, newY);
        map[y][x].free();
    }

    @Override
    public boolean update() {
        for (int i = 0; i < bunkers.len(); i++) {
            bunkers.get(i).increaseTreasure();
        }
        Team previousTeam = teams.get(currentTeamId);
        if (isThereOneTeamLeft())
            return true;
        if (previousTeam == teams.get(currentTeamId))
            currentTeamId++;
        if (currentTeamId >= teams.len()) {
            currentTeamId = 0;
        }
        return false;
    }

    @Override
    public boolean isThereOneTeamLeft() {
        int i = 0;
        Team team;
        while (i < teams.len()) {
            team = teams.get(i);
            if (team.getBunkersLen() == 0 && team.getPlayersLen() == 0) {
                teams.remove(i);
            } else {
                i++;
            }
        }
        return teams.len() == 1;
    }
}
