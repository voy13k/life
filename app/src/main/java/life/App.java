package life;

public class App {

    public static void main(String[] args) {
        String error = "USAGE:";
        try {
            if (args.length > 0) {
                Board board = new Board(200, 200);
                board.seed(String.join(" ", args));
                System.out.println(board.getLiveCells());
                return;
            }
        } catch (InvalidSeedException e) {
            error = e.getMessage();
        }
        System.err.println(error + " Coordinates expected in the form of \"[[m,n],[o,p],...]\"");
    }

}
