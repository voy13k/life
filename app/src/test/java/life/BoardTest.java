package life;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    void shouldRetrunNoboardCellsIfNotSeeded() {
        Board board = new Board(3, 4);
        assertEquals(0, board.getLiveCells().size());
    }

    @Test
    void shouldReturnSeededCells() {
        Board board = new Board(3, 4);
        board.seed(
                new Cell(3, 2),
                new Cell(1, 3));
        assertEquals(
                Arrays.asList(
                        new Cell(3, 2),
                        new Cell(1, 3)),
                board.getLiveCells());
    }

    @Test
    void shouldOrderByRowFirst() {
        Board board = new Board(3, 4);
        board.seed(
                new Cell(1, 3),
                new Cell(3, 2));
        assertEquals(
                Arrays.asList(
                        new Cell(3, 2),
                        new Cell(1, 3)),
                board.getLiveCells());
    }

    @ParameterizedTest()
    @CsvSource({
            "0, 1",
            "4, 1",
            "1, 0",
            "1, 5"
    })
    void shouldThrowExceptionWhenCellOutsideGrid(int x, int y) {
        Board board = new Board(3, 4);
        var exception = assertThrows(IllegalCoordinatesException.class,
                () -> {
                    board.seed(new Cell(x, y));
                });
        assertEquals(x, exception.getCell().x());
        assertEquals(y, exception.getCell().y());
    }

}
