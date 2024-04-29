package game;

public class Game {
    private final boolean log;
    public final int n;
    public final int m;
    public final int k;
    private final int ifSquare;
    private final Player player1;
    private final Player player2;
    private Board board;
    public Game(boolean log, int ifSquare, int n, int m, int k, Player player1, Player player2) {
        this.log = log;
        if (ifSquare == 1) {
            board = new SquareBoard(n, m, k);
        }
        if (ifSquare == 0) {
           board = new CircleBoard(n , k);
        }
        this.n = n;
        this.m = m;
        this.k = k;
        this.ifSquare = ifSquare;
        this.player1 = player1;
        this.player2 = player2;
    }
    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
    private int move(final Player player, final int number) {
        final Move move;
        try {
            move = player.move(board.getPosition(), board.getCell());
        } catch (Exception exc) {
            return 3 - number;
        }
        final Result result = board.makeMove(move);
        log("Player " + number + " move: " + move);
        log("Position:\n" + board);
        if (result == Result.WIN) {
            log("Player " + number + " won");
            return number;
        } else if (result == Result.LOSE) {
            log("Player " + (3 - number) + " won");
            return 3 - number;
        } else if (result == Result.DRAW) {
            log("Draw");
            return 0;
        } else {
            return -1;
        }
    }

    public int play() {
        if ((k > n && k > m && ifSquare == 1) || (k > 2 * n + 1)) {
            return 0;
        }
        while (true) {
            final int result1 = move(player1, 1);
            if (result1 != -1) {
                return result1;
            }
            final int result2 = move(player2, 2);
            if (result2 != -1) {
                return result2;
            }
        }
    }
}
