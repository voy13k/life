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

    public static void main(String[] args) {
        Board board = new Board();
        try {
            board.seed(String.join(" ", args));
        } catch (IlegalSeedException e) {
            System.err.printf(
                    "%s Coordinates expected in the form of \"[[m,n],[o,p],...]\"",
                    e.getMessage());
            return;
        }

        IntStream.rangeClosed(1, NUMBER_OF_CYCLES).forEachOrdered(cycle -> {
            board.tick();
            System.out.printf("%6d: %s%n", cycle, board);
        });
    }

}
