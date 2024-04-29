package game;

import java.io.PrintStream;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;
    public final String name;

    public HumanPlayer(final PrintStream out, final Scanner in, String name) {
        this.name = name;
        this.out = out;
        this.in = in;
    }

    public HumanPlayer(String name) {
        this(System.out, new Scanner(System.in), name);
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            int x = enter();
            int y = enter();
            final Move move = new Move(x, y, cell);
            if (position.isValid(move)) {
                return move;
            }
            final int row = move.getRow();
            final int column = move.getColumn();
            out.println("Move " + move + " is invalid");
        }
    }
    public String toString() {
        return name;
    }
    public int enter() {
        while (true) {
            try {
                int x = in.nextInt();
                return x;
            } catch (Exception exc) {
                in.next();
                System.out.println("Integer type is required");
            }
        }
    }

}
