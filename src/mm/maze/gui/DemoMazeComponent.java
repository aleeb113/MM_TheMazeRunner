package mm.maze.gui;

import mm.maze.cell.Cell;
import mm.maze.cell.Directions;
import mm.maze.main.Maze;
import mm.maze.main.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DemoMazeComponent extends MazeComponent {

    private Maze maze;
    private Set<Position> runnersPositions;
    private int rs;

    public DemoMazeComponent(Maze maze) {
        super(maze);
        this.maze = maze;
        this.runnersPositions = getRunnersStartPositions();
        this.rs = maze.getSize().getRectangularSize();
        final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(() -> repaintMe(), 0, 500, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        paintRunners(g2D);
        updateRunnersPositions();
    }

    private void paintRunners(Graphics2D g2D) {
        for (Position p : runnersPositions) {
            int x = p.getX();
            int y = p.getY();
            g2D.drawImage(Graphix.creeper, x * rs, y * rs, rs, rs, null);
        }
    }

    private Set<Position> getRunnersStartPositions() {
        Set<Position> positions = new HashSet<>();
        positions.add(new Position(1, 5));  // M
        positions.add(new Position(7, 5));  // A
        positions.add(new Position(11, 1)); // Z
        positions.add(new Position(18, 1)); // E

        positions.add(new Position(1, 12)); // R
        positions.add(new Position(6, 7));  // U
        positions.add(new Position(10, 7)); // N
        positions.add(new Position(15, 12));// N
        positions.add(new Position(22, 7)); // E
        positions.add(new Position(27, 12));// R
        return positions;
    }

    private void updateRunnersPositions() {
        Random random = new Random();
        for (Position p : runnersPositions) {
            int x = p.getX();
            int y = p.getY();
            Cell[][] matrix = maze.getMatrix();
            ArrayList<Directions> directions = (ArrayList<Directions>) matrix[x][y].getPossibleDirections();
            p.update(directions.get(random.nextInt(directions.size())));
        }
    }

    private void repaintMe() {
        this.repaint();
    }
}

