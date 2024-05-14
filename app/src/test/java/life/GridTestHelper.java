package life;

public class GridTestHelper {

    public static String toGrid(int height, int width, String seed) {
        String grid[][] = new String[height][width];
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < width; ++col) {
                grid[row][col] = " ";
            }
        }
        for (Position pos : Position.parse(seed)) {
            grid[pos.row()][pos.col()] = "X";
        }
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < height; ++row) {
            builder.append("[");
            builder.append(String.join("", grid[row]));
            builder.append("]\n");
        }
        return builder.toString();
    }

}
