package life;

public class IllegalCoordinatesException extends RuntimeException {

    private Cell cell;

    public IllegalCoordinatesException(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }

}
