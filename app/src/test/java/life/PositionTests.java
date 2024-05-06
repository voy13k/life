package life;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class PositionTests {

    @Test
    void toString_shouldTranslateToOneBasedCoordinates() {
        assertThat(new Position(2, 4), hasToString("[3,5]"));
    }

    @Test
    void parse_shouldTranslateToZeroBasedIndices() {
        assertThat(Position.parse("[[3, 5]]").get(0), is(new Position(2, 4)));
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
        List<?> positions = Position.parse(seed);
        assertThat(positions, hasSize(3));
        assertThat(positions.get(0), hasToString("[2,4]"));
        assertThat(positions.get(1), hasToString("[3,1]"));
        assertThat(positions.get(2), hasToString("[2,5]"));
    }

}
