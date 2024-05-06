package life;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import life.Board.OutOfRangeException;

class BoardTest {

    private Board board = new Board(4, 3);

    @Test
    void getVisualGrid_shouldShowBlankGridOfRightSize() {
        assertThat(board.getVisualGrid(), is("""
                [   ]
                [   ]
                [   ]
                [   ]
                """));
    }

    @Test
    void getVisualGrid_shouldShowLiveCellPositions() {
        board.seed("[[2,3],[3,1],[4,2]]");
        assertThat(board.getVisualGrid(), is("""
                [   ]
                [  X]
                [X  ]
                [ X ]
                """));
    }

    @Test
    void toString_shouldRetrunNoLiveCellsIfNotSeeded() {
        assertThat(board, hasToString("[]"));
    }

    @Test
    void toString_shouldReturnSeededCells() {
        board.seed("[[2,3],[3,1]]");
        assertThat(board, hasToString("[[2,3], [3,1]]"));
    }

    @Test
    void toString_shouldOrderByRowFirst() {
        board.seed("[[3,1],[2,3]]");
        assertThat(board, hasToString("[[2,3], [3,1]]"));
    }

    @ParameterizedTest()
    @ValueSource(strings = {
            "[[1, 0]]",
            "[[1, 4]]",
            "[[0,1]]",
            "[[5,1]]"
    })
    void seed_shouldRejectCellsOutsideGrid(String seed) {
        var exception = assertThrows(OutOfRangeException.class,
                () -> {
                    board.seed(seed);
                });
        assertThat(exception.getMessage(), containsString(seed));
    }

}
