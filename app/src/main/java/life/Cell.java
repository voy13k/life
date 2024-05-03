package life;

import java.util.ArrayList;

import org.json.JSONArray;

/**
 * Coordinates count from 1
 */
public record Cell(int x, int y) {

    public static Cell[] parse(String seed) {
        var cells = new ArrayList<Cell>();
        try {
            new JSONArray(seed).forEach(s -> {
                if (!(s instanceof JSONArray coords)) {
                    throw new InvalidSeedException(seed);
                }
                if (coords.length() != 2) {
                    throw new InvalidSeedException(seed);
                }
                cells.add(new Cell(coords.getInt(0), coords.getInt(1)));
            });
        } catch (Exception e) {
            throw new InvalidSeedException(seed);
        }
        return cells.toArray(new Cell[cells.size()]);
    }

    @Override
    public final String toString() {
        return String.format("[%d, %d]", x, y);
    }
}

class InvalidSeedException extends RuntimeException {
    public InvalidSeedException(String seed) {
        super(String.format("Invalid input '%s'. Coordinates of the form [[n,o],[p,q]...] expected.", seed));
    }
}
