package life;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private boolean grid[/* rows */][/* cols */];

    public Board(int width, int height) {
        // true means alive, false means dead.
        // NOTE!!! first index is row number, which is y coordinate,
        // so the cell address is grid[y][x],
        // could be confusing.
        grid = new boolean[height][width];
    }

    public void seed(Cell... seeds) {
        Arrays.stream(seeds).forEach(s -> {
            try {
                grid[s.y() - 1][s.x() - 1] = true;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalCoordinatesException(s);
            }
        });
    }

    public List<Cell> getLiveCells() {
        var liveCells = new ArrayList<Cell>();
        for (int row = 0; row < grid.length; ++row) {
            for (int col = 0; col < grid[row].length; ++col) {
                if (grid[row][col]) {
                    liveCells.add(new Cell(col + 1, row + 1));
                }
            }
        }
        return liveCells;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        Arrays.stream(grid).forEach(row -> {
            builder.append(Arrays.toString(row) + '\n');
        });
        return builder.toString();
    }

}
