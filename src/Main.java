import PaintBall.*;

import java.util.Scanner;

public class Main {

    public static final String ENTRY_POINT = ">";
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

                    }
                    case HELP -> help(false);
                    case QUIT -> System.out.println("bye");
                }
            } else {
                switch (command) {
                    case GAME -> game = startGame(sc);
                    case MOVE -> move(game, sc);
                    case CREATE -> create(game, sc);
                    case ATTACK -> System.out.println("a");
                    case STATUS -> status(game, sc);
                    case MAP -> map(game);
                    case BUNKERS -> bunkers(game);
                    case PLAYERS -> players(game);
                    case HELP -> help(true);
                    case QUIT -> System.out.println("bye");
                }
                currentTeam = game.getCurrentTeam();
            }
        }
        while (!command.equals(QUIT));
    }

    private static void players(Game game) {
        PlayerIterator PIt = game.getCurrentTeamsPlayers();
        Player player = null;
        while (PIt.hasNext()) {
            player = PIt.next();
            System.out.println(player.getType() + " player in position (" + (player.getX() + 1) + ", " + (player.getY() + 1) + ")");
        }
        if (player == null) {
            System.out.println("Without players.");
        }
    }

    private static void map(Game game) {
        System.out.println(game.getX() + " " + game.getY());
        System.out.print("**1");
        for (int i = 2; i <= game.getX(); i++) {
            System.out.print(i);
        }
        for (int i = 0; i < game.getX(); i++) {
            for (int j = 0; j < game.getY(); j++) {
                System.out.print(i + 1);
                System.out.print(" " + game.getTileAt(i, j));
            }
            System.out.println();
        }
    }

    private static Game startGame(Scanner sc) {
        int width = sc.nextInt();
        int length = sc.nextInt();
        int teamsLength = sc.nextInt();
        int bunkersLength = sc.nextInt();
        Game game = new GameClass(width, length, teamsLength, bunkersLength);
        addBunkers(sc, bunkersLength, game);
        addTeams(sc, bunkersLength, game);
        return game;
    }

    private static void addTeams(Scanner sc, int bunkersLength, Game game) {
        for (int i = 0; i < bunkersLength; i++) {
            String name = sc.next();
            int bunkerId = game.getBunkerId(sc.next());
            if (game.verifyTeamParameters(bunkerId, name)) {
                game.addTeam(bunkerId, name);
            } else {
                System.out.println("Team not created.");
            }
        }
        if (game.getNumberOfTeams() < 2) {
            System.out.println("FATAL ERROR: Insufficient number of teams.");
            game = null;
        }
    }

    private static void addBunkers(Scanner sc, int bunkersLength, Game game) {
        for (int i = 0; i < bunkersLength; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int treasure = sc.nextInt();
            String name = sc.next();
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

    private static void status(Game game, Scanner sc) {

        Iterator<Tile> BIt = game.getBunkerIterator();
        Iterator<Team> TIt = game.getTeamIterator();
        int x = game.getX();
        int y = game.getY();
        int numberOfBunkers = game.getNumberOfBunkers();
        Team team;
        Bunker bunker;
        String bunkerName;
        int bunkerOwnerId;
        String teamName;
        int numberOfTeams = game.getNumberOfTeams();

        System.out.println(x + " " + y);
        System.out.println(numberOfBunkers + " bunkers:");
        while (BIt.hasNext()) {
            bunker = ((Bunker) BIt.next());
            if (bunker.exists()) {
                bunkerName = bunker.toString();
                System.out.print(bunkerName);
                bunkerOwnerId = bunker.getOwnerId();
                if (bunkerOwnerId != 0) {
                    teamName = game.getTeamName(bunkerOwnerId);
                    System.out.println(" (" + teamName + ")");
                } else System.out.println(" (without owner)");
            }
        }
        System.out.println(numberOfTeams + " teams:");
        while (TIt.hasNext()) {
            team = TIt.next();
            if (team.exists()) {
                teamName = team.toString();
                System.out.print(teamName + "; ");
            }
        }

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
        numberOfOwnBunkers = team.getBunkerLen();
        System.out.println(numberOfOwnBunkers + " bunkers:");
        bunker = (Bunker) GBI.next();
        if (bunker != null) {
            while (GBI.hasNext() && GBI.next() != null) {
                String bunkerName = bunker.toString();
                int bunkerTreasure = bunker.getTreasure();
                int bunkerX = bunker.getX();
                int bunkerY = bunker.getY();
                System.out.printf("%s with %d coins in position (%d, %d)\n", bunkerName, bunkerTreasure, bunkerX, bunkerY);

                bunker = (Bunker) GBI.next();
            }
        } else System.out.println("Without bunkers.");

    }

    private static void create(Game game, Scanner sc) {
        Iterator<Team> TIt = game.getTeamIterator();
        String playerType = sc.next();
        String bunkerName = sc.next();
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
            System.out.println("Non- existent bunker.");
        } else if (bunker == null) {
            System.out.println("Bunker illegally invaded.");
        } else if (bunker.isOccupied()) {
            System.out.println("Bunker not free.");
        } else if (bunker.getTreasure() - game.playerPrice(playerType) < 0) {
            System.out.println("Insufficient coins for recruitment.");
        } else game.createPlayer(playerType, bunkerName);

    }

    private static void move(Game game, Scanner sc) {
        // Af fazer error msgs
        int x = sc.nextInt();
        int y = sc.nextInt();
        String input = sc.nextLine();
        String[] inputParts = input.split(" ");
        game.move(x, y, inputParts);
    }
}