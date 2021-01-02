package mm.maze.main;

import mm.maze.gui.MazeFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

class TheMazeRunner {

    public static void main(String[] args) throws IOException {

        EventQueue.invokeLater(() -> {
            MazeFrame frame = new MazeFrame();
            frame.setTitle("Maze Runner");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);


        });
    }

}
