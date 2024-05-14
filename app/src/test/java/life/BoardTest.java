package life;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Board board = new Board();

    @Test
    void toString_shouldRetrunNoLiveCellsIfNotSeeded() {
        assertThat(board, hasToString("[]"));
    }

    @Test
    void toString_shouldReturnSeededCells() {
        board.seed("[[2,3],[3,1]]");
        String toString = board.toString();
        assertThat(toString, Matchers.containsString("[2,3]"));
        assertThat(toString, Matchers.containsString("[3,1]"));
    }

}
