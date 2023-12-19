package pl.jano.Logic;

import java.util.Arrays;
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

    public Cell[][] getBoard() {
        return board;
    }

    public boolean isEmpty(Coordinates coord) {
        return board[coord.getyCoord()][coord.getxCoord()].isEmpty();
    }

    public boolean areCoordsCorrect(Coordinates coord) {
        return (cols > coord.getyCoord() && coord.getyCoord() >= 0) &&
                (rows > coord.getxCoord() && coord.getxCoord() >= 0);
    }

    public void setEmptyCells(List<Coordinates> emptyCells) {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                board[i][j].setEmpty(false);
            }
        }
        for (Coordinates coord : emptyCells) {
            this.board[coord.getyCoord()][coord.getxCoord()].setEmpty(true);
        }
    }


    public void setNonEmptyCells(List<Coordinates> emptyCells) {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                board[i][j].setEmpty(true);
            }
        }
        for (Coordinates coord : emptyCells) {
            this.board[coord.getyCoord()][coord.getxCoord()].setEmpty(false);
        }
    }

    public void printOutBoard() {
        System.out.println("___________________");
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (board[i][j].isEmpty())
                    System.out.print("0 ");
                else
                    System.out.print("X ");
            }
            System.out.print("\n");
        }

    }

    private Coordinates getLongestEmptyDirection(Coordinates start) {
        Coordinates north = new Coordinates(-1, 0);
        Coordinates south = new Coordinates(1, 0);
        Coordinates east = new Coordinates(0, 1);
        Coordinates west = new Coordinates(0, -1);

        int maxEmptyCount = 0;
        Coordinates longestEmptyDirection = null;

        for (Coordinates direction : Arrays.asList(north, south, east, west)) {
            int emptyCount = countEmptyCellsInDirection(start, direction);
            if (emptyCount > maxEmptyCount) {
                maxEmptyCount = emptyCount;
                longestEmptyDirection = direction;
            }
        }

        return longestEmptyDirection;
    }

    public int countEmptyCellsInDirection(Coordinates start, Coordinates direction) {
        int emptyCount = 0;

        while (areCoordsCorrect(start) && isEmpty(start)) {
            start.addCoordinates(direction);
            emptyCount++;
        }

        return emptyCount - 1;
    }
}