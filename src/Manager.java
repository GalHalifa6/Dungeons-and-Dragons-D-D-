import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Manager {
    private Board board;
    private Player player;
    private boolean gameOver;
    private int gameLevel;
    private CLI cli;
    private List<Enemy> enemies;
    private List<Tile> tiles;
    private String path; 

    public Manager() {
        this.cli = new CLI(this);
        this.tiles = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.gameOver = false;
        this.gameLevel = 1;
        Tile.manager = this;
    }

    public void setPlayer(Player player) { this.player = player; }
    public void setEnemies(List<Enemy> enemies) { this.enemies = enemies; }
    public void setBoard(Board board) { this.board = board; }
    public void setCli(CLI cli) { this.cli = cli; }
    public boolean isPlayerExists() { return player != null; }
    public void endGame() { gameOver = true; }
    public void sendMessage(String msg) { cli.print(msg); }
    public void addToTiles(Tile tile){ tiles.add(tile); }
    public void is_GameOver(){ gameOver = true; } 

    public void runGame() {
        while (!gameOver) {
            cli.acceptInput();
            enemies.forEach(Enemy::on_GameTick);
            onGameTick();
        }
    }

    public void onGameTick() {
        player.on_GameTick();
        sendMessage(player.description());
        cli.printBoard();
    }

    public void removeEnemy(Enemy enemy) {
        player.swapTiles(enemy);
        Tile newTile = new Empty(enemy.getX(), enemy.getY());
        tiles.add(newTile);
        board.replaceTile(newTile);
        enemies.remove(enemy);

        if (enemies.isEmpty()) {
            sendMessage("You beat level " + gameLevel + ". Good job!");
            nextLevel();
        }
    }

    public void acceptInput(char input) {
        switch (input) {
            case 'e' -> player.castAbility();
            case 'w' -> player.moveUp();
            case 's' -> player.moveDown();
            case 'a' -> player.moveLeft();
            case 'd' -> player.moveRight();
        }
    }


    public void readFile(String inputPath) {
        System.out.println(inputPath);
        this.path = inputPath;

        File resolved = resolveLevelFile(inputPath, gameLevel);
        if (resolved == null || !resolved.exists()) {
            sendMessage("You win! (missing file for level " + gameLevel + ")");
            endGame();
            return;
        }

    
        this.tiles = new ArrayList<>();
        this.enemies = new ArrayList<>();

        int rows = 0, cols = 0;
        try (Scanner s = new Scanner(resolved)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                rows++;
                cols = Math.max(cols, line.length());
            }
        } catch (FileNotFoundException e) {
            sendMessage("You win! (cannot open level " + gameLevel + ")");
            endGame();
            return;
        }

        this.board = new Board(rows, cols);
        cli.setBoard(board);

        try (Scanner s = new Scanner(resolved)) {
            int x = 0;
            while (s.hasNextLine()) {
                char[] line = s.nextLine().toCharArray();
                for (int y = 0; y < line.length; y++) {
                    initializeTile(line[y], x, y);
                }
                x++;
            }
        } catch (FileNotFoundException e) {
            sendMessage("You win! (cannot reopen level " + gameLevel + ")");
            endGame();
            return;
        }

        cli.printBoard();
    }

    private File resolveLevelFile(String inputPath, int level) {
        File input = new File(inputPath);

        if (input.isDirectory()) {
            File f = new File(input, "level" + level + ".txt");
            if (f.exists()) return f;

            File alt1 = new File(input, "level" + level);          
            File alt2 = new File(input, "level" + level + "txt");   
            if (alt1.exists()) return alt1;
            if (alt2.exists()) return alt2;

            return f;
        } else {
            return input;
        }
    }

    private void initializeTile(char c, int x, int y) {
        switch (c) {
            case '.' -> new Empty(x, y);
            case '#' -> new Wall(x, y);
            case 's' -> new Monster('s', x, y, "Lannister Soldier", 80, 8, 3, 3, 25);
            case 'k' -> new Monster('k', x, y, "Lannister Knight", 200, 14, 8, 4, 50);
            case '@' -> initializePlayer(x, y);
            default -> new Empty(x, y); 
        }
    }

    private void initializePlayer(int x, int y) {
        if (player == null) {
            sendMessage("Select Player:");
            displayPlayerOptions();
            char choice = cli.choosePlayer();
            createPlayer(choice, x, y);
            sendMessage("You chose: " + player.getName());
        } else {
            player.setX(x);
            player.setY(y);
            board.replaceTile(player);
        }
    }

    private void displayPlayerOptions() {
        sendMessage("1. Warrior: John Snow");
        sendMessage("2. Mage: Melisandre");
    }

    private void createPlayer(char choice, int x, int y) {
        switch (choice) {
            case '1' -> new Warrior(x, y, "John Snow", 300, 30, 4, 3);
            case '2' -> new Mage(x, y, "Melisandre", 100, 5, 1, 300, 30, 15, 5, 6);
            default  -> new Warrior(x, y, "John Snow", 300, 30, 4, 3); // ברירת מחדל
        }
    }

    public void nextLevel() {
        gameLevel++;
        readFile(path);
    }
}
