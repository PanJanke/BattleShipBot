package pl.jano.Logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    @Test
    void addCoordinates() {
        Coordinates a = new Coordinates(1, 1);
        Coordinates b = new Coordinates(-1, 1);
        a.addCoordinates(b);
        assertEquals(a.getyCoord(), 0);
        assertEquals(a.getxCoord(), 2);
    }

    @Test
    void addCoordinates2() {
        Coordinates a = new Coordinates(1, 6);
        Coordinates b = new Coordinates(1, 0);
        a.addCoordinates(b);
        assertEquals(a.getyCoord(), 2);
        assertEquals(a.getxCoord(), 6);
    }
}