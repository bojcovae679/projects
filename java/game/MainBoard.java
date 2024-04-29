package game;

import java.util.Arrays;
import java.util.Map;

public abstract class MainBoard implements Board {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.N, ' '
    );
    private final Cell[][] cells;
    private Cell turn;
    private int moveX = 0;
    private int moveY = 0;
    private final int n;
    private final int m;
    private final int k;
    private int numberOfMoves = 0;
    private Position position;

    public MainBoard( int n, int m, int k) {
        this.k = k;
        this.n = n;
        this.m = m;
        if (n > -1 && m > -1 && k > -1) {
            this.cells = new Cell[n][m];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    cells[i][j] = Cell.E;
                }
            }
        } else {
            this.cells = new Cell[0][0];
            System.err.println("Invalid parameters of the desk");
            System.exit(-1);
        }
        turn = Cell.X;
        this.position = new MainPosition(n, m);
    }
    protected Cell[][] getCells() {
        return cells;
    }
    public Position getPosition() {
        return position;
    }
    public Cell getCell() {
        return turn;
    }
    public boolean suitable(Cell e, boolean wonOrDraw, Cell turn) {
        if(wonOrDraw) {
            return e == turn;
        }
        return e == turn || e == Cell.E;
    }
    public int countCross(boolean wonOrDraw, boolean ifRow, Move move, Cell turn, int diff) {
        int x = move.getRow();
        int y = move.getColumn();
        int x1;
        int mn;
        Cell e = turn;
        if (ifRow) {
            x1 = x + diff;
            mn = n;
            if (x1 < mn && x1 > -1) {
                e = cells[x1][y];
            }
        } else {
            x1 = y + diff;
            mn = m;
            if(x1 < mn && x1 > -1)  {
                e = cells[x][x1];
            }
        }
        int count = 0;
        while (count < k + 1 && x1 < mn && x1 > -1 && suitable(e, wonOrDraw, turn)) {
            count = count + 1;
            x1 = x1 + diff;
            if (x1 < mn && x1 > -1) {
                if (ifRow) {
                    e = cells[x1][y];
                } else {
                    e = cells[x][x1];
                }
            }
        }
        return count;
    }
    public boolean wonCross(boolean wonOrDraw, boolean ifRow, Move move, Cell turn) {
        int count1 = countCross(wonOrDraw, ifRow, move, turn, 1);
        int count2 = countCross(wonOrDraw, ifRow, move, turn, -1);
        return count1 + count2 >= (k-1);
    }
    public int countDiag(boolean wonOrDraw, boolean ifMain, Move move, Cell turn,int diff) {
        int x = move.getRow();
        int y = move.getColumn();
        int x1 = x + diff;
        int y1 = y + diff;
        if (ifMain) {
            y1 = y - diff;
        }
        int count = 0;
        Cell e = turn;
        if (x1 > -1 && x1 < n && y1 < m && y1 > -1) {
            e = cells[x1][y1];
        }
        while (count < k + 1 && x1 < n && x1 > -1 && y1 < m && y1 > -1  && suitable(e, wonOrDraw, turn)) {
            count = count + 1;
            x1 = x1 + diff;
            y1 = y1 + diff;
            if (ifMain) {
                y1 = y1 - 2 * diff;
            }
            if (x1 > -1 && x1 < n && y1 < m && y1 > -1 ) {
                e = cells[x1][y1];
            }
        }
        return count;
    }
    public boolean wonDiag(boolean wonOrDraw, boolean ifMain, Move move, Cell turn) {
        int count1 = countDiag(wonOrDraw,ifMain, move, turn, 1);
        int count2 = countDiag(wonOrDraw,ifMain, move, turn, -1);
        return count1 + count2 >= (k-1);
    }
    public boolean wining(Move move, Cell turn) {
        return wonCross(true, true, move, turn) || wonCross(true, false, move, turn)
                || wonDiag(true, true, move, turn) || wonDiag(true, false, move, turn);
    }
    public boolean draw(Move move, Cell turn) {
        return !wonCross(false, true, move, turn) && !wonCross(false, false, move, turn)
                && !wonDiag(false, true, move, turn) && !wonDiag(false, false, move, turn);
    }

    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < n
                && 0 <= move.getColumn() && move.getColumn() < m
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell();
    }
    public boolean ifDraw(int moveX, int moveY) {
        Move movex = new Move(moveX, moveY, Cell.E);
        return ((cells[moveX][moveY] == Cell.E && draw(movex,Cell.X) && draw(movex, Cell.O))
                || (cells[moveX][moveY] != Cell.E && draw(movex,Cell.E)));
    }
    public void getToRealCell(){
        while (cells[moveX][moveY] == Cell.N) {
            moveY = moveY + 1;
            if (moveY == m){
                moveX = moveX + 1;
                moveY = 0;
            }
            if (moveX == n) {
                break;
            }
        }
    }
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        numberOfMoves = numberOfMoves + 1;
        cells[move.getRow()][move.getColumn()] = move.getValue();
        position.move(move.getRow(), move.getColumn(), move.getValue());
        if (wining( move, turn)) {
            return Result.WIN;
        }
        getToRealCell();
        if (moveX == n) {
            return Result.DRAW;
        }
        while (ifDraw(moveX, moveY)) {
            moveY = moveY + 1;
            if (moveY == m){
                moveX = moveX + 1;
                moveY = 0;
            }
            if (moveX == n) {
                return Result.DRAW;
            }
            getToRealCell();
            if (moveX == n) {
                return Result.DRAW;
            }
        }
        if (numberOfMoves == n*m) {
            return Result.DRAW;
        }
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
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


}
