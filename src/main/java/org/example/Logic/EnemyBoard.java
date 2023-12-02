package org.example.Logic;

import java.util.List;

public class EnemyBoard {
    private static final int rows = 10;
    private static final int cols = 10;
    private Cell[][] board;

    public EnemyBoard() {
        this.board = new Cell[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = new Cell(true, 0);
            }
        }
    }

    public void updateBoard(List<Coordinates> emptyCells) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j].setEmpty(false);
            }
        }
        for (Coordinates coord : emptyCells) {
            this.board[coord.getxCoord()][coord.getyCoord()].setEmpty(true);
        }
    }


}
