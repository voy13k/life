package life;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Construct a playboard specifying its height and width.
 * <p>
 * Use {@link #seed(String)} to populate it with live cells.
 * <p>
 * Invoke {@link #tick()} repeatedly to run the generation cycles,
 * one generantion at a time.
 * <p>
 * At any time, use {@link #toString()} to obtain a list of coordinates
 * of the currently living cells.
 * <p>
 * At any time, use {@link #getVisualGrid()} for a visual rectangular
 * representation of current state of the board.
 */
public class Board {

    // true means alive, false means dead.
    private Set<Position> aliveCells = new HashSet<>();

    /**
     * Set the provided cells alive.
     * It can be used at any point after construction, as an inital seed, or to add
     * life after some cycles already passed.
     * 
     * @param seed in the form of "[[a, b], [c, d], ...]"
     */
    public void seed(String seed) {
        aliveCells.addAll(Position.parse(seed));
    }

    /**
     * Combine all living cell positions into a JSON array string.
     * 
     * @return a string of the form "[[a, b], [c, d], ...]" with the coordinates of
     *         the living cells
     */
    @Override
    public String toString() {
        var cellStrings = new ArrayList<String>();
        aliveCells.stream().forEach(cell -> {
            cellStrings.add(cell.toString());
        });
        return cellStrings.toString();
    }

    public void tick() {
        var newGeneration = new HashSet<Position>();

        aliveCells.stream().forEach(cell -> {
            maybeSetAlive(new Position(cell.row() - 1, cell.col() - 1), newGeneration);
            maybeSetAlive(new Position(cell.row() - 1, cell.col()), newGeneration);
            maybeSetAlive(new Position(cell.row() - 1, cell.col() + 1), newGeneration);
            maybeSetAlive(new Position(cell.row(), cell.col() - 1), newGeneration);
            maybeSetAlive(cell, newGeneration);
            maybeSetAlive(new Position(cell.row(), cell.col() + 1), newGeneration);
            maybeSetAlive(new Position(cell.row() + 1, cell.col() - 1), newGeneration);
            maybeSetAlive(new Position(cell.row() + 1, cell.col()), newGeneration);
            maybeSetAlive(new Position(cell.row() + 1, cell.col() + 1), newGeneration);
        });

        aliveCells = newGeneration;
    }

    private void maybeSetAlive(Position cell, HashSet<Position> newGeneration) {
        switch (neighbourCount(cell.row(), cell.col())) {
            case 2 -> {
                // alive stays alive, dead stays dead
                if (aliveCells.contains(cell)) {
                    newGeneration.add(cell);
                }
            }
            case 3 -> {
                newGeneration.add(cell);
            }
            // all other cases die, so no action, newGrid cells are dead by default.
        }
    }

    private int neighbourCount(int row, int col) {
        return oneIfAlive(row - 1, col - 1)
                + oneIfAlive(row - 1, col)
                + oneIfAlive(row - 1, col + 1)
                + oneIfAlive(row, col - 1)
                + oneIfAlive(row, col + 1)
                + oneIfAlive(row + 1, col - 1)
                + oneIfAlive(row + 1, col)
                + oneIfAlive(row + 1, col + 1);
    }

    private int oneIfAlive(int row, int col) {
        return aliveCells.contains(new Position(row, col)) ? 1 : 0;
    }

}
