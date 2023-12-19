package pl.jano.Logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipChaserTest {

    private ShipChaser shipChaser;

    @BeforeEach
    void setUp() {
        shipChaser = new ShipChaser(false, 5);
    }

    @Test
    void checkExistedVerticalPivot() {
        List<Coordinates> hittedCells = new ArrayList<>(Arrays.asList(
                new Coordinates(0, 0),
                new Coordinates(1, 1),
                new Coordinates(2, 2),
                new Coordinates(3, 3),
                new Coordinates(3, 4),
                new Coordinates(3, 5),
                new Coordinates(3, 6)
        ));

        shipChaser.setHittedCells(hittedCells);
        shipChaser.checkPossiblePivot();
        assertFalse(shipChaser.getHorizontal());
        assertTrue(shipChaser.getPivotSetted());

    }


    @Test
    void checkExistedHorizontalPivot() {
        List<Coordinates> hittedCells = new ArrayList<>(Arrays.asList(
                new Coordinates(0, 0),
                new Coordinates(1, 1),
                new Coordinates(2, 2),
                new Coordinates(3, 3),
                new Coordinates(4, 3),
                new Coordinates(5, 3),
                new Coordinates(6, 3)
        ));

        shipChaser.setHittedCells(hittedCells);
        shipChaser.checkPossiblePivot();
        assertTrue(shipChaser.getHorizontal());
        assertTrue(shipChaser.getPivotSetted());
    }

    @Test
    void testNoPivot() {
        List<Coordinates> hittedCells = new ArrayList<>(Arrays.asList(
                new Coordinates(0, 0),
                new Coordinates(1, 1),
                new Coordinates(2, 2),
                new Coordinates(3, 3),
                new Coordinates(4, 4),
                new Coordinates(6, 6)
        ));

        shipChaser.setHittedCells(hittedCells);
        shipChaser.checkPossiblePivot();
        assertNull(shipChaser.getHorizontal());
        assertFalse(shipChaser.getPivotSetted());

    }
}