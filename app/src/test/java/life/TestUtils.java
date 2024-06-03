package life;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class TestUtils {

    static Collection<Position> fromMatrix(String matrix) {
        var lines = matrix.lines()
                .map(l -> l.replaceAll("[\\[\\]]", ""))
                .toList();
        var cells = new ArrayList<Position>();
        for (int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);
            for (int col = 0; col < line.length(); col++) {
                if (line.charAt(col) != ' ') {
                    cells.add(new Position(row, col));
                }
            }
        }
        return cells;
    }

    @Test
    void fromMatrix_BlankToEmptyList() {
        assertThat(fromMatrix(""), Matchers.hasSize(0));
    }

    @Test
    void fromMatrix_SingleToSingle() {
        assertThat(fromMatrix("""
                [X]
                """), containsInAnyOrder(new Position(0, 0)));
    }

    @Test
    void fromMatrix_secondRow() {
        assertThat(fromMatrix("""
                [   ]
                [X  ]
                """), containsInAnyOrder(new Position(1, 0)));
    }

    @Test
    void fromMatrix_secondCol() {
        assertThat(fromMatrix("""
                [ X]
                """), containsInAnyOrder(new Position(0, 1)));
    }

    @Test
    void fromMatrix_manyItems() {
        assertThat(fromMatrix("""
                [  X ]
                [ X X]
                [X   ]
                """), containsInAnyOrder(
                new Position(0, 2),
                new Position(1, 1),
                new Position(1, 3),
                new Position(2, 0)));
    }

    @Test
    void fromMatrix_anyChar() {
        assertThat(fromMatrix("""
                [  A ]
                [ B C]
                """), containsInAnyOrder(
                new Position(0, 2),
                new Position(1, 1),
                new Position(1, 3)));
    }

}
