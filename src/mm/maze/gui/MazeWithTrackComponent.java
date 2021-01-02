package mm.maze.gui;

import mm.maze.main.Maze;
import mm.maze.main.Node;
import mm.maze.main.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class MazeWithTrackComponent extends MazeComponent {
    Maze maze;

    public MazeWithTrackComponent(Maze maze) {
        super(maze);
        this.maze = maze;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        ArrayList<Node> tmp = this.maze.getNodes();
        Set<Position> track = tmp.get(tmp.size()-1).pathFromRoot;
        int rs = maze.getSize().getRectangularSize();
        Image trackImage = getTrackImage();
        for (Position position : track) {
            g2D.drawImage(trackImage, position.getX() * rs, position.getY() * rs, rs, rs, null);
        }
    }

    private Image getTrackImage() {
        if (Graphix.wayImage.equals(Graphix.grey)) return Graphix.greyTrack;
        else if (Graphix.wayImage.equals(Graphix.white)) return Graphix.whiteTrack;
        else if (Graphix.wayImage.equals(Graphix.black)) return Graphix.blackTrack;
        else return Graphix.sandTrack;
    }
}
