package mm.maze.main;

import mm.maze.cell.Cell;
import mm.maze.cell.Directions;

import java.io.Serializable;
import java.util.*;

public class Node implements Serializable {

    Position position;
    Maze maze;
    public Integer distanceFromRoot;
    boolean isRoot;
    Map<Node, AdjacentNodeProperties> adjacentNodes; //private ?
    public Set<Position> pathFromRoot;


    public Node(int x, int y, Maze maze, boolean isRoot) {
        this.position = new Position(x, y);
        this.maze = maze;
        this.pathFromRoot = new HashSet<>();
        this.isRoot = isRoot;

        if (this.isRoot) this.distanceFromRoot = 0;
        else this.distanceFromRoot = MazeConfig.MAX_DISTANCE_FROM_ROOT;
    }

    public Position getPosition() {
        return position;
    }

    public Map<Node, AdjacentNodeProperties> findAdjacentNodes() {
        this.adjacentNodes = new HashMap<>();
        Set<Position> path = new HashSet<>();
        path.add(this.position);
        for (Directions direction : getPossibleDirections(this.position)) {
            findNextNode(new Position(position.getX(), position.getY()), direction, 0, new HashSet<>(path));
        }
        return adjacentNodes;
    }

    private void findNextNode(Position position, Directions direction, int distance, Set<Position> path) {

        position.update(direction);
        path.add(new Position(position.getX(), position.getY()));
        distance++;

        Node n = maze.getNode(position);
        if (n != null) { //node found
            if (!adjacentNodes.containsKey(n)) { //new node in adjacent nodes
                AdjacentNodeProperties anp = new AdjacentNodeProperties(distance, new HashSet<>(path));
                adjacentNodes.put(n, anp);
            } else { //node already exist in adjacent nodes
                AdjacentNodeProperties anp = adjacentNodes.get(n);
                if (distance < anp.distance)
                    adjacentNodes.put(n, new AdjacentNodeProperties(distance, new HashSet<>(path)));
            }
        } else { //node not found
            List<Directions> possibleDirections = getPossibleDirections(position);
            if (possibleDirections.size() > 1)
                for (Directions dir : possibleDirections)
                    if (!dir.equals(Directions.getOppositeDirection(direction)))
                        findNextNode(new Position(position.getX(), position.getY()), dir, distance, new HashSet<>(path));
        }
    }

    private List<Directions> getPossibleDirections(Position p) {
        Cell[][] matrix = maze.getMatrix();
        return matrix[p.getX()][p.getY()].getPossibleDirections();
    }

    public void setDistanceFromRoot(Integer distanceFromRoot) {
        this.distanceFromRoot = distanceFromRoot;
    }
}


