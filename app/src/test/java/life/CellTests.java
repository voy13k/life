package life;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class CellTests {

    @Test
    void shouldProduceExpectedStringRepresentation() {
        assertEquals("[2, 4]", new Cell(2, 4).toString());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "",
            " ",
            "1,2",
            "blah",
            "[1,2]",
            "[[]]",
            "[[1]]",
            "[[blah, 1]]",
            "[[1, blah]]",
            "[[1, 2, 3]]",
            "[[1, 2], [1]]",
            "[[1, 2], [1, 2, 3]]",
            "[[1, 2]  [3, 4]]",
            "[[1, 2]]  [[3, 4]]"
         })
    void parseShouldThrowExceptionForInvalidInput(String seed) {
        var exception = assertThrows(InvalidSeedException.class, () -> {
            Cell.parse(seed);
        });
        assertTrue(exception.getMessage().contains("" + seed));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[[2,4],[3,1],[2,5]]",
            " [ [ 2, 4 ] , [ 3 , 1 ] , [ 2 , 5 ] ] "
    })
    void shouldIgnoreSpaces(String seed) {
        Cell[] cells = Cell.parse(seed);
        assertEquals(3, cells.length);
        assertEquals("[2, 4]", cells[0].toString());
        assertEquals("[3, 1]", cells[1].toString());
        assertEquals("[2, 5]", cells[2].toString());
    }

}
