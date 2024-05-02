package life;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LifeTest {

    @Test
    void shouldCreateDeadGridOfRightSize() {
        Life life = new Life(3, 4);
        assertEquals("""
                [false, false, false]
                [false, false, false]
                [false, false, false]
                [false, false, false]
                """, life.toString());
    }

    @Test
    void shouldRetrunNoLifeCellsIfNotSeeded() {
        Life life = new Life(3, 4);
        assertEquals(0, life.getLiveCells().size());
    }

    @Test
    void shouldReturnSeededCells() {
        Life life = new Life(3, 4);
        life.seed(
                new Cell(3, 2),
                new Cell(1, 3));
        assertEquals(
                Arrays.asList(
                        new Cell(3, 2),
                        new Cell(1, 3)),
                life.getLiveCells());
    }

    @Test
    void shouldOrderByRowFirst() {
        Life life = new Life(3, 4);
        life.seed(
                new Cell(1, 3),
                new Cell(3, 2));
        assertEquals(
                Arrays.asList(
                        new Cell(3, 2),
                        new Cell(1, 3)),
                life.getLiveCells());
    }

    @ParameterizedTest()
    @CsvSource({
            "0, 1",
            "4, 1",
            "1, 0",
            "1, 5"
    })
    void shouldThrowExceptionWhenCellOutsideGrid(int x, int y) {
        Life life = new Life(3, 4);
        var exception = assertThrows(IllegalCoordinatesException.class,
                () -> {
                    life.seed(new Cell(x, y));
                });
        assertEquals(x, exception.getCell().x());
        assertEquals(y, exception.getCell().y());
    }

}
