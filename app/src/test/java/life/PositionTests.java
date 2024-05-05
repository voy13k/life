package life;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class PositionTests {

    @Test
    void toStringShouldTranslateToOneBasedCoordinates() {
        assertEquals("[3,5]", new Position(2, 4).toString());
    }

    @Test
    void parseShouldTranslateToZeroBasedIndices() {
        Position[] positions = Position.parse("[[3, 5]]");
        assertEquals(new Position(2, 4), positions[0]);
    }

    @ParameterizedTest
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
            "[[1, 2]  [3, 4]]",
            "[[1, 2], [1, 2, 3]]"
    })
    @NullSource
    void parseShouldThrowExceptionForInvalidInput(String seed) {
        var exception = assertThrows(IlegalSeedException.class, () -> {
            Position.parse(seed);
        });
        assertTrue(exception.getMessage().contains("" + seed));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[[2,4],[3,1],[2,5]]",
            " [ [ 2, 4 ] , [ 3 , 1 ] , [ 2 , 5 ] ] "
    })
    void parseShouldIgnoreSpaces(String seed) {
        Position[] positions = Position.parse(seed);
        assertEquals(3, positions.length);
        assertEquals("[2,4]", positions[0].toString());
        assertEquals("[3,1]", positions[1].toString());
        assertEquals("[2,5]", positions[2].toString());
    }

}
