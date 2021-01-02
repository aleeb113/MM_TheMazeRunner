package mm.maze.cell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cell implements Serializable {

    public boolean way;

    private List<Directions> possibleDirections = new ArrayList<>();

    public Cell(boolean way) {
        this.way = way;
    }

    public boolean isWay() {
        return way;
    }

    public List<Directions> getPossibleDirections() {
        return possibleDirections;
    }

    public void setWay(boolean way) {
        this.way = way;
    }

    public void setPossibleDirections(List<Directions> possibleDirections) {
        this.possibleDirections = possibleDirections;
    }

    @Override
    public String toString() {
        return "Cell [way=" + way + ", possibleDirections=" + possibleDirections + "]";
    }

}
