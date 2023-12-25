package pl.jano.Logic;

public class Cell {
    private boolean empty;

    private Integer probabilityOfHit;

    public Cell(boolean empty) {
        this.empty = empty;
        this.probabilityOfHit = 0;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public Integer getProbabilityOfHit() {
        return probabilityOfHit;
    }

    public void setProbabilityOfHit(Integer probabilityOfHit) {
        this.probabilityOfHit = probabilityOfHit;
    }


    public void increaseProbability() {
        this.probabilityOfHit++;
    }
}
