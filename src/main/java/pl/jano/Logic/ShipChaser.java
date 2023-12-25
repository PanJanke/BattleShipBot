package pl.jano.Logic;

import java.util.*;

public class ShipChaser {
    private boolean chase;
    private Boolean horizontal;
    private boolean pivotSetted;
    private List<Integer> fleet;
    private List<Coordinates> hittedCells;

    public ShipChaser(boolean chase, List<Integer> fleet) {
        this.chase = chase;
        this.fleet = fleet;
        this.horizontal = null;
        this.pivotSetted = false;
        this.hittedCells = new ArrayList<>();
    }

    public void clearHittedList(){
        this.hittedCells.clear();
    }

    public List<Coordinates> getHittedCells() {
        return hittedCells;
    }

    public boolean isChase() {
        return chase;
    }

    public void setChase(boolean chase) {
        this.chase = chase;
    }

    public List<Integer> getFleet() {
        return fleet;
    }

    public void setFleet(List<Integer> fleet) {
        this.fleet = fleet;
    }

    public Boolean getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(Boolean horizontal) {
        this.horizontal = horizontal;
    }

    public Boolean getPivotSetted() {
        return pivotSetted;
    }

    public void setPivotSetted(Boolean pivotSetted) {
        this.pivotSetted = pivotSetted;
    }

    public void setHittedCells(List<Coordinates> hittedCells) {
        this.hittedCells = hittedCells;
    }

    public void checkPossiblePivot() {
        if (hittedCells.size() != 2)
            return;

        Map<Integer, Integer> xCoordMap = new HashMap<>();
        Map<Integer, Integer> yCoordMap = new HashMap<>();

        for (Coordinates c : hittedCells) {
            int x = c.getxCoord();
            int y = c.getyCoord();
            xCoordMap.put(x, xCoordMap.getOrDefault(x, 0) + 1);
            yCoordMap.put(y, yCoordMap.getOrDefault(y, 0) + 1);

            if (xCoordMap.get(x) > 1) {
                pivotSetted = true;
                this.horizontal = true;
                return;
            }

            if (yCoordMap.get(y) > 1) {
                pivotSetted = true;
                this.horizontal = false;
                return;
            }
        }
        pivotSetted = false;
    }

    public Coordinates chooseCandidate(Coordinates[] candidates, Coordinates direction) {
        Coordinates candidateA = candidates[0];
        Coordinates candidateB = candidates[1];
        System.out.println("cos tu niby jest nulem");
        candidateA.print();
        candidateB.print();
        if (Coordinates.manhattanDistance(Coordinates.addCoordinates(candidateA, direction), candidateB) > Coordinates.manhattanDistance(candidateA, candidateB))
            return candidateA;
        else
            return candidateB;
    }

    public Coordinates[] getEdgedCoordinatesFromList() {
        if (horizontal)
            hittedCells.sort(Comparator.comparingInt(Coordinates::getxCoord));
        else
            hittedCells.sort(Comparator.comparingInt(Coordinates::getyCoord));

        List<Coordinates> result = new ArrayList<>();
        result.add(hittedCells.getFirst());
        result.add(hittedCells.getLast());

        return result.toArray(new Coordinates[0]);
    }

    public void addHittedCell(Coordinates hit) {
        hittedCells.add(hit);
    }

    public void removeSinkedShip() {

        int sinkedShipLength = hittedCells.size();
        Iterator<Integer> iterator = fleet.iterator();

        while (iterator.hasNext()) {
            int currentElement = iterator.next();
            if (currentElement == sinkedShipLength) {
                iterator.remove();
                return;
            }
        }

    }


}
