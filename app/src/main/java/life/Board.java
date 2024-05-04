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

        grid = newGrid();
    }

    private boolean[][] newGrid() {
        return new boolean[height][width];
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

    private boolean inRange(Cell c) {
        return inRange(c.x(), c.y());
    }

    private boolean inRange(int x, int y) {
        return y >= 0 && y < height &&
                x >= 0 && x < width;
    }

    class OutOfRangeException extends IlegalSeedException {
        public OutOfRangeException(String seed, Cell cell) {
            super("\"" + seed + "\", out of range cell " + cell);
        }
    }

    /**
     * Combine all living cells into one JSON array,
     * 
     * @return a string og the form "[[a, b], [c, d], ...]" with the coordinates of
     *         the living cells
     */
    public String getLiveCells() {
        var liveCells = new ArrayList<Cell>();
        walkTheGrid((alive, x, y) -> {
            if (alive) {
                liveCells.add(new Cell(x, y));
            }
        });
        return liveCells.toString();
    }

    private void walkTheGrid(GridWalker gridWalker) {
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                gridWalker.visit(grid[row][col], col, row);
            }
        }
    }

    private interface GridWalker {
        void visit(boolean b, int col, int row);
    }

    /**
     * Run once life cycle
     */
    public void tick() {
        // we need a fresh instance of the grid, so that as we calculate and perform the updates
        // we don't modify the cells we haven't processed yet
        boolean[][] newGrid = newGrid();

        // this walks over the old grid
        walkTheGrid((alive, x, y) -> {
            switch (neighbourCount(x, y)) {
                // we update the new grid
                case 2 -> {
                    newGrid[y][x] = alive;
                }
                case 3 -> {
                    newGrid[y][x] = true;
                }
                default -> {
                    // all other cases die, so no action, newGrid is by default dead.
                }
            }
        });
        // Only now we can "commit" all the changes at once
        grid = newGrid;
    }

    private int neighbourCount(int x, int y) {
        return neighbour(x - 1, y - 1)
                + neighbour(x - 1, y)
                + neighbour(x - 1, y + 1)
                + neighbour(x, y - 1)
                + neighbour(x, y + 1)
                + neighbour(x + 1, y - 1)
                + neighbour(x + 1, y)
                + neighbour(x + 1, y + 1);
    }

    private int neighbour(int x, int y) {
        return inRange(x, y) && grid[y][x] ? 1 : 0;

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

}
