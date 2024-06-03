package life;

public record Position(int row, int col) {

    @Override
    public String toString() {
        return String.format("[%s, %s]", row, col);
    }

}
