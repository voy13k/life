package life;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;

/**
 * <p>
 * Wraps the cell coordinates so that it cab be uased as a key
 * </p>
 * 
 * The {@link #parse(String)} converts input string set of Coord objects,
 * the {@link #toString()} produces the ouput format of [row, col].
 */
public record Position(int row, int col) {

    /**
     * <pre>
     * parse("[[1, 3], [3, 4]]")
     * </pre>
     * 
     * results in
     * 
     * <pre>
     * set of { Position(1, 3), Position(3, 4) }
     * </pre>
     * 
     * @param seed JSON like array of arrays, e.g. "[[1, 3], [3, 4]]" coord pairs
     * @return set of Position, e.g { Position(1, 3), Position(3, 4) }
     */
    public static Set<Position> parse(String seed) {
        var positions = new HashSet<Position>();
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
                positions.add(new Position(inputRow, inputCol));
            });
        } catch (Exception e) {
            throw new IlegalSeedException(seed, e);
        }
        return positions;
    }

    /**
     * Produce "[row, col]" output coordinate tuple string
     */
    @Override
    public final String toString() {
        return new JSONArray(new int[] { row, col}).toString();
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
