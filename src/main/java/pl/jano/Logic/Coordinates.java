package pl.jano.Logic;

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

    public void addCoordinates(Coordinates coord){
        this.xCoord+=coord.getxCoord();
        this.yCoord+=coord.getyCoord();

    }
}
