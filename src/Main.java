import PaintBall.*;

import java.util.Scanner;

public class Main {

    public static final String ENTRY_POINT = "> ";
    public static final String GAME = "game";
    public static final String HELP = "help";
    public static final String MOVE = "move";
    public static final String CREATE = "create";
    public static final String ATTACK = "attack";
    public static final String STATUS = "status";
    public static final String MAP = "map";
    public static final String BUNKERS = "bunkers";
    public static final String PLAYERS = "players";
    public static final String QUIT = "quit";
    public static final String BYE = "Bye.";
    public static final String INVALID_COMMAND = "Invalid command.";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String command;
        Game game = null;
        String currentTeam = "";

        do {
            System.out.print(currentTeam + ENTRY_POINT);
            command = sc.next().toLowerCase();
            if (game == null) {
                switch (command) {
                    case GAME -> {
                        game = startGame(sc);
                        currentTeam = (game == null) ? "" : game.getCurrentTeam();
                    }
                    case HELP -> help(false);
                    case QUIT -> System.out.println(BYE);
                    default -> {
                        System.out.println(INVALID_COMMAND);
                        clean(sc);
                    }
                }
            } else {
                switch (command) {
                    case GAME -> game = startGame(sc);
                    case MOVE -> game = move(game, sc.nextInt(), sc.nextInt(), sc.nextLine().trim());
                    case CREATE -> game = create(game, sc.next().trim(), sc.nextLine().trim());
                    case ATTACK -> game = attack(game);
                    case STATUS -> status(game);
                    case MAP -> map(game);
                    case BUNKERS -> bunkers(game);
                    case PLAYERS -> players(game);
                    case HELP -> help(true);
                    case QUIT -> System.out.println("Bye.");
                    default -> {
                        System.out.println(INVALID_COMMAND);
                        clean(sc);
                    }
                }
                currentTeam = (game == null) ? "" : game.getCurrentTeam();
            }
        }
        while (!command.equals(QUIT));
    }

    private static void clean(Scanner sc) {
        if (sc.hasNext()) {
            sc.nextLine();
        }
    }

    private static Game attack(Game game) {
        game.attack();
        map(game);
        return update(game);
    }

    private static void players(Game game) {
        Iterator<Team> TIt = game.getTeamIterator();
        PlayerIterator PIt = game.getCurrentTeamsPlayers();
        Team team = null;
        boolean loop = true;
        while (TIt.hasNext() && loop) {
            team = TIt.next();
            if (team.toString().equals(game.getCurrentTeam())) {
                loop = false;
            }
        }
        Player player = null;
        if (team.getPlayersLen() > 0) {
            System.out.printf("%d players:\n", team.getPlayersLen());
        }

        while (PIt.hasNext()) {
            player = PIt.next();
            System.out.println(player.getType() + " player in position (" + (player.getX() + 1) + ", " + (player.getY() + 1) + ")");
        }
        if (player == null) {
            System.out.println("Without players.");
        }
    }

    private static void map(Game game) {
        System.out.println(game.getY() + " " + game.getX());
        System.out.print("**1");
        for (int i = 2; i <= game.getY(); i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (int i = 0; i < game.getX(); i++) {
            System.out.print(i + 1);
            for (int j = 0; j < game.getY(); j++) {
                System.out.print(" " + game.getTileAt(j, i));
            }
            System.out.println();
        }
    }

    private static Game startGame(Scanner sc) {
        int width = sc.nextInt();
        int length = sc.nextInt();
        int teamsLength = sc.nextInt();
        int bunkersLength = sc.nextInt();
        Game game = new GameClass(width, length);
        System.out.println(bunkersLength + " bunkers:");
        addBunkers(sc, bunkersLength, game);
        System.out.println(teamsLength + " teams:");
        game = addTeams(sc, teamsLength, game);
        return game;
    }

    private static Game addTeams(Scanner sc, int teamsLength, Game game) {
        for (int i = 0; i < teamsLength; i++) {
            String name = sc.next();
            int bunkerId = game.getBunkerId(sc.nextLine().trim());
            if (game.verifyTeamParameters(bunkerId, name)) {
                game.addTeam(bunkerId, name);
            } else {
                System.out.println("Team not created.");
            }
        }
        if (game.getNumberOfTeams() < 2) {
            System.out.println("FATAL ERROR: Insufficient number of teams.");
            return null;
        }
        return game;
    }

    private static void addBunkers(Scanner sc, int bunkersLength, Game game) {
        for (int i = 0; i < bunkersLength; i++) {
            int x = sc.nextInt() - 1;
            int y = sc.nextInt() - 1;
            int treasure = sc.nextInt();
            String name = sc.nextLine().trim();
            if (game.verifyBunkerParameters(x, y, treasure, name)) {
                game.addBunker(x, y, treasure, name);
            } else {
                System.out.println("Bunker not created.");
            }
        }
    }

    private static void help(boolean gameIsInitialized) {
        System.out.println("game - Create a new game");
        if (gameIsInitialized) {
            System.out.println("move - Move a player");
            System.out.println("create - Create a player in a bunker");
            System.out.println("attack - Attack with all players of the current team");
            System.out.println("status - Show the current state of the game");
            System.out.println("map - Show the map of the current team");
            System.out.println("bunkers - List the bunkers of the current team, by the order they were seized");
            System.out.println("players - List the active players of the current team, by the order they were created");
        }
        System.out.println("help - Show available commands");
        System.out.println("quit - End program execution");
    }

    private static void status(Game game) {
        Iterator<Tile> BIt = game.getBunkerIterator();
        Iterator<Team> TIt = game.getTeamIterator();
        int x = game.getX();
        int y = game.getY();
        int numberOfBunkers = game.getNumberOfBunkers();
        Team team;
        Bunker bunker;
        String teamName;
        int numberOfTeams = game.getNumberOfTeams();

        System.out.println(y + " " + x);
        System.out.println(numberOfBunkers + " bunkers:");
        while (BIt.hasNext()) {
            bunker = (Bunker) BIt.next();
            System.out.print(bunker.toString());
            if (bunker.getOwner() != null) {
                teamName = bunker.getOwner().toString();
                System.out.println(" (" + teamName + ")");
            } else System.out.println(" (without owner)");
        }
        System.out.println(numberOfTeams + " teams:");
        System.out.print(TIt.next().toString());
        while (TIt.hasNext()) {
            System.out.print("; " + TIt.next().toString());
        }
        System.out.println();
    }

    private static void bunkers(Game game) {
        Iterator<Team> TIt = game.getTeamIterator();
        String currentTeamName = game.getCurrentTeam();
        String tempName;
        Team team = null;
        boolean foundTeam = false;
        int numberOfOwnBunkers;
        Bunker bunker;
        while (TIt.hasNext() && !foundTeam) {
            team = TIt.next();
            tempName = team.toString();
            foundTeam = tempName.equals(currentTeamName);
        }
        Iterator<Tile> GBI = team.getBunkerIterator();
        numberOfOwnBunkers = team.getBunkersLen();
        System.out.println(numberOfOwnBunkers + " bunkers:");
        while (GBI.hasNext()) {
            bunker = (Bunker) GBI.next();
            System.out.printf("%s with %d coins in position (%d, %d)\n",
                    bunker.toString(), bunker.getTreasure(), bunker.getX() + 1, bunker.getY() + 1);
        }
        if (game.getNumberOfBunkers() == 0)
            System.out.println("Without bunkers.");

    }

    private static Game create(Game game, String playerType, String bunkerName) {
        Iterator<Team> TIt = game.getTeamIterator();
        Team team = null;
        boolean isTeamFound = false;
        Bunker bunker = null;
        while (TIt.hasNext() && !isTeamFound) {
            team = TIt.next();
            if (team.toString().equals(game.getCurrentTeam())) {
                isTeamFound = true;
            }
        }
        Iterator<Tile> tempB = team.getBunkerIterator();
        while (tempB.hasNext()) {
            Bunker tempBunker = (Bunker) tempB.next();
            if (tempBunker.toString().equals(bunkerName)) {
                bunker = tempBunker;
            }
        }
        if (!game.checkPlayerType(playerType)) {
            System.out.println("Non-existent player type.");
        } else if (game.getBunkerId(bunkerName) == -1) {
            System.out.println("Non-existent bunker.");
        } else if (bunker == null) {
            System.out.println("Bunker illegally invaded.");
        } else if (bunker.isOccupied()) {
            System.out.println("Bunker not free.");
        } else if (bunker.getTreasure() < game.playerPrice(playerType)) {
            System.out.println("Insufficient coins for recruitment.");
        } else {
            game.createPlayer(playerType, bunkerName);
            System.out.printf("%s player created in %s\n", playerType, bunkerName);
        }
        return update(game);
    }


    private static Game move(Game game, int x, int y, String input) {
        x -= 1;
        y -= 1;
        String[] directions = input.split("\\s+");
        int i = 0;
        int numberOfMoves = directions.length;
        boolean playerIsMoving = true;
        Iterator<Player> pIt = game.getCurrentTeamsPlayers();
        Player thisPlayer = null;

        while (pIt.hasNext() && thisPlayer == null) {
            Player otherPlayer = pIt.next();
            if (otherPlayer.getX() == x && otherPlayer.getY() == y)
                thisPlayer = otherPlayer;
        }
        do {
            System.out.println("x = " + x + ", y = " + y);
            System.out.println(game.findPlayer(x, y));
            switch (game.move(x, y, directions[i], numberOfMoves)) {

                case 1 -> System.out.println("Invalid position.");
                case 2 -> System.out.println("Invalid direction.");
                case 3 -> {
                    System.out.println("No player in that position.");
                    playerIsMoving = false;
                }
                case 4 -> {
                    System.out.println("Invalid move.");
                    playerIsMoving = false;
                }
                case 5 -> System.out.println("Position occupied.");
                case 6 -> System.out.println("Trying to move off the map.");
                case 7 -> {
                    x = thisPlayer.getX();
                    y = thisPlayer.getY();
                    System.out.println("Bunker seized.");
                    System.out.printf("%s player in position (%d, %d)\n", thisPlayer.getType(), x + 1, y + 1);
                }
                case 8 -> {
                    x = thisPlayer.getX();
                    y = thisPlayer.getY();
                    System.out.println("Won the fight.");
                    System.out.printf("%s player in position (%d, %d)\n", thisPlayer.getType(), x + 1, y + 1);

                }
                case 9 -> {
                    x = thisPlayer.getX();
                    y = thisPlayer.getY();
                    System.out.println("Won the fight and bunker seized.");
                    System.out.printf("%s player in position (%d, %d)\n", thisPlayer.getType(), x + 1, y + 1);

                }
                case 10 -> {
                    System.out.println("Player eliminated.");
                    playerIsMoving = false;
                }
                case 0 -> {
                    x = thisPlayer.getX();
                    y = thisPlayer.getY();
                    System.out.printf("%s player in position (%d, %d)\n",
                            thisPlayer.getType(), x + 1, y + 1);
                }
            }
            i++;
            numberOfMoves--;
        } while (numberOfMoves > 0 && playerIsMoving && !game.isThereOneTeamLeft());
        return update(game);
    }

    private static Game update(Game game) {
        if (game.update()) {
            System.out.printf("Winner is %s.\n", game.getCurrentTeam());
            return null;
        }
        return game;
    }
}