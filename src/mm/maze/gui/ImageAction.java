package mm.maze.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ImageAction extends AbstractAction {

    private MazeFrame frame;
    private boolean changeWall;
    private boolean changeWay;

    public ImageAction(Image newWallImage, MazeFrame frame, Boolean wall, Boolean way) {
        this.frame = frame;
        this.changeWall = wall;
        this.changeWay = way;
        putValue("new Image", newWallImage);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (changeWall) Graphix.wallImage = (Image) getValue("new Image");
        if (changeWay) Graphix.wayImage = (Image) getValue("new Image");
        frame.repaint();
    }
}