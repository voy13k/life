package life;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Construct a playboard specifying its height and width.
 * <p>
 * Use {@link #seed(String)} to populate it with live cells.
 * <p>
 * Invoke {@link #tick()} repeatedly to run the generation cycles,
 * one generantion at a time.
 * <p>
 * At any time, use {@link #getLiveCells()} to obtain a list of coordinates
 * of the currently living cells.
 * <p>
 * At any time, use {@link #toString()} for a visual rectangular
 * representation of current state of the board.
 */
public class Board {

    // true means alive, false means dead.
    private boolean grid[/* rows */][/* cols */];

    // handy later for inRange checks
    private int rows;
    private int cols;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        grid = newGrid();
    }

    private boolean[][] newGrid() {
        return new boolean[this.rows][this.cols];
    }

    /**
     * Set the provided cells alive.
     * It can be used at any point after construction, as an inital seed, or to add
     * life after some cycles already passed.
     * 
     * @param seed in the form of "[[a, b], [c, d], ...]"
     */
    public void seed(String seed) {
        Cell[] cells = Cell.parse(seed);
        Arrays.stream(cells).forEach(c -> {
            if (!inRange(c.row(), c.col())) {
                throw new OutOfRangeException(seed, c);
            }
            grid[c.row()][c.col()] = true;
        });
    }

    private boolean inRange(int row, int col) {
        return row >= 0 && row < this.rows &&
                col >= 0 && col < this.cols;
    }

    class OutOfRangeException extends IlegalSeedException {
        public OutOfRangeException(String seed, Cell cell) {
            super("\"" + seed + "\", out of range cell " + cell);
        }
    }

    /**
     * Combine all living cells into a JSON array string.
     * 
     * @return a string of the form "[[a, b], [c, d], ...]" with the coordinates of
     *         the living cells
     */
    public String getLiveCells() {
        var liveCells = new ArrayList<Cell>();
        walkGridRange((row, col) -> {
            if (grid[row][col]) {
                // Cell.toString() will add [ ] around individual cell coords
                liveCells.add(new Cell(row, col));
            }
        });
        // List.toString() will add outer [ ]
        return liveCells.toString();
    }

    private void walkGridRange(GridRangeWalker walker) {
        // Because we are walking over this.grid
        // which was constructed using this.height this.width
        // we can use this.height and this.width for the limits
        // as a shortcut instead of dynamic array.length
        for (int row = 0; row < this.rows; ++row) {
            for (int col = 0; col < this.cols; ++col) {
                walker.step(row, col);
            }
        }
    }

    private interface GridRangeWalker {
        void step(int row, int col);
    }

    /**
     * Run one life cycle
     */
    public void tick() {
        // we need a fresh instance of the grid, so that as we calculate
        // and perform the updates we don't modify the cells we haven't processed yet
        boolean[][] newGrid = newGrid();

        walkGridRange((row, col) -> {
            switch (neighbourCount(row, col)) {
                case 2 -> {
                    // alive stays alive, dead stays dead
                    newGrid[row][col] = grid[row][col];
                }
                case 3 -> {
                    // alive stays alive, dead becomes alive
                    newGrid[row][col] = true;
                }
                // all other cases die, so no action, newGrid cells are dead by default.
            }
        });
        // Only now we can "commit" all the changes at once
        grid = newGrid;
    }

    private int neighbourCount(int row, int col) {
        return neighbour(row - 1, col - 1)
                + neighbour(row - 1, col)
                + neighbour(row - 1, col + 1)
                + neighbour(row, col - 1)
                + neighbour(row, col + 1)
                + neighbour(row + 1, col - 1)
                + neighbour(row + 1, col)
                + neighbour(row + 1, col + 1);
    }

    private int neighbour(int row, int col) {
        return inRange(row, col) && grid[row][col] ? 1 : 0;
    }

    /**
     * <p>
     * Print the current state of the board as a rectangle,
     * representing live cells by 'X'.
     * </p>
     * e.g. a Board(4,5) with 4 live cells [[1,3], [1,4], [2,3], [4,2]]:
     * will produce
     * <pre>
     *[  XX ]
     *[  X  ]
     *[     ]
     *[ X   ]
     * </pre>
     */
    @Override
    public String toString() {
        var builder = new StringBuilder();
        Arrays.stream(grid).forEach(rowArr -> {
            builder.append("[");
            for (boolean val : rowArr) {
                builder.append(val ? "X" : " ");
            }
            builder.append("]\n");
        });
        return builder.toString();
    }

}
