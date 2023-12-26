package pl.jano.Logic;

import java.util.Arrays;
import java.util.List;

public class EnemyBoard {

    private static final Coordinates north = new Coordinates(-1, 0);
    private static final Coordinates south = new Coordinates(1, 0);
    private static final Coordinates east = new Coordinates(0, 1);
    private static final Coordinates west = new Coordinates(0, -1);
    private static final int rows = 10;
    private static final int cols = 10;

    private final Cell[][] board;

    public EnemyBoard() {
        this.board = new Cell[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = new Cell(true);
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
        return (cols > coord.getyCoord() && coord.getyCoord() >= 0)
                && (rows > coord.getxCoord() && coord.getxCoord() >= 0);
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

    public void printEmptyCells() {
        System.out.println("___________________");
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (board[i][j].isEmpty()) System.out.print("0 ");
                else System.out.print("X ");
            }
            System.out.print("\n");
        }
    }

    public Coordinates findCellWithHighestProbability() {
        int maxProbability = Integer.MIN_VALUE;
        Coordinates target = new Coordinates(1, 1);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell currentCell = board[j][i];
                if (currentCell.isEmpty() && currentCell.getProbabilityOfHit() > maxProbability) {
                    maxProbability = currentCell.getProbabilityOfHit();
                    target.setyCoord(j);
                    target.setxCoord(i);
                }
            }
        }
        return target;
    }

    public void printProbability() {
        System.out.println();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (board[i][j].isEmpty()) System.out.printf("%-3d ", board[i][j].getProbabilityOfHit());
                else System.out.print("XX  ");
            }
            System.out.print("\n");
        }
    }


    private void clearProbability() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.board[i][j].setProbabilityOfHit(0);
            }
        }
    }

    public Coordinates getLongestEmptyDirection(Coordinates start) {
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

    public Coordinates getLongestEmptyDirectionInOneAxis(Coordinates[] candidates) {
        int maxEmptyCount = Integer.MIN_VALUE;
        boolean horizontal = candidates[0].getxCoord() == candidates[1].getxCoord();
        Coordinates longestEmptyDirection = null;
        for (Coordinates start : candidates) {
            for (Coordinates direction : !horizontal ? Arrays.asList(east, west) : Arrays.asList(north, south)) {
                int emptyCount = countEmptyCellsInDirection(start, direction);
                if (emptyCount > maxEmptyCount) {
                    maxEmptyCount = emptyCount;
                    longestEmptyDirection = direction;
                }
            }
        }

        return longestEmptyDirection;
    }


    private int countEmptyCellsInDirection(Coordinates start, Coordinates direction) {
        int emptyCount = 0;
        Coordinates iterator = new Coordinates(start.getyCoord(), start.getxCoord());
        do {
            iterator.addCoordinates(direction);
            emptyCount++;
        } while (areCoordsCorrect(iterator) && isEmpty(iterator));

        return emptyCount - 1;
    }

    public void incProbability(Coordinates position) {
        this.board[position.getyCoord()][position.getxCoord()].increaseProbability();
    }

    public void increaseProbabilityInDirection(Coordinates start, Coordinates direction, Integer numberOfCells) {
        Coordinates iterator = new Coordinates(start.getyCoord(), start.getxCoord());
        for (int i = 0; i < numberOfCells; i++) {
            incProbability(iterator);
            iterator.addCoordinates(direction);
        }
    }


    public void setProbability(List<Integer> fleet) {
        clearProbability();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (board[j][i].isEmpty()) {

                    Coordinates myPosition = new Coordinates(j, i);
                    int southCounter = countEmptyCellsInDirection(myPosition, south) + 1;
                    int eastCounter = countEmptyCellsInDirection(myPosition, east) + 1;
                    for (Integer lengthOfShip : fleet) {
                        if (southCounter >= lengthOfShip) {
                            increaseProbabilityInDirection(myPosition, south, lengthOfShip);
                        }
                        if (eastCounter >= lengthOfShip) {
                            increaseProbabilityInDirection(myPosition, east, lengthOfShip);
                        }
                    }
                }
            }
        }
    }
}
