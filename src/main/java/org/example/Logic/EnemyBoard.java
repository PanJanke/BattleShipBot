package org.example.Logic;

public class EnemyBoard {
    private static final int rows = 10;
    private static final int cols = 10;

    private Tile[][] board;

    public EnemyBoard() {
        this.board = new Tile[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = new Tile(true, 0);
            }
        }
    }



    public Tile[][] getBoard() {
        return board;
    }

    public void setBoard(Tile[][] board) {
        this.board = board;
    }
}
