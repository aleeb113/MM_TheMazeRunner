package mm.maze.main;

import mm.maze.cell.Cell;
import mm.maze.cell.Directions;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Maze implements Serializable {

    private Size size;
    private Cell[][] matrix;
    private ArrayList<Node> nodes;
    private Queue<Node> nodesToVerify;
    private ArrayList<Node> verifiedNodes; //set?

    public Maze(MazeSize newMazeSize) {
        switch (newMazeSize) {
            case SMALL: {
                this.size = new Size(30, 20);
                break;
            }
            case MEDIUM: {
                this.size = new Size(40, 25);
                break;
            }
            case LARGE: {
                this.size = new Size(50, 30);
                break;
            }
        }
        this.nodes = new ArrayList<>();

        int horizontal = size.getHorizontalSize();
        int vertical = size.getVerticalSize();

        this.matrix = new Cell[horizontal][vertical];

        for (int x = 0; x < horizontal; x++)
            for (int y = 0; y < vertical; y++) {
                this.matrix[x][y] = new Cell(false);
            }
    }


    public void findNodes() {
        this.nodes = new ArrayList<>();
        for (int y = 0; y < this.size.getVerticalSize(); y++) {
            for (int x = 0; x < this.size.getHorizontalSize(); x++) {

                if ((y == 0) && (this.matrix[x])[y].isWay()) {
                    Node root = new Node(x, y, this, true);
                    nodes.add(root);
                }
                if (this.matrix[x][y].isWay()) {
                    List<Directions> possibleDirections = new ArrayList<>();
                    if (x > 0)
                        if (this.matrix[x - 1][y].isWay()) possibleDirections.add(Directions.LEFT);
                    if (x < this.size.getHorizontalSize() - 1)
                        if (this.matrix[x + 1][y].isWay()) possibleDirections.add(Directions.RIGHT);
                    if (y > 0)
                        if (this.matrix[x][y - 1].isWay()) possibleDirections.add(Directions.UP);
                    if (y < this.size.getVerticalSize() - 1)
                        if (this.matrix[x][y + 1].isWay()) possibleDirections.add(Directions.DOWN);
                    this.matrix[x][y].setPossibleDirections(possibleDirections);
                    if (possibleDirections.size() > 2) {
                        this.nodes.add(new Node(x, y, this, false));
                    }
                }
                if ((y == this.size.getVerticalSize() - 1) && (this.matrix[x])[y].isWay()) //exit of maze
                    nodes.add(new Node(x, y, this, false));
            }
        }
    }

    public void findShortestPath() {
        findNodes();
        for (Node n : nodes) n.findAdjacentNodes();

        verifiedNodes = new ArrayList<>();
        nodesToVerify = new LinkedList<>();

        Node root = nodes.get(0);
        verifiedNodes.add(root);
        Node firstNodeToVerify = null;
        if (root.adjacentNodes.size() > 0) {
            firstNodeToVerify = root.adjacentNodes.keySet().iterator().next();
            Integer distanceFromRoot = firstNodeToVerify.adjacentNodes.get(root).distance;
            firstNodeToVerify.setDistanceFromRoot(distanceFromRoot);
            firstNodeToVerify.pathFromRoot = firstNodeToVerify.adjacentNodes.get(root).path;
            nodesToVerify.add(firstNodeToVerify);
        }

        if (firstNodeToVerify != null) {
            Node nextNode = null;
            while (!nodesToVerify.isEmpty()) {
                nextNode = nodesToVerify.remove();
                Map<Node, AdjacentNodeProperties> adjacentNodes = nextNode.adjacentNodes;
                for (Node n : verifiedNodes) adjacentNodes.remove(n);
                findNextNodesToVerify(adjacentNodes, nextNode);
                verifiedNodes.add(nextNode);
            }
        }
    }

    private void findNextNodesToVerify(Map<Node, AdjacentNodeProperties> nodesToSort, Node verifiedNode) {
        List<Node> n = new ArrayList<>(nodesToSort.keySet());
        List<Integer> distances = new ArrayList<>();

        for (AdjacentNodeProperties anp : nodesToSort.values()) distances.add(anp.distance);

        Collections.sort(distances);

        Iterator<Integer> it1 = distances.iterator();

        while (it1.hasNext()) {
            Integer distance1 = it1.next();
            Iterator<Node> it2 = n.iterator();
            while (it2.hasNext()) {
                Node node = it2.next();
                Integer distance2 = nodesToSort.get(node).distance;
                if (distance1.equals(distance2)) {
                    if (!verifiedNodes.contains(node))
                        if (!nodesToVerify.contains(node))
                            nodesToVerify.add(node);
                    if (updateDistanceFromRoot(node, verifiedNode.distanceFromRoot + distance1, verifiedNode)) ;
                    it2.remove();
                    break;
                }
            }
        }
    }

    private boolean updateDistanceFromRoot(Node node, int distance, Node adjacentNode) {
        if (distance < node.distanceFromRoot) {
            node.pathFromRoot = new HashSet<>(adjacentNode.pathFromRoot);
            node.pathFromRoot.addAll(node.adjacentNodes.get(adjacentNode).path);
            node.setDistanceFromRoot(distance);
            return true;
        }
        return false;
    }

    public boolean isCorrect() {
        int rootCounter = 0;
        int finishLineCounter = 0;
        for (int x = 0; x < this.size.getHorizontalSize(); x++) {
            if (matrix[x][0].isWay()) rootCounter++;
            if (matrix[x][this.size.getVerticalSize() - 1].isWay()) finishLineCounter++;
        }
        if (rootCounter != 1 || finishLineCounter != 1) return false;

        for (int y = 0; y < this.size.getVerticalSize(); y++) {
            if (matrix[0][y].isWay()) return false;
            if (matrix[this.size.getHorizontalSize() - 1][y].isWay()) return false;
        }
        return true;
    }


    public Cell[][] getMatrix() {
        return matrix;
    }


    public Size getSize() {
        return size;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    @Deprecated //made for beta version only; for loading maze from text file
    public Cell[][] loadMazeFromFile(String file) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get(file));
        ArrayList linesSize = new ArrayList<Integer>();

        int verticalSize = 0;
        for (String s : lines) {
            linesSize.add(s.length());
            verticalSize++;
        }

        this.size.setVerticalSize(verticalSize);
        this.size.setHorizontalSize((int) linesSize.get(0));

        if (checkMazeShape(linesSize)) {
            return importMaze(lines);
        } else {
            System.out.println("Maze size is incorrect");
            return new Cell[0][0];
        }
    }

    @Deprecated //made for beta version only; for loading maze from text file
    private boolean checkMazeShape(@NotNull ArrayList<Integer> linesSize) {

        int horizontalSize = linesSize.get(0);
        boolean correctSize = true;
        if ((this.size.getVerticalSize() < MazeConfig.MINIMAL_SIZE) || (this.size.getHorizontalSize() < MazeConfig.MINIMAL_SIZE)) {
            correctSize = false;
        } else {
            for (Integer i : linesSize) {
                if (i != horizontalSize) {
                    correctSize = false;
                    break;
                }
            }
        }
        return correctSize;
    }

    @Deprecated //made for beta version only; for loading maze from text file
    public Cell[][] importMaze(List<String> lines) {

        Cell[][] maze = new Cell[this.size.getHorizontalSize()][this.size.getVerticalSize()];

        int y = 0;
        for (String s : lines) {
            for (int x = 0; x < this.size.getHorizontalSize(); x++) {
                if (s.charAt(x) == MazeConfig.MAZE_CHAR_VALUE) {
                    maze[x][y] = new Cell(true);
                } else {
                    maze[x][y] = new Cell(false);
                }
            }
            y++;
        }
        return maze;
    }

    @Deprecated
        //made for beta version only; for printing to console
    void printMaze() {

        System.out.println();
        for (int y = 0; y < this.size.getVerticalSize(); y++) {
            System.out.print("\t");
            for (int x = 0; x < this.size.getHorizontalSize(); x++) {
                if (this.matrix[x][y].isWay()) {
                    System.out.print("   ");
                } else {
                    System.out.print("[I]");
                }
            }
            System.out.println();
        }
    }

    //made for beta version only; in new version there is only one start point
    public List<Position> findStartPoints() {

        List<Position> startPoints = new ArrayList<>();
        int y = 0;
        for (int x = 0; x < this.size.getHorizontalSize(); x++) {
            if (this.matrix[x][y].isWay()) {
                startPoints.add(new Position(x, y));
            }
        }
        return startPoints;
    }

    //made for beta version only; in new version there is only one end point
    public Set<Position> findEndPoints() {

        Set<Position> endpoints = new HashSet<>();
        for (int x = 0; x < this.size.getHorizontalSize(); x++) {
            addPositionToEndpointsListIfIsWay(matrix[x][this.size.getVerticalSize() - 1],
                    new Position(x, this.size.getVerticalSize() - 1), endpoints);
        }
        for (int y = 1; y < this.size.getVerticalSize(); y++) {
            addPositionToEndpointsListIfIsWay(matrix[0][y], new Position(0, y), endpoints);
        }
        for (int y = 1; y < this.size.getVerticalSize(); y++) {
            addPositionToEndpointsListIfIsWay(matrix[this.size.getHorizontalSize() - 1][y],
                    new Position(this.size.getHorizontalSize() - 1, y), endpoints);
        }
        return endpoints;
    }

    @Deprecated //made for beta version; in new version there is only one end point
    private void addPositionToEndpointsListIfIsWay(Cell cell, Position position, Set<Position> endpoints) {
        if (cell.isWay()) {
            endpoints.add(position);
        }
    }


    public boolean isNode(Position position) {
        for (Node n : nodes)
            if (position.equals(n.position)) return true;
        return false;
    }

    public Node getNode(Position position) {
        for (Node n : nodes)
            if (position.equals(n.position)) return n;
        return null;
    }
}
