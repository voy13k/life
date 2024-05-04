package life;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private boolean grid[/* rows */][/* cols */];
    private int width;
    private int height;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        // true means alive, false means dead.
        // NOTE!!! first index is row number, which is y coordinate,
        // so the cell address is grid[y][x],
        // could be confusing.
        grid = new boolean[height][width];
    }

    public void seed(String seed) {
        Cell[] cells = Cell.parse(seed);
        Arrays.stream(cells).forEach(c -> {
            if (!inRange(c)) {
                throw new OutOfRangeException(seed, c);
            }
            grid[c.y()][c.x()] = true;
        });
    }

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

    @Override
    public String toString() {
        var builder = new StringBuilder();
        Arrays.stream(grid).forEach(row -> {
            builder.append("[");
            for (boolean c: row) {
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
