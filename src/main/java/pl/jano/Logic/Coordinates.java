package pl.jano.Logic;

import java.util.Objects;

public class Coordinates {
    private int xCoord;
    private int yCoord;

    public Coordinates(int yCoord, int xCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public static int manhattanDistance(Coordinates coord1, Coordinates coord2) {
        return Math.abs(coord1.xCoord - coord2.xCoord) + Math.abs(coord1.yCoord - coord2.yCoord);
    }

    public void addCoordinates(Coordinates coord) {
        this.xCoord += coord.getxCoord();
        this.yCoord += coord.getyCoord();
    }

    public static Coordinates addCoordinates(Coordinates coord1, Coordinates coord2) {
        int newX = coord1.xCoord + coord2.xCoord;
        int newY = coord1.yCoord + coord2.yCoord;
        return new Coordinates(newY, newX);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return xCoord == that.xCoord && yCoord == that.yCoord;
    }

    @Override
    public String toString() {
        return "Coordinates{"
                + "xCoord=" + xCoord
                + ", yCoord=" + yCoord
                + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoord, yCoord);
    }

    public void print(){
        System.out.println("Y: "+(getyCoord()+1) +" X: "+ (getxCoord() + 1));
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }
}
