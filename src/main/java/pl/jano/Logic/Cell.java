package pl.jano.Logic;

public class Cell {
    private boolean empty;
    private Integer probabilityOfHit;

    public Cell(boolean empty, Integer probabilityOfHit) {
        this.empty = empty;
        this.probabilityOfHit = probabilityOfHit;
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
}
