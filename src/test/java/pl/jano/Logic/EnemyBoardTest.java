package pl.jano.Logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class EnemyBoardTest {

    private EnemyBoard enemyBoard;
    private List<Coordinates> emptyCells;
    private final Coordinates north = new Coordinates(-1, 0);
    private final Coordinates south = new Coordinates(1, 0);
    private final Coordinates east = new Coordinates(0, 1);
    private final Coordinates west = new Coordinates(0, -1);

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
    void chooseDirectionOneAxisEast() {
        emptyCells = Arrays.asList(

                new Coordinates(3, 3),
                new Coordinates(3, 4),
                new Coordinates(3, 5)
        );
        enemyBoard.setNonEmptyCells(emptyCells);

        List<Coordinates> candidates = new ArrayList<>();
        candidates.add(new Coordinates(3, 3));
        candidates.add(new Coordinates(3, 5));
        Coordinates result = enemyBoard.getLongestEmptyDirectionInOneAxis(candidates.toArray(new Coordinates[0]), true);
        assertEquals(east, result);

    }

    @Test
    void chooseDirectionOneAxisNorth() {
        emptyCells = Arrays.asList(
                new Coordinates(6, 3),
                new Coordinates(7, 3),
                new Coordinates(8, 3)
        );

        enemyBoard.setNonEmptyCells(emptyCells);

        List<Coordinates> candidates = new ArrayList<>();
        candidates.add(new Coordinates(6, 3));
        candidates.add(new Coordinates(8, 3));
        Coordinates result = enemyBoard.getLongestEmptyDirectionInOneAxis(candidates.toArray(new Coordinates[0]), false);
        assertEquals(north, result);

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
    void LongestEmptyDirectionWest() {
        enemyBoard.setNonEmptyCells(emptyCells);
        Coordinates start = new Coordinates(1, 6);
        Coordinates result = enemyBoard.getLongestEmptyDirection(start);

        assertEquals(west, result);

    }




    @Test
    void LongestEmptyDirectionEast() {
        enemyBoard.setNonEmptyCells(emptyCells);
        Coordinates start = new Coordinates(1, 4);
        Coordinates result = enemyBoard.getLongestEmptyDirection(start);

        assertEquals(east, result);
    }


    @Test
    void LongestEmptyDirectionNorth2() {
        emptyCells = Arrays.asList(
                new Coordinates(6, 3),
                new Coordinates(7, 3),
                new Coordinates(8, 3)
        );
        enemyBoard.setNonEmptyCells(emptyCells);
        Coordinates start = new Coordinates(6, 3);
        Coordinates result = enemyBoard.getLongestEmptyDirection(start);

        assertEquals(north, result);
    }


    @Test
    void LongestEmptyDirectionNorth() {
        enemyBoard.setNonEmptyCells(emptyCells);
        Coordinates start = new Coordinates(9, 5);
        Coordinates result = enemyBoard.getLongestEmptyDirection(start);

        assertEquals(north, result);
    }

    @Test
    void LongestEmptyDirectionSouth() {
        enemyBoard.setNonEmptyCells(emptyCells);
        Coordinates start = new Coordinates(0, 9);
        Coordinates result = enemyBoard.getLongestEmptyDirection(start);

        assertEquals(south, result);
    }




    //using reflection to get acess to private method.
    @Test
    void EmptyCellCounter() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        enemyBoard.setNonEmptyCells(emptyCells);
        Coordinates start = new Coordinates(1, 6);


        Method privateMethod = EnemyBoard.class.getDeclaredMethod("countEmptyCellsInDirection", Coordinates.class, Coordinates.class);
        privateMethod.setAccessible(true);

        int northCount = (int) privateMethod.invoke(enemyBoard, start, north);
        int southCount = (int) privateMethod.invoke(enemyBoard, start, south);
        int eastCount = (int) privateMethod.invoke(enemyBoard, start, east);
        int westCount = (int) privateMethod.invoke(enemyBoard, start, west);

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