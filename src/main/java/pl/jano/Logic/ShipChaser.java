package pl.jano.Logic;

import java.util.*;

public class ShipChaser {
    private boolean chase;
    private Boolean horizontal;
    private boolean pivotSet;
    private final List<Integer> fleet;
    private List<Coordinates> hitCells;

    public ShipChaser(boolean chase, List<Integer> fleet) {
        this.chase = chase;
        this.fleet = fleet;
        this.horizontal = null;
        this.pivotSet = false;
        this.hitCells = new ArrayList<>();
    }

    public void clearHitList(){
        this.hitCells.clear();
    }

    public List<Coordinates> getHitCells() {
        return hitCells;
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

    public Boolean getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(Boolean horizontal) {
        this.horizontal = horizontal;
    }

    public Boolean getPivotSet() {
        return pivotSet;
    }

    public void setPivotSet(Boolean pivotSet) {
        this.pivotSet = pivotSet;
    }

    public void setHitCells(List<Coordinates> hitCells) {
        this.hitCells = hitCells;
    }

    public void checkPossiblePivot() {
        if (hitCells.size() != 2)
            return;

        Map<Integer, Integer> xCoordMap = new HashMap<>();
        Map<Integer, Integer> yCoordMap = new HashMap<>();

        for (Coordinates c : hitCells) {
            int x = c.getxCoord();
            int y = c.getyCoord();
            xCoordMap.put(x, xCoordMap.getOrDefault(x, 0) + 1);
            yCoordMap.put(y, yCoordMap.getOrDefault(y, 0) + 1);

            if (xCoordMap.get(x) > 1) {
                pivotSet = true;
                this.horizontal = true;
                return;
            }

            if (yCoordMap.get(y) > 1) {
                pivotSet = true;
                this.horizontal = false;
                return;
            }
        }
        pivotSet = false;
    }

    public Coordinates chooseCandidate(Coordinates[] candidates, Coordinates direction) {
        Coordinates candidateA = candidates[0];
        Coordinates candidateB = candidates[1];
        if (Coordinates.manhattanDistance(Coordinates.addCoordinates(candidateA, direction), candidateB) > Coordinates.manhattanDistance(candidateA, candidateB))
            return candidateA;
        else
            return candidateB;
    }

    public Coordinates[] getEdgedCoordinatesFromList() {
        if (horizontal)
            hitCells.sort(Comparator.comparingInt(Coordinates::getxCoord));
        else
            hitCells.sort(Comparator.comparingInt(Coordinates::getyCoord));

        List<Coordinates> result = new ArrayList<>();
        result.add(hitCells.getFirst());
        result.add(hitCells.getLast());

        return result.toArray(new Coordinates[0]);
    }

    public void addHitCell(Coordinates hit) {
        hitCells.add(hit);
    }

    public void chaseFinished(){
        setChase(false);
        removeSankShip();
        clearHitList();
        setPivotSet(false);
        setHorizontal(false);
    }

    public void printFleet(){
        System.out.println("Fleet: ");
        for (int i:fleet) {
            System.out.println(i);
        }
    }

    public void removeSankShip() {
        int sunkShipLength = hitCells.size();
        for(int i =0;i< fleet.size();i++)
            if(fleet.get(i)==sunkShipLength) {
                fleet.remove(i);
                break;
            }
    }


}
