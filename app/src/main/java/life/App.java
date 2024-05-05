package life;

import java.util.stream.IntStream;

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
                    "%s Coordinates expected in the form of \"[[m,n],[o,p],...]\" wthin the 1..%d range.",
                    e.getMessage(),
                    BOARD_WIDTH);
            return;
        }

        IntStream.rangeClosed(1, NUMBER_OF_CYCLES).forEachOrdered(cycle -> {
            board.tick();
            System.out.printf("%6d: %s%n", cycle, board.getLiveCells());
        });
    }

}
