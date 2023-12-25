package pl.jano.Logic;

import org.checkerframework.checker.units.qual.C;
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
        List<Integer> fleet = new ArrayList<>();
        fleet.add(5);
        fleet.add(4);
        fleet.add(3);
        fleet.add(3);
        fleet.add(2);
        shipChaser = new ShipChaser(false, fleet);
    }


    @Test
    void chooseCandidate() {
        Coordinates[] candidates = {
                new Coordinates(1, 3),
                new Coordinates(2, 3)
        };
        Coordinates direction = new Coordinates(1, 0);

        Coordinates result = shipChaser.chooseCandidate(candidates, direction);
        assertEquals(new Coordinates(2, 3), result);


    }


    @Test
    void EdgedValuesTestVertical() {
        shipChaser.setHorizontal(false);
        List<Coordinates> hittedCells = new ArrayList<>(Arrays.asList(
                new Coordinates(3, 3),
                new Coordinates(4, 3),
                new Coordinates(5, 3),
                new Coordinates(6, 3)
        ));
        shipChaser.setHittedCells(hittedCells);

        Coordinates[] result = shipChaser.getEdgedCoordinatesFromList();
        assertEquals(new Coordinates(3, 3), result[0]);
        assertEquals(new Coordinates(6, 3), result[1]);
    }

    @Test
    void EdgedValuesTestHorizontal() {
        shipChaser.setHorizontal(true);
        List<Coordinates> hittedCells = new ArrayList<>(Arrays.asList(
                new Coordinates(6, 2),
                new Coordinates(6, 3),
                new Coordinates(6, 4),
                new Coordinates(6, 1)
        ));
        shipChaser.setHittedCells(hittedCells);

        Coordinates[] result = shipChaser.getEdgedCoordinatesFromList();
        assertEquals(new Coordinates(6, 1), result[0]);
        assertEquals(new Coordinates(6, 4), result[1]);
    }

    @Test
    void checkExistedVerticalPivot() {
        List<Coordinates> hittedCells = new ArrayList<>(Arrays.asList(
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
                new Coordinates(4, 3),
                new Coordinates(5, 3)
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

    @Test
    void removeSinkedShipTest() {
        List<Coordinates> hittedCells = new ArrayList<>(Arrays.asList(
                new Coordinates(0, 0),
                new Coordinates(1, 1),
                new Coordinates(2, 2),
                new Coordinates(2, 3)
        ));
        shipChaser.setHittedCells(hittedCells);
        shipChaser.removeSinkedShip();
        assertEquals(4, shipChaser.getFleet().size());
        assertFalse(shipChaser.getFleet().contains(4));
    }
}