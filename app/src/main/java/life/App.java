package life;

public class App {

    public static void main(String[] args) {
        if (args.length == 1) {
            Board board = new Board(200, 200);
            board.seed(args[0]);
            System.out.println(board.getLiveCells());
        } else {
            System.err.println("USAGE: single coordinate list expected in the form of \"[[m,n],[o,p],...]\"");
        }
    }

}
