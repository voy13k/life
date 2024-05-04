package life;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    // true means alive, false means dead.
    // NOTE!!! first index is row number, which is y coordinate,
    // so the cell address is grid[y][x],
    // could be confusing.
    private boolean grid[/* rows */][/* cols */];
    private int width;
    private int height;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        grid = new boolean[height][width];
    }

    /**
     * Set the provided cells alive
     * 
     * @param seed in the form of "[[a, b], [c, d], ...]"
     */
    public void seed(String seed) {
        Cell[] cells = Cell.parse(seed);
        Arrays.stream(cells).forEach(c -> {
            if (!inRange(c)) {
                throw new OutOfRangeException(seed, c);
            }
            grid[c.y()][c.x()] = true;
        });
    }

    /**
     * Combine all living cells into one JSON array,
     * 
     * @return a string og the form "[[a, b], [c, d], ...]" with the coordinates of
     *         the living cells
     */
    public String getLiveCells() {
        var liveCells = new ArrayList<Cell>();
        for (int row = 0; row < grid.length; ++row) {
            for (int col = 0; col < grid[row].length; ++col) {
                if (grid[row][col]) {
                    liveCells.add(new Cell(col, row));
                }
            }
        }
        return liveCells.toString();
    }

    public void tick() {
    }

    /**
     * <p>
     * Print the state of the board, live cells represented by 'X'
     * </p>
     * e.g. a Board(4,5) with 4 live cells:
     * 
     * <pre>
     *[  XX ]
     *[  X  ]
     *[     ]
     *[ X   ]
     * </pre>
     * <p>
     * x coordinates go left to right
     * </p>
     * <p>
     * y coordinates go top to bottom,
     * </p>
     * the board above has live cells equal [[3,3], [4,1], [3,2], [2,4]]
     */
    @Override
    public String toString() {
        var builder = new StringBuilder();
        Arrays.stream(grid).forEach(row -> {
            builder.append("[");
            for (boolean c : row) {
                builder.append(c ? "X" : " ");
            }
            builder.append("]\n");
        });
        return builder.toString();
    }

    class OutOfRangeException extends IlegalSeedException {
        public OutOfRangeException(String seed, Cell cell) {
            super("\"" + seed + "\", out of range cell " + cell);
        }
    }

    private boolean inRange(Cell c) {
        return c.y() >= 0 && c.y() < height &&
                c.x() >= 0 && c.x() < width;
    }

}
