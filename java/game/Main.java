package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static private Scanner sc = new Scanner(System.in);
    public static int enter() {
        while (true) {
            try {
                int x = sc.nextInt();
                return x;
            } catch (Exception exc) {
                sc.next();
                System.out.println("Integer type is required");
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("Enter parameters ifSquare(0 or 1), n, m, and k");
        int ifSquare = enter();
        int n = enter();
        int m = enter();
        int k = enter();
        System.out.println("Do you want the usual game or tournament?(enter 1 or 2)");
        int cond = enter();
        if (cond == 1) {
            System.out.println("Enter names of players");
            String name1 = sc.next();
            String name2 = sc.next();
            final Game game = new Game(true, ifSquare, n, m, k, new HumanPlayer(name1), new HumanPlayer(name2));
            int result = game.play();
            System.out.println("Game result: " + result);
        } else {
            System.out.println("Enter number of participants");
            int p = enter();
            List<Player> players = new ArrayList<>();
            for (int i = 0; i < p; i++) {
                players.add(new RandomPlayer());
            }
            Tournament tour = new Tournament(players, n, m, k, ifSquare);
            tour.play();
        }
    }
}