package mm.maze.gui;

import mm.maze.main.Maze;
import mm.maze.main.Node;
import mm.maze.main.Position;

import java.awt.*;
import java.util.ArrayList;

public class MazeComponentWithNodes extends MazeComponent {
    private Maze maze;
    private ArrayList<Node> nodes;

    public MazeComponentWithNodes(Maze maze) {
        super(maze);
        this.maze = maze;
        this.nodes = this.maze.getNodes();
        System.out.println(nodes.size());
        Graphix.wayImage = Graphix.white;
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        int rs = maze.getSize().getRectangularSize();
        Graphics2D g2D = (Graphics2D) g;
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            String s = String.valueOf(i);
            Position p = n.getPosition();

            g2D.drawImage(Graphix.node, p.getX() * rs, p.getY() * rs, rs, rs, null);
            g2D.drawString(s, (1+p.getX()) * rs - rs , (1+p.getY()) * rs-rs/2);
        }
    }
}
