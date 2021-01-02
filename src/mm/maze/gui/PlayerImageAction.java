package mm.maze.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PlayerImageAction extends AbstractAction {

    Image playerImage;
    JFrame frame;

    public PlayerImageAction(Image newPlayerImage, JFrame frame) {
        this.playerImage = newPlayerImage;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Graphix.playerImage = playerImage;
        frame.repaint();

    }
}
