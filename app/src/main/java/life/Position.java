package life;

import java.util.ArrayList;

import org.json.JSONArray;

/**
 * <p>
 * Converts between in / out coordinate representation (1-based coordinates)
 * and internal representation (0-based array indexes).
 * </p>
 * 
 * The {@link #parse(String)} converts input to internal,
 * the {@link #toString()} converts internal to output.
 */
public record Position(int row, int col) {

    /**
     * Convert the input string containing 1-based coordinate pairs
     * into an array of Positions with 0-based coordinates.
     * E.g.
     * 
     * <pre>
     * parse("[[1, 3], [3, 4]]")
     * </pre>
     * 
     * results in
     * 
     * <pre>
     * new Position[] { Position(0, 2), Position(2, 3) }
     * </pre>
     * 
     * @param seed JSON like array of arrays, e.g. "[[1, 3], [3, 4]]", 1-based coord
     *             pairs
     * @return array of Position objects with 0 based coords,
     *         e.g. { Position(0, 2), Position(2, 3) }
     */
    public static Position[] parse(String seed) {
        var positions = new ArrayList<Position>();
        try {
            new JSONArray(seed).forEach(obj -> {
                if (!(obj instanceof JSONArray coordArr)) {
                    throw new IlegalSeedException(seed);
                }
                if (coordArr.length() != 2) {
                    throw new IlegalSeedException(seed);
                }
                int inputRow = coordArr.getInt(0);
                int inputCol = coordArr.getInt(1);
                positions.add(new Position(inputRow - 1, inputCol - 1));
            });
        } catch (Exception e) {
            throw new IlegalSeedException(seed, e);
        }
        return positions.toArray(Position[]::new);
    }

    /**
     * Produce "[row + 1, col + 1]" output coordinate tuple
     */
    @Override
    public final String toString() {
        return new JSONArray(new int[] { row + 1, col + 1 }).toString();
    }
}

class IlegalSeedException extends IllegalArgumentException {
    public IlegalSeedException(String seed, Throwable causedBy) {
        super(String.format("Invalid seed %s.", seed), causedBy);
    }

    public IlegalSeedException(String seed) {
        this(seed, null);
    }
}
