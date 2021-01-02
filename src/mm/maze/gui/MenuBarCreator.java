package mm.maze.gui;

import mm.maze.main.MazeSize;

import javax.swing.*;

public class MenuBarCreator {

    public static MazeFrame frame;

    public MenuBarCreator(MazeFrame frame) {
        this.frame = frame;
        JMenuBar mb = new JMenuBar();
        frame.setJMenuBar(mb);
        mb.add(createFileMenu());
        mb.add(createEditMenu());
        mb.add(createCreateMenu());
    }

    public JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("Exit")).addActionListener((actionEvent -> System.exit(0)));
        return fileMenu;
    }

    public JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        editMenu.add(createSetMazeLookMenu());
        return editMenu;
    }

    public JMenu createSetMazeLookMenu() {
        JMenu setMazeLookMenu = new JMenu("Set maze look");
        setMazeLookMenu.add(createSetWallImageMenu());
        setMazeLookMenu.add(createSetWayImageMenu());
        return setMazeLookMenu;
    }

    public JMenu createSetWallImageMenu() {
        JMenu setWallImageMenu = new JMenu("Set wall image");

        JMenuItem brickItem = new JMenuItem("Brick", new ImageIcon(Graphix.brick.getScaledInstance(32, 32, 1)));
        setWallImageMenu.add(brickItem);
        brickItem.addActionListener(new ImageAction(Graphix.brick, frame, true, false));

        JMenuItem mossyStone = new JMenuItem("Mossy stone", new ImageIcon(Graphix.mossyStone.getScaledInstance(32, 32, 1)));
        setWallImageMenu.add(mossyStone);
        mossyStone.addActionListener(new ImageAction(Graphix.mossyStone, frame, true, false));

        JMenuItem rock = new JMenuItem("Rock", new ImageIcon(Graphix.rock.getScaledInstance(32, 32, 1)));
        setWallImageMenu.add(rock);
        rock.addActionListener(new ImageAction(Graphix.rock, frame, true, false));

        JMenuItem stone = new JMenuItem("Stone", new ImageIcon(Graphix.stone.getScaledInstance(32, 32, 1)));
        setWallImageMenu.add(stone);
        stone.addActionListener(new ImageAction(Graphix.stone, frame, true, false));

        JMenuItem soil = new JMenuItem("Soil", new ImageIcon(Graphix.soil.getScaledInstance(32, 32, 1)));
        setWallImageMenu.add(soil);
        soil.addActionListener(new ImageAction(Graphix.soil, frame, true, false));

        return setWallImageMenu;
    }

    public JMenu createSetWayImageMenu() {
        JMenu setWayImageMenu = new JMenu("Set way image");

        JMenuItem sand = new JMenuItem("Sand", new ImageIcon(Graphix.sand.getScaledInstance(32, 32, 1)));
        setWayImageMenu.add(sand);
        sand.addActionListener(new ImageAction(Graphix.sand, frame, false, true));

        JMenuItem white = new JMenuItem("White", new ImageIcon(Graphix.white.getScaledInstance(32, 32, 1)));
        setWayImageMenu.add(white);
        white.addActionListener(new ImageAction(Graphix.white, frame, false, true));

        JMenuItem black = new JMenuItem("Black", new ImageIcon(Graphix.black.getScaledInstance(32, 32, 1)));
        setWayImageMenu.add(black);
        black.addActionListener(new ImageAction(Graphix.black, frame, false, true));

        JMenuItem grey = new JMenuItem("Grey", new ImageIcon(Graphix.grey.getScaledInstance(32, 32, 1)));
        setWayImageMenu.add(grey);
        grey.addActionListener(new ImageAction(Graphix.grey, frame, false, true));

        return setWayImageMenu;
    }

    public JMenu createCreateMenu() {
        JMenu createMenu = new JMenu(("Create"));
        createMenu.add(createNewMazeMenu());
        return createMenu;
    }

    public JMenu createNewMazeMenu() {
        JMenu newMazeMenu = new JMenu("Create new maze");

        JMenuItem smallMaze = new JMenuItem("Create small maze");
        JMenuItem mediumMaze = new JMenuItem("Create medium maze");
        JMenuItem largeMaze = new JMenuItem("Create large maze");

        newMazeMenu.add(smallMaze);
        newMazeMenu.add(mediumMaze);
        newMazeMenu.add(largeMaze);

       // smallMaze.addActionListener(new newMazeActionListener(MazeSize.SMALL, frame));
     //   mediumMaze.addActionListener(new newMazeActionListener(MazeSize.MEDIUM, frame));
     //   largeMaze.addActionListener(new newMazeActionListener(MazeSize.LARGE, frame));

        return newMazeMenu;
    }


}
