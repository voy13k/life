package life;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import life.Board.OutOfRangeException;

class BoardTest {

    private Board board = new Board(4, 3);

    @Test
    void getVisualGrid_shouldShowBlankGridOfRightSize() {
        assertEquals("""
                [   ]
                [   ]
                [   ]
                [   ]
                """, board.getVisualGrid());
    }

    @Test
    void getVisualGrid_shouldShowLiveCellPositions() {
        board.seed("[[2,3],[3,1],[4,2]]");
        assertEquals("""
                [   ]
                [  X]
                [X  ]
                [ X ]
                """, board.getVisualGrid());
    }

    @Test
    void toString_shouldRetrunNoLiveCellsIfNotSeeded() {
        assertEquals("[]", board.toString());
    }

    @Test
    void toString_shouldReturnSeededCells() {
        board.seed("[[2,3],[3,1]]");
        assertEquals("[[2,3], [3,1]]", board.toString());
    }

    @Test
    void toString_shouldOrderByRowFirst() {
        board.seed("[[3,1],[2,3]]");
        assertEquals("[[2,3], [3,1]]", board.toString());
    }

    @ParameterizedTest()
    @ValueSource(strings = {
            "[[1,0]]",
            "[[1,4]]",
            "[[0,1]]",
            "[[5,1]]"
    })
    void seed_shouldRejectCellsOutsideGrid(String seed) {
        var exception = assertThrows(OutOfRangeException.class,
                () -> {
                    board.seed(seed);
                });
        assertTrue(exception.getMessage().contains(seed));
    }

}
