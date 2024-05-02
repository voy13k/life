package life;

import java.util.Arrays;

public class Life {

    private boolean grid[/* rows */][/* cols */];

    public Life(int width, int height) {
        // true means alive, false means dead.
        // NOTE!!! first index is row number, which is y coordinate,
        // so the cell address is grid[y][x],
        // could be confusing.
        grid = new boolean[height][width];
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
