package org.example.Logic;

public class ChaseShip {


    private  boolean chase;
    private int maxPossibleLength;

    public ChaseShip(boolean chase, int maxPossibleLength) {
        this.chase = chase;
        this.maxPossibleLength = maxPossibleLength;
    }

    public boolean isChase() {
        return chase;
    }

    public void setChase(boolean chase) {
        this.chase = chase;
    }

    public int getMaxPossibleLength() {
        return maxPossibleLength;
    }

    public void setMaxPossibleLength(int maxPossibleLength) {
        this.maxPossibleLength = maxPossibleLength;
    }




}
