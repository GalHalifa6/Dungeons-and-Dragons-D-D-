import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CLI {
    private static final List<String> PLAYER_SELECTION_INPUTS =
            Arrays.asList("1", "2", "3", "4", "5", "6", "7");
    private static final List<String> GAME_INPUTS =
            Arrays.asList("w", "s", "a", "d", "e", "q");

    private static final Scanner IN = new Scanner(System.in);

    private Manager manager;
    private Board board;

    public CLI(Manager manager) {
        this.manager = manager;
        manager.setCli(this);
    }

    public void printBoard() {
        if (board == null) return;
        for (int i = 0; i < board.getWidth(); i++) {
            System.out.println();
            for (int j = 0; j < board.getLength(); j++) {
                System.out.print(board.getTile(i, j).getChar());
            }
        }
        System.out.println();
    }

    public boolean legalInput(String input) {
        if (input == null) return false;
        List<String> valid = manager.isPlayerExists() ? GAME_INPUTS : PLAYER_SELECTION_INPUTS;
        return valid.contains(input);
    }

    /** קורא פקודת משחק אחת (w/a/s/d/e/q) ושולח ל-Manager. */
    public void acceptInput() {
        String input = getValidInput();
        char c = input.charAt(0);
        manager.acceptInput(c);
    }

    public void print(String msg) {
        System.out.println(msg);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    /** בחירת שחקן ('1'..'7'). */
    public char choosePlayer() {
        String input = getValidInput();
        return input.charAt(0);
    }

    
    private String getValidInput() {
        while (true) {
            if (!IN.hasNextLine()) {
                throw new IllegalStateException("STDIN closed");
            }
            String input = IN.nextLine().trim();
            if (legalInput(input)) {
                return input;
            }
            System.out.println("Invalid input. Please try again:");
        }
    }
}
