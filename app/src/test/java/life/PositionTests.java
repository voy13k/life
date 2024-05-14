package life;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class PositionTests {

    @Test
    void toString_shouldTranslateToOneBasedCoordinates() {
        assertThat(new Position(2, 4), hasToString("[2,4]"));
    }

    @Test
    void parse_shouldTranslateToZeroBasedIndices() {
        Set<Position> actual = Position.parse("[[3, 5]]");
        assertThat(actual, contains(new Position(3, 5)));
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
        assertThat(exception.getMessage(), containsString("" + seed /* this hack also handles testing of null seed */));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[[2,4],[3,1],[2,5]]",
            " [ [ 2, 4 ] , [ 3 , 1 ] , [ 2 , 5 ] ] "
    })
    void parseShouldIgnoreSpaces(String seed) {
        Set<?> positions = Position.parse(seed);
        assertThat(positions, containsInAnyOrder(
                new Position(2, 4),
                new Position(3, 1),
                new Position(2, 5)));
    }

}
