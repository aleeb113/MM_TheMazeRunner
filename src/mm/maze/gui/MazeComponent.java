package mm.maze.gui;

import mm.maze.main.Maze;
import mm.maze.main.MazeConfig;

import javax.swing.*;
import java.awt.*;

public class MazeComponent extends JComponent {

    private Maze maze;

    public MazeComponent(Maze maze) {
        this.maze = maze;
    }

    @Override
    public Dimension getPreferredSize() {
        return MazeConfig.MAZE_COMPONENT_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        int rs = maze.getSize().getRectangularSize();

        for (int x = 0; x < maze.getSize().getHorizontalSize(); x++)
            for (int y = 0; y < maze.getSize().getVerticalSize(); y++) {
                if (maze.getMatrix()[x][y].isWay())
                    g2D.drawImage(Graphix.wayImage, x * rs, y * rs, rs, rs, null);
                else g2D.drawImage(Graphix.wallImage, x * rs, y * rs, rs, rs, null);
            }
    }
}
