package life;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Life {

    public static void main(String[] args) {
        Collection<Position> generation = parse(args);
        for (int i = 1; i <= 100; i++) {
            generation = tick(generation);
            System.out.printf("%d: %s\n", i, generation);
        }
    }

    private static Collection<Position> parse(String[] args) {
        Collection<Position> generation = new ArrayList<>();
        JSONArray positions = new JSONArray(String.join(" ", args));
        for (int i1 = 0; i1 < positions.length(); i1++) {
            JSONArray coords = positions.getJSONArray(i1);
            generation.add(new Position(coords.getInt(0), coords.getInt(1)));
        }
        return generation;
    }

    public static List<Position> tick(Collection<Position> oldGeneration) {
        return oldGeneration.stream()
                .flatMap(Life::neigbourhood)
                .collect(Collectors.toSet()).stream()
                .filter(potential -> switch (neighbourCountOf(potential, oldGeneration)) {
                    case (2) -> oldGeneration.contains(potential);
                    case (3) -> true;
                    default -> false;
                })
                .toList();
    }

    private static int neighbourCountOf(Position cell, Collection<? extends Position> generation) {
        return (int) neigbourhood(cell)
                .filter(c -> !c.equals(cell))
                .filter(generation::contains)
                .count();
    }

    private static Stream<Position> neigbourhood(Position cell) {
        return Stream.of(
                new Position(cell.row() - 1, cell.col() - 1),
                new Position(cell.row() - 1, cell.col()),
                new Position(cell.row() - 1, cell.col() + 1),
                new Position(cell.row(), cell.col() - 1),
                cell,
                new Position(cell.row(), cell.col() + 1),
                new Position(cell.row() + 1, cell.col() - 1),
                new Position(cell.row() + 1, cell.col()),
                new Position(cell.row() + 1, cell.col() + 1)
        );
    }
}
