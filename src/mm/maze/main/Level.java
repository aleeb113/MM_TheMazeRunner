package mm.maze.main;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Level {

    private Maze maze;
    private Image way;
    private Image wall;
    private int maxPoints;


    public Level(String mazePath, Image wall, Image way, int maxPoints) {

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(mazePath));
            this.maze = (Maze) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.way = way;
        this.wall = wall;
        this.maxPoints = maxPoints;
    }


    public Maze getMaze() {
        return maze;
    }

    public Image getWay() {
        return way;
    }

    public Image getWall() {
        return wall;
    }

    public int getMaxPoints() {
        return maxPoints;
    }
}
