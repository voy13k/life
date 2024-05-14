package life;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Not an exhaustive set of test cases, but a decent representation
 * to give us a reasonable confidence.
 */
public class BoardSingleTickTests {

        record Case(String seed, String boardBefore, String expextedBoardAfter) {
        }

        @ParameterizedTest
        @MethodSource({
                        // "noNeighbours",
                        "oneNeighbour",
                        "twoNeighbours",
                        "threeNeighbours",
                        "fourNeighbours",
                        "fiveNeighbours",
                        "sixNeighbours",
                        "sevenNeighbours",
                        "eightNeighbours",
        })
        void tick_shouldChangeTheGridAsExpected(Case c) {
                Board board = new Board();
                board.seed(c.seed);
                assertThat(GridTestHelper.toGrid(5, 6, board.toString()), is(c.boardBefore));

                board.tick();

                assertThat(GridTestHelper.toGrid(5, 6, board.toString()), is(c.expextedBoardAfter));
        }

        static Stream<Case> eightNeighbours() {
                return Stream.of(
                                new Case("[[1,1],[1,2],[1,3],[2,1],[2,2],[2,3],[3,1],[3,2],[3,3]]", """
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
                                                """));
        }

        static Stream<Case> sevenNeighbours() {
                return Stream.of(
                                new Case("[[1,1],[1,2],[1,3],[2,2],[2,3],[3,1],[3,2],[3,3]]", """
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
                                                """));
        }

        static Stream<Case> sixNeighbours() {
                return Stream.of(
                                new Case("[[1,1],[1,2],[1,3],[2,2],[2,3],[3,2],[3,3]]", """
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
                                                """));
        }

        static Stream<Case> fiveNeighbours() {
                return Stream.of(
                                new Case("[[1,1],[1,2],[1,3],[2,2],[2,3],[3,3]]", """
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
                                                """));
        }

        static Stream<Case> fourNeighbours() {
                return Stream.of(
                                new Case("[[1,1],[1,2],[1,3],[2,2],[2,3]]", """
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
                                                """));
        }

        static Stream<Case> threeNeighbours() {
                return Stream.of(
                                new Case("[[1,1],[1,2],[1,3],[2,2]]", """
                                                [      ]
                                                [ XXX  ]
                                                [  X   ]
                                                [      ]
                                                [      ]
                                                """, """
                                                [  X   ]
                                                [ XXX  ]
                                                [ XXX  ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[1,1],[1,2],[2,2],[2,3]]", """
                                                [      ]
                                                [ XX   ]
                                                [  XX  ]
                                                [      ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [ XXX  ]
                                                [ XXX  ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[1,1],[1,2],[2,2],[3,3]]", """
                                                [      ]
                                                [ XX   ]
                                                [  X   ]
                                                [   X  ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [ XX   ]
                                                [ XXX  ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[1,1],[1,2],[2,2],[3,2]]", """
                                                [      ]
                                                [ XX   ]
                                                [  X   ]
                                                [  X   ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [ XX   ]
                                                [  XX  ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[1,1],[1,2],[2,2],[3,1]]", """
                                                [      ]
                                                [ XX   ]
                                                [  X   ]
                                                [ X    ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [ XX   ]
                                                [  X   ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[1,1],[1,2],[2,1],[2,2]]", """
                                                [      ]
                                                [ XX   ]
                                                [ XX   ]
                                                [      ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [ XX   ]
                                                [ XX   ]
                                                [      ]
                                                [      ]
                                                """));
        }

        static Stream<Case> twoNeighbours() {
                return Stream.of(
                                new Case("[[1,1],[1,2],[2,2]]", """
                                                [      ]
                                                [ XX   ]
                                                [  X   ]
                                                [      ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [ XX   ]
                                                [ XX   ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[1,1],[1,3],[2,2]]", """
                                                [      ]
                                                [ X X  ]
                                                [  X   ]
                                                [      ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [  X   ]
                                                [  X   ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[1,1],[2,2],[2,3]]", """
                                                [      ]
                                                [ X    ]
                                                [  XX  ]
                                                [      ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [  X   ]
                                                [  X   ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[1,1],[2,2],[3,3]]", """
                                                [      ]
                                                [ X    ]
                                                [  X   ]
                                                [   X  ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [      ]
                                                [  X   ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[1,1],[2,2],[3,2]]", """
                                                [      ]
                                                [ X    ]
                                                [  X   ]
                                                [  X   ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [      ]
                                                [ XX   ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[1,1],[2,2],[3,1]]", """
                                                [      ]
                                                [ X    ]
                                                [  X   ]
                                                [ X    ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [      ]
                                                [ XX   ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[1,1],[2,1],[2,2]]", """
                                                [      ]
                                                [ X    ]
                                                [ XX   ]
                                                [      ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [ XX   ]
                                                [ XX   ]
                                                [      ]
                                                [      ]
                                                """));
        }

        static Stream<Case> oneNeighbour() {
                return Stream.of(
                                new Case("[[1,1],[2,2]]", """
                                                [      ]
                                                [ X    ]
                                                [  X   ]
                                                [      ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[1,2],[2,2]]", """
                                                [      ]
                                                [  X   ]
                                                [  X   ]
                                                [      ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[1,3],[2,2]]", """
                                                [      ]
                                                [   X  ]
                                                [  X   ]
                                                [      ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[2,2], [2,3]]", """
                                                [      ]
                                                [      ]
                                                [  XX  ]
                                                [      ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[2,2], [3,3]]", """
                                                [      ]
                                                [      ]
                                                [  X   ]
                                                [   X  ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[2,2], [3,2]]", """
                                                [      ]
                                                [      ]
                                                [  X   ]
                                                [  X   ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[2,2], [3,1]]", """
                                                [      ]
                                                [      ]
                                                [  X   ]
                                                [ X    ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                """),
                                new Case("[[2,1], [2,2]]", """
                                                [      ]
                                                [      ]
                                                [ XX   ]
                                                [      ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                """));
        }

        static Stream<Case> noNeighbours() {
                return Stream.of(
                                new Case("[[2,2]]", """
                                                [      ]
                                                [      ]
                                                [  X   ]
                                                [      ]
                                                [      ]
                                                """, """
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                [      ]
                                                """));
        }

}
