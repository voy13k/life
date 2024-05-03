package life;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import life.Board.OutOfRangeException;

class BoardTest {

    @Test
    void shouldCreateDeadGridOfRightSize() {
        Board board = new Board(3, 4);
        assertEquals("""
                [false, false, false]
                [false, false, false]
                [false, false, false]
                [false, false, false]
                """, board.toString());
    }

    @Test
    void shouldRetrunNoLiveCellsIfNotSeeded() {
        Board board = new Board(3, 4);
        assertEquals("[]", board.getLiveCells());
    }

    @Test
    void shouldReturnSeededCells() {
        Board board = new Board(3, 4);
        board.seed("[[3,2],[1,3]]");
        assertEquals("[[3, 2], [1, 3]]", board.getLiveCells());
    }

    @Test
    void shouldOrderByRowFirst() {
        Board board = new Board(3, 4);
        board.seed("[[1,3],[3,2]]");
        assertEquals("[[3, 2], [1, 3]]", board.getLiveCells());
    }

    @ParameterizedTest()
    @ValueSource(strings = {
            "[[0, 1]]",
            "[[4, 1]]",
            "[[1, 0]]",
            "[[1, 5]]"
    })
    void shouldThrowExceptionWhenCellOutsideGrid(String seed) {
        Board board = new Board(3, 4);
        var exception = assertThrows(OutOfRangeException.class,
                () -> {
                    board.seed(seed);
                });
        assertTrue(exception.getMessage().contains(seed));
    }

}
