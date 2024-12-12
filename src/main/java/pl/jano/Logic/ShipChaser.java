package pl.jano.Logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ShipChaser {
    private boolean chase;
    private List<Integer> fleet;
    private LinkedList<Coordinates> hitCells;

    public ShipChaser(boolean chase, List<Integer> fleet) {
        this.chase = chase;
        this.fleet = new ArrayList<>(fleet);
        this.hitCells = new LinkedList<>();
    }

    public void clearHitList() {
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


    public void setHitCells(List<Coordinates> hitCells) {
        this.hitCells = (LinkedList<Coordinates>) hitCells;
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
        if (hitCells.stream().mapToInt(Coordinates::getyCoord).distinct().count() == 1)
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

    public void chaseFinished() {
        setChase(false);
        removeSankShip();
        clearHitList();
    }

    public void printFleet() {
        System.out.println("Fleet: ");
        for (int i : fleet) {
            System.out.println(i);
        }
    }

    public void removeSankShip() {
        int sunkShipLength = hitCells.size();
        for (int i = 0; i < fleet.size(); i++)
            if (fleet.get(i) == sunkShipLength) {
                fleet.remove(i);
                break;
            }
    }


}
