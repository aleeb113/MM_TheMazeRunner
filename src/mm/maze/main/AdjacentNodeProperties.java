package mm.maze.main;

import javafx.geometry.Pos;

import java.util.Set;

public class AdjacentNodeProperties {

    Integer distance;
    Set<Position> path;

    public AdjacentNodeProperties(Integer distance, Set<Position> path) {
        this.distance = distance;
        this.path = path;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Set<Position> getPath() {
        return path;
    }

    public void setPath(Set<Position> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "\n{" +
                "distance=" + distance +
                ", path=" + path +
                '}';
    }
}
