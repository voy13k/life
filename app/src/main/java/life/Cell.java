package life;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * <p>
 * Translates between input / output coordinates representation (1-based
 * coordinates)
 * and internal representation (0 - based array indexes).
 * </p>
 * 
 * The {@link #parse(String)} translates input to internal,
 * the {@link #toString()} translates internal to output.
 */
public record Cell(int row, int col) {

    /**
     * Convert the input string containing 1-based coordinate pairs
     * into an array of Cells with 0-based coordinates.
     * E.g.
     * 
     * <pre>
     * parse("[[1, 3], [3, 4]]")
     * </pre>
     * 
     * =>
     * 
     * <pre>
     * Cell[] arr = { Cell(0, 2), Cell(2, 3) }
     * </pre>
     * 
     * @param seed JSON like array of arrays, e.g. "[[1, 3], [3, 4]]"
     * @return array of Cell objects with 0 based row/col coordinates, e.g. {
     *         Cell(0, 2), Cell(2, 3) },
     */
    public static Cell[] parse(String seed) {
        var cells = new ArrayList<Cell>();
        try {
            new JSONArray(seed).forEach(s -> {
                if (!(s instanceof JSONArray coords)) {
                    throw new IlegalSeedException(seed);
                }
                if (coords.length() != 2) {
                    throw new IlegalSeedException(seed);
                }
                int parsedRow = coords.getInt(0);
                int parsedCol = coords.getInt(1);
                cells.add(new Cell(parsedRow - 1, parsedCol - 1));
            });
        } catch (Exception e) {
            throw new IlegalSeedException(seed, e);
        }
        return cells.toArray(Cell[]::new);
    }

    @Override
    /**
     * Produce "[row + 1, col + 1]" output coordinate tuple
     */
    public final String toString() {
        return String.format("[%d, %d]", row + 1, col + 1);
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
