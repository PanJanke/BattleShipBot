package pl.jano.Logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class EnemyBoardTest {

    private EnemyBoard enemyBoard;
    private List<Coordinates> emptyCells;

    @BeforeEach
    void setUp() {
        enemyBoard = new EnemyBoard();
        emptyCells = Arrays.asList(
                new Coordinates(0, 0),
                new Coordinates(1, 1),
                new Coordinates(2, 2),
                new Coordinates(3, 3),
                new Coordinates(3, 4),
                new Coordinates(3, 5),
                new Coordinates(3, 6)
        );
    }


    @Test
    void updateBoard() {
        EnemyBoard enemyBoard = new EnemyBoard();

        Coordinates coord = new Coordinates(4, 4);

        enemyBoard.setEmptyCells(List.of(coord));

        enemyBoard.setEmptyCells(emptyCells);

        assertTrue(enemyBoard.getBoard()[0][0].isEmpty());
        assertTrue(enemyBoard.getBoard()[1][1].isEmpty());


        assertFalse(enemyBoard.getBoard()[4][4].isEmpty());
        assertFalse(enemyBoard.getBoard()[0][1].isEmpty());
        assertFalse(enemyBoard.getBoard()[1][0].isEmpty());
    }

    @Test
    void printOutBoard() {

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        enemyBoard.setEmptyCells(emptyCells);
        enemyBoard.printOutBoard();

        String expectedOutput =
                "___________________\n" +
                        "0 X X X X X X X X X \n" +
                        "X 0 X X X X X X X X \n" +
                        "X X 0 X X X X X X X \n" +
                        "X X X 0 0 0 0 X X X \n" +
                        "X X X X X X X X X X \n" +
                        "X X X X X X X X X X \n" +
                        "X X X X X X X X X X \n" +
                        "X X X X X X X X X X \n" +
                        "X X X X X X X X X X \n" +
                        "X X X X X X X X X X";

        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
    }

    @Test
    void EmptyCellCounter() {

        enemyBoard.setNonEmptyCells(emptyCells);
        Coordinates north = new Coordinates(-1, 0);
        Coordinates south = new Coordinates(1, 0);
        Coordinates east = new Coordinates(0, 1);
        Coordinates west = new Coordinates(0, -1);

        Coordinates start = new Coordinates(1, 6);
        int northCount = enemyBoard.countEmptyCellsInDirection(start, north);

        start = new Coordinates(1, 6);
        int southCount = enemyBoard.countEmptyCellsInDirection(start, south);

        start = new Coordinates(1, 6);
        int eastCount = enemyBoard.countEmptyCellsInDirection(start, east);

        start = new Coordinates(1, 6);
        int westCount = enemyBoard.countEmptyCellsInDirection(start, west);

        assertEquals(1, northCount);
        assertEquals(1, southCount);
        assertEquals(3, eastCount);
        assertEquals(4, westCount);

    }

    @Test
    void correctCoordinates() {
        Coordinates coordA = new Coordinates(0, 0);
        Coordinates coordB = new Coordinates(9, 9);
        Coordinates coordC = new Coordinates(5, 8);

        assertTrue(enemyBoard.areCoordsCorrect(coordA));
        assertTrue(enemyBoard.areCoordsCorrect(coordB));
        assertTrue(enemyBoard.areCoordsCorrect(coordC));
    }

    @Test
    void incorrectCoordinates() {
        Coordinates coordA = new Coordinates(-1, 0);
        Coordinates coordD = new Coordinates(0, -1);
        Coordinates coordB = new Coordinates(9, 10);
        Coordinates coordC = new Coordinates(10, 9);
        Coordinates coordE = new Coordinates(21, 32);

        assertFalse(enemyBoard.areCoordsCorrect(coordA));
        assertFalse(enemyBoard.areCoordsCorrect(coordB));
        assertFalse(enemyBoard.areCoordsCorrect(coordC));
        assertFalse(enemyBoard.areCoordsCorrect(coordD));
        assertFalse(enemyBoard.areCoordsCorrect(coordE));
    }
}