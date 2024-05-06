package life;

import java.util.stream.IntStream;

/**
 * <p>
 * Input of coordinate pairs of the initial live cell positions
 * in the form of a JSON list [[a,b],[c.d],...]
 * is taken from the invocation argumens.
 * All arguments are joined together using a space separator
 * and then processed as a single string.
 * <p>
 * The output list of the end state live cell positions
 * is printed on the std out in the same format.
 */
public class App {

    private static final int NUMBER_OF_CYCLES = 100;
    private static final int BOARD_HEIGHT = 200;
    private static final int BOARD_WIDTH = 200;

    public static void main(String[] args) {
        Board board = new Board(BOARD_HEIGHT, BOARD_WIDTH);
        try {
            board.seed(String.join(" ", args));
        } catch (IlegalSeedException e) {
            System.err.printf(
                    "%s Coordinates expected in the form of \"[[m,n],[o,p],...]\" wthin the [1..%d, 1..%d] range, inclusive.",
                    e.getMessage(),
                    BOARD_HEIGHT,
                    BOARD_WIDTH);
            return;
        }

        IntStream.rangeClosed(1, NUMBER_OF_CYCLES).forEachOrdered(cycle -> {
            board.tick();
            System.out.printf("%6d: %s%n", cycle, board);
        });
    }

}
