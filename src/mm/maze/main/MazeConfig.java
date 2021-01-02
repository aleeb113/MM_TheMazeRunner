package mm.maze.main;

import mm.maze.gui.Graphix;

import java.awt.*;
import java.util.ArrayList;

public class MazeConfig {

    static final int MINIMAL_SIZE = 3;
    static final char MAZE_CHAR_VALUE = 49;
    public static final Dimension MAZE_COMPONENT_SIZE = new Dimension(1200, 700);
    static final int MAX_DISTANCE_FROM_ROOT = 2147483647;
    public static final String WELCOME_TEXT = "Welcome to MAZE RUNNER \n\nPress PLAY to start game \nor CREATE your own maze";
    public static ArrayList<Level> LEVELS = new ArrayList<>(){{
        add(new Level("src/mm/maze/levels/level01.maze", Graphix.brick,Graphix.white,500));
        add(new Level("src/mm/maze/levels/level02.maze", Graphix.stone,Graphix.grey,500));
        add(new Level("src/mm/maze/levels/level03.maze", Graphix.mossyStone,Graphix.grey,700));
        add(new Level("src/mm/maze/levels/level04.maze", Graphix.rock,Graphix.black,700));
        add(new Level("src/mm/maze/levels/level05.maze", Graphix.soil,Graphix.sand,1000));
        add(new Level("src/mm/maze/levels/level06.maze", Graphix.brick,Graphix.black,1000));
    }};


}
