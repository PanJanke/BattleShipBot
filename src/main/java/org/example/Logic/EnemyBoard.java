package org.example.Logic;

public class EnemyBoard {
    private static final int rows = 10;
    private static final int cols = 10;
    private Cell[][] board;

    private int hitCounter;

    public EnemyBoard() {
        this.hitCounter=0;
        this.board = new Cell[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = new Cell(true, 0);
            }
        }
    }



    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }
}
