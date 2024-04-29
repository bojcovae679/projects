package game;

public class CircleBoard extends MainBoard{
    public CircleBoard(int r, int k) {
        super(2 * r + 1, 2 * r + 1, k);
        for (int i = 0; i < 2 * r + 1; i++) {
            for (int j = 0; j < 2 * r + 1; j++) {
                if (Math.pow(Math.abs(i - r), 2) + Math.pow(Math.abs(j - r), 2) > Math.pow(r,2)) {
                    getCells()[i][j] = Cell.N;
                    getPosition().move(i, j, Cell.N);
                }
            }
        }
    }
}
