package game;


import java.util.Map;

public class MainPosition implements Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.N, ' '
    );
    private final Cell[][] cells;
    private final int n;
    private final int m;
    public MainPosition(int n, int m) {
        this.n = n;
        this.m = m;
        this.cells = new Cell[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cells[i][j] = Cell.E;
            }
        }
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < n
                && 0 <= move.getColumn() && move.getColumn() < m
                && cells[move.getRow()][move.getColumn()] == Cell.E;
    }

    @Override
    public int getN() {
        return n;
    }

    @Override
    public int getM() {
        return m;
    }
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ");
        for (int c = 0; c < m; c++) {
            sb.append(c);
        }
        for (int r = 0; r < n; r++) {
            sb.append("\n");
            sb.append(r);
            for (int c = 0; c < m; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
    public void move(int x, int y, Cell cell) {
        cells[x][y] = cell;
    }
}
