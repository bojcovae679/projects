package game;

import java.util.*;

public class Tournament {
    Map<Player,Integer> results = new LinkedHashMap<>();
    List<Player> players;
    int n;
    int m;
    int k;
    int ifSquare;
    public Tournament(List<Player> players, int n, int m ,int k, int ifSquare) {
        this.n = n;
        this.m = m;
        this.k = k;
        this.ifSquare = ifSquare;
        this.players = players;
    }
    public Player play2(Player player1, Player player2, int place) {
        int result;
        do {
            final Game game = new Game(true, ifSquare, n, m, k, player1, player2);
            result = game.play();
        } while ( result == 0 || result == -1);
        if (result == 1) {
            results.put(player2, place);
            System.out.println(player1.toString() + " won");
            return player1;
        }
        results.put(player1, place);
        System.out.println(player2.toString() + " won");
        return player2;
    }

    public void play(){
        int n = log(players.size())/2;
        int place = logNumber(players.size()) + 1;
        Collections.shuffle(players);
        List<Player> winners = new ArrayList<>();;
        int k =  players.size() - n;
        for (int i = 0; i < k; i++) {
            System.out.println("Game of " + players.get(i).toString() + " and " + players.get(k + i).toString());
            winners.add(play2(players.get(i), players.get(k + i), place));
        }
        for (int i = 2 * k; i < players.size(); i++) {
            winners.add(players.get(i));
        }
        place = place - 1;
        while (players.size() != 1) {
            players = winners;
            winners = new ArrayList<>();
            for (int i = 0; i < log(players.size())/2; i++) {
                System.out.println("Game of " + players.get(i).toString() + " and " + players.get(players.size() - i - 1).toString());
                winners.add(play2(players.get(i), players.get(players.size() - i - 1), place));
            }
            place = place - 1;
        }
        results.put(players.get(0), 1);
        for(Player i : results.keySet()){
            System.out.println(i + " " + results.get(i));
        }
    }
    public int log(int n) {
        int i = 1;
        while (i < n) {
            i = i * 2;
        }
        return i;
    }
    public int logNumber(int n) {
        int i = 1;
        int ans = 0;
        while (i < n) {
            i = i * 2;
            ans = ans + 1;
        }
        return ans;
    }

}
