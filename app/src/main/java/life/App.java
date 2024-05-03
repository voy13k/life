package life;

public class App {

    private static final int REQUIRED_BOARD_SIZE = 200;

    public static void main(String[] args) {
        Board board = new Board(REQUIRED_BOARD_SIZE, REQUIRED_BOARD_SIZE);
        try {
            board.seed(String.join(" ", args));
        } catch (IlegalSeedException e) {
            System.err.printf(
                    "%s Coordinates expected in the form of \"[[m,n],[o,p],...]\" wthin the 1..%d range.",
                    e.getMessage(),
                    REQUIRED_BOARD_SIZE);
            return;
        }
        System.out.printf("%s%n", board.getLiveCells());
    }

}
