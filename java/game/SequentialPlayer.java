package game;

public class SequentialPlayer implements Player {
    int r;
    int c = -1;
    @Override
    public Move move(Position position, Cell cell) {
        c = c + 1;
        if (c == position.getM()) {
            r = r + 1;
            c = 0;
        }
        return new Move(r, c, cell);
    }
    public String toString() {
        return hashCode() + "";
    }
}
