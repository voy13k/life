package life;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;

import static life.TestUtils.fromMatrix;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

class LifeTickTests {

    private static String[][] one() {
        return new String[][]{{"""
                [X  ]
                [   ]
                [   ]
                """, """
                [   ]
                [   ]
                [   ]
                """}, {"""
                [   ]
                [ X ]
                [   ]
                """, """
                [   ]
                [   ]
                [   ]
                """}, {"""
                [   ]
                [   ]
                [  X]
                """, """
                [   ]
                [   ]
                [   ]
                """},};
    }

    private static String[][] two() {
        return new String[][]{{"""
                [X  ]
                [ X ]
                [   ]
                """, """
                [   ]
                [   ]
                [   ]
                """}, {"""
                [ X ]
                [ X ]
                [   ]
                """, """
                [   ]
                [   ]
                [   ]
                """}, {"""
                [  X]
                [ X ]
                [   ]
                """, """
                [   ]
                [   ]
                [   ]
                """}, {"""
                [   ]
                [ XX]
                [   ]
                """, """
                [   ]
                [   ]
                [   ]
                """}, {"""
                [   ]
                [ X ]
                [  X]
                """, """
                [   ]
                [   ]
                [   ]
                """}, {"""
                [   ]
                [ X ]
                [ X ]
                """, """
                [   ]
                [   ]
                [   ]
                """}, {"""
                [   ]
                [ X ]
                [X  ]
                """, """
                [   ]
                [   ]
                [   ]
                """}, {"""
                [   ]
                [XX ]
                [   ]
                """, """
                [   ]
                [   ]
                [   ]
                """},};
    }

    private static String[][] three() {
        return new String[][]{{"""
                [X   ]
                [ XX ]
                [    ]
                """, """
                [ X  ]
                [ X  ]
                [    ]
                """}, {"""
                [ X  ]
                [ XX ]
                [    ]
                """, """
                [ XX ]
                [ XX ]
                [    ]
                """}, {"""
                [  X ]
                [ XX ]
                [    ]
                """, """
                [ XX ]
                [ XX ]
                [    ]
                """}, {"""
                [   X]
                [ XX ]
                [    ]
                """, """
                [  X ]
                [  X ]
                [    ]
                """}, {"""
                [    ]
                [ XXX]
                [    ]
                """, """
                [  X ]
                [  X ]
                [  X ]
                """}, {"""
                [    ]
                [ XX ]
                [   X]
                """, """
                [    ]
                [  X ]
                [  X ]
                """}, {"""
                [    ]
                [ XX ]
                [  X ]
                """, """
                [    ]
                [ XX ]
                [ XX ]
                """}, {"""
                [    ]
                [ XX ]
                [ X  ]
                """, """
                [    ]
                [ XX ]
                [ XX ]
                """}, {"""
                [    ]
                [ XX ]
                [X   ]
                """, """
                [    ]
                [ X  ]
                [ X  ]
                """}, {"""
                [    ]
                [XXX ]
                [    ]
                """, """
                [ X  ]
                [ X  ]
                [ X  ]
                """}, {"""
                [XX  ]
                [    ]
                [  X ]
                """, """
                [    ]
                [ X  ]
                [    ]
                """}, {"""
                [XX  ]
                [    ]
                [ X  ]
                """, """
                [    ]
                [XX  ]
                [    ]
                """},};
    }

    private static String[][] four() {
        return new String[][]{{"""
                [      ]
                [ XXX  ]
                [  X   ]
                [      ]
                """, """
                [  X   ]
                [ XXX  ]
                [ XXX  ]
                [      ]
                """}, {"""
                [      ]
                [ XX   ]
                [  XX  ]
                [      ]
                """, """
                [      ]
                [ XXX  ]
                [ XXX  ]
                [      ]
                """}, {"""
                [      ]
                [ XX   ]
                [  X   ]
                [   X  ]
                """, """
                [      ]
                [ XX   ]
                [ XXX  ]
                [      ]
                """}, {"""
                [      ]
                [ XX   ]
                [  X   ]
                [  X   ]
                """, """
                [      ]
                [ XX   ]
                [  XX  ]
                [      ]
                """}, {"""
                [      ]
                [ XX   ]
                [  X   ]
                [ X    ]
                """, """
                [      ]
                [ XX   ]
                [  X   ]
                [      ]
                """}, {"""
                [      ]
                [ XX   ]
                [ XX   ]
                [      ]
                """, """
                [      ]
                [ XX   ]
                [ XX   ]
                [      ]
                """},};
    }

    private static String[][] five() {
        return new String[][]{{"""
                [      ]
                [ XXX  ]
                [  XX  ]
                [      ]
                [      ]
                """, """
                [  X   ]
                [ X X  ]
                [ X X  ]
                [      ]
                [      ]
                """},};
    }

    private static String[][] six() {
        return new String[][]{{"""
                [      ]
                [ XXX  ]
                [  XX  ]
                [   X  ]
                [      ]
                """, """
                [  X   ]
                [ X X  ]
                [ X  X ]
                [  XX  ]
                [      ]
                """},};
    }

    private static String[][] seven() {
        return new String[][]{{"""
                [      ]
                [ XXX  ]
                [  XX  ]
                [  XX  ]
                [      ]
                """, """
                [  X   ]
                [ X X  ]
                [    X ]
                [  XX  ]
                [      ]
                """},};
    }

    private static String[][] eight() {
        return new String[][]{{"""
                [      ]
                [ XXX  ]
                [  XX  ]
                [ XXX  ]
                [      ]
                """, """
                [  X   ]
                [ X X  ]
                [    X ]
                [ X X  ]
                [  X   ]
                """},};
    }

    private static String[][] nine() {
        return new String[][]{{"""
                [      ]
                [ XXX  ]
                [ XXX  ]
                [ XXX  ]
                [      ]
                """, """
                [  X   ]
                [ X X  ]
                [X   X ]
                [ X X  ]
                [  X   ]
                """},};
    }

    @Test
    void tick_blankToBlank() {
        assertThat(Life.tick(Set.of()), hasSize(0));
    }

    @ParameterizedTest
    @MethodSource({
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
    })
    void tick_neighbours(String matrixBefore, String expectedMatrixAfter) {
        var beforeCells = fromMatrix(matrixBefore);
        var expectedCells = fromMatrix(expectedMatrixAfter);
        assertThat(Life.tick(beforeCells), containsInAnyOrder(expectedCells.toArray()));
    }

}
