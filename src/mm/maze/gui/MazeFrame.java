package mm.maze.gui;

import mm.maze.cell.Cell;
import mm.maze.main.Maze;
import mm.maze.main.MazeConfig;
import mm.maze.main.MazeSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MazeFrame extends JFrame {

    JPanel infoPanel;
    private JPanel mainPanel;
    private Maze maze;
    JTextArea infoTextArena;
    int levelNumber;
    int totalScore;
    Map<String, JButton> buttons;
    boolean playGame;

    public MazeFrame() throws HeadlessException {

        //TODO TopScores Table

        totalScore = 0;
        levelNumber = 0;
        playGame = true;
        mainPanel = new JPanel();
        createButtons();
        createMenuBar();
        showDemo();
    }

    private void createButtons() {
        buttons = new HashMap<>();
        buttons.put("CreateButton", getCreateButton());
        buttons.put("PrintTrackButton", getPrintTrackButton());
        buttons.put("ShowNodesButton", getShowNodesButton());
        buttons.put("QuitGameButton", getQuitGameButton());
        buttons.put("PlayButton", getPlayButton());
        buttons.put("BackButton", getBackButton());
    }

    private JButton getCreateButton() {
        JButton createButton = new JButton("Create");
        createButton.addActionListener(actionEvent -> {
            String[] mazeSizeOptions = {"Small", "Medium", "Large"};
            int selectedMazeSize = JOptionPane.showOptionDialog(null, "Select maze size", "New Maze Size",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, mazeSizeOptions, mazeSizeOptions[0]);
            if (selectedMazeSize == 0) new newMazeActionListener(MazeSize.SMALL).actionPerformed(null);
            else if (selectedMazeSize == 1) new newMazeActionListener(MazeSize.MEDIUM).actionPerformed(null);
            else if (selectedMazeSize == 2) new newMazeActionListener(MazeSize.LARGE).actionPerformed(null);
        });
        return createButton;
    }

    private JButton getPrintTrackButton() {
        JButton printTrackButton = new JButton("Find the shortest way");
        printTrackButton.addActionListener(actionEvent -> {
            if (this.maze != null) {
                maze.findShortestPath();
                infoTextArena.setText(String.valueOf(maze.getNodes().get(maze.getNodes().size() - 1).distanceFromRoot));
                MazeWithTrackComponent mazeWithTrackComponent = new MazeWithTrackComponent(this.maze);
                mainPanel.removeAll();
                mainPanel.add(mazeWithTrackComponent);
                pack();
                repaint();
            }
        });
        return printTrackButton;
    }

    private JButton getShowNodesButton() {
        JButton showNodesButton = new JButton("Show nodes");
        showNodesButton.addActionListener(actionEvent -> {
            if (this.maze != null) {
                MazeComponentWithNodes mazeWithNodes = new MazeComponentWithNodes(this.maze);
                mainPanel.removeAll();
                mainPanel.add(mazeWithNodes);
                pack();
                repaint();
            }
        });
        return showNodesButton;

    }

    private JButton getQuitGameButton() {
        JButton quitGameButton = new JButton("Quit game");
        quitGameButton.addActionListener(actionEvent -> {
            int quitConfirm = JOptionPane.showConfirmDialog(null, "Do You really want to quit game ?");
            if (quitConfirm == JOptionPane.YES_OPTION) {
                showDemo();
                levelNumber = 0;
                totalScore = 0;
            }
        });
        return quitGameButton;
    }


    private JButton getPlayButton() {
        JButton playButton = new JButton("Play");
        playButton.addActionListener(actionEvent -> {
            PlayMazeComponent playMazeComponent;
            System.out.println(playGame);
            if (playGame) {
                Graphix.wallImage = MazeConfig.LEVELS.get(levelNumber).getWall();
                Graphix.wayImage = MazeConfig.LEVELS.get(levelNumber).getWay();
                playMazeComponent = new PlayMazeComponent(levelNumber, this);
            } else {
                playMazeComponent = new PlayMazeComponent(this.maze, this);
            }
            mainPanel.removeAll();
            mainPanel.add(playMazeComponent);
            pack();
            repaint();
        });
        return playButton;
    }

    private JButton getBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(actionEvent -> {
            playGame = true;
            showDemo();
        });
        return backButton;
    }

    private void showDemo() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("MazeRunnerDemoMaze.maze"));
            Maze demo = (Maze) in.readObject();
            DemoMazeComponent demoMaze = new DemoMazeComponent(demo);
            mainPanel.removeAll();
            mainPanel.add(demoMaze);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (infoPanel != null) remove(infoPanel);
        infoPanel = getInfoPanel(MazeConfig.WELCOME_TEXT);
        add(mainPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.SOUTH);
        pack();
        repaint();
    }


    public JPanel getInfoPanel(String textToDisplay) {
        JPanel panel = new JPanel();

        infoTextArena = new JTextArea(textToDisplay, 6, 30);
        JScrollPane infoScrollPane = new JScrollPane(infoTextArena);
        panel.add(infoScrollPane);

        panel.add(buttons.get("CreateButton"));
        panel.add(buttons.get("PlayButton"));


        return panel;
    }

    private void reloadMaze(Maze maze) {
        this.maze = maze;
        mainPanel.removeAll();
        remove(infoPanel);
        infoPanel = getInfoPanel(MazeConfig.WELCOME_TEXT);
        infoPanel.add(buttons.get("BackButton"));
        infoPanel.add(buttons.get("ShowNodesButton"));
        infoPanel.add(buttons.get("PrintTrackButton"));
        infoTextArena.setText("You can PLAY the maze here\n\n" +
                "You can also find nodes and shortest way to finish");

        mainPanel.add(new MazeComponent(maze), BorderLayout.NORTH);
        add(infoPanel, BorderLayout.SOUTH);
        pack();
        repaint();
    }

    private void createMenuBar() {
        JMenuBar mb = new JMenuBar();
        this.setJMenuBar(mb);
        mb.add(createFileMenu());
        mb.add(createEditMenu());
        mb.add(createCreateMenu());
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        fileMenu.add(new JMenuItem("Open")).addActionListener(actionEvent -> {
            if (chooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
                playGame = false;
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile().getName()));
                    maze = (Maze) in.readObject();
                    reloadMaze(maze);
                } catch (IOException | ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(null, "This not maze file");
                    e.printStackTrace();
                }
            }
        });

        fileMenu.add(new JMenuItem("Exit")).addActionListener((actionEvent -> System.exit(0)));
        return fileMenu;
    }


    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        editMenu.add(createSetMazeLookMenu());
        editMenu.add(createSetPlayerLookMenu());
        return editMenu;
    }

    private JMenu createSetPlayerLookMenu() {
        JMenu playerLookMenu = new JMenu("Set player look");

        JMenuItem crepperItem = new JMenuItem("Creeper", new ImageIcon(Graphix.creeper.getScaledInstance(32, 32, 1)));
        JMenuItem alexItem = new JMenuItem("Alex", new ImageIcon(Graphix.alex.getScaledInstance(32, 32, 1)));
        JMenuItem steveItem = new JMenuItem("Steve", new ImageIcon(Graphix.steve.getScaledInstance(32, 32, 1)));

        playerLookMenu.add(crepperItem);
        playerLookMenu.add(steveItem);
        playerLookMenu.add(alexItem);

        crepperItem.addActionListener(new PlayerImageAction(Graphix.creeper, this));
        steveItem.addActionListener(new PlayerImageAction(Graphix.steve, this));
        alexItem.addActionListener(new PlayerImageAction(Graphix.alex, this));

        return playerLookMenu;
    }


    private JMenu createSetMazeLookMenu() {
        JMenu setMazeLookMenu = new JMenu("Set maze look");
        setMazeLookMenu.add(createSetWallImageMenu());
        setMazeLookMenu.add(createSetWayImageMenu());
        return setMazeLookMenu;
    }

    private JMenu createSetWallImageMenu() {
        JMenu setWallImageMenu = new JMenu("Set wall image");

        JMenuItem brickItem = new JMenuItem("Brick", new ImageIcon(Graphix.brick.getScaledInstance(32, 32, 1)));
        JMenuItem mossyStone = new JMenuItem("Mossy stone", new ImageIcon(Graphix.mossyStone.getScaledInstance(32, 32, 1)));
        JMenuItem rock = new JMenuItem("Rock", new ImageIcon(Graphix.rock.getScaledInstance(32, 32, 1)));
        JMenuItem stone = new JMenuItem("Stone", new ImageIcon(Graphix.stone.getScaledInstance(32, 32, 1)));
        JMenuItem soil = new JMenuItem("Soil", new ImageIcon(Graphix.soil.getScaledInstance(32, 32, 1)));

        setWallImageMenu.add(brickItem);
        setWallImageMenu.add(mossyStone);
        setWallImageMenu.add(rock);
        setWallImageMenu.add(stone);
        setWallImageMenu.add(soil);

        brickItem.addActionListener(new ImageAction(Graphix.brick, this, true, false));
        mossyStone.addActionListener(new ImageAction(Graphix.mossyStone, this, true, false));
        rock.addActionListener(new ImageAction(Graphix.rock, this, true, false));
        stone.addActionListener(new ImageAction(Graphix.stone, this, true, false));
        soil.addActionListener(new ImageAction(Graphix.soil, this, true, false));

        return setWallImageMenu;
    }

    private JMenu createSetWayImageMenu() {
        JMenu setWayImageMenu = new JMenu("Set way image");

        JMenuItem sand = new JMenuItem("Sand", new ImageIcon(Graphix.sand.getScaledInstance(32, 32, 1)));
        setWayImageMenu.add(sand);
        sand.addActionListener(new ImageAction(Graphix.sand, this, false, true));

        JMenuItem white = new JMenuItem("White", new ImageIcon(Graphix.white.getScaledInstance(32, 32, 1)));
        setWayImageMenu.add(white);
        white.addActionListener(new ImageAction(Graphix.white, this, false, true));

        JMenuItem black = new JMenuItem("Black", new ImageIcon(Graphix.black.getScaledInstance(32, 32, 1)));
        setWayImageMenu.add(black);
        black.addActionListener(new ImageAction(Graphix.black, this, false, true));

        JMenuItem grey = new JMenuItem("Grey", new ImageIcon(Graphix.grey.getScaledInstance(32, 32, 1)));
        setWayImageMenu.add(grey);
        grey.addActionListener(new ImageAction(Graphix.grey, this, false, true));

        return setWayImageMenu;
    }

    private JMenu createCreateMenu() {
        JMenu createMenu = new JMenu(("Create"));
        createMenu.add(createNewMazeMenu());
        return createMenu;
    }

    private JMenu createNewMazeMenu() {
        JMenu newMazeMenu = new JMenu("Create new maze");

        JMenuItem smallMaze = new JMenuItem("Create small maze");
        JMenuItem mediumMaze = new JMenuItem("Create medium maze");
        JMenuItem largeMaze = new JMenuItem("Create large maze");

        newMazeMenu.add(smallMaze);
        newMazeMenu.add(mediumMaze);
        newMazeMenu.add(largeMaze);

        smallMaze.addActionListener(new newMazeActionListener(MazeSize.SMALL));
        mediumMaze.addActionListener(new newMazeActionListener(MazeSize.MEDIUM));
        largeMaze.addActionListener(new newMazeActionListener(MazeSize.LARGE));


        return newMazeMenu;
    }

    private class newMazeActionListener implements ActionListener {
        private Maze newMaze;
        private Rectangle2D[][] recs;

        public newMazeActionListener(MazeSize size) {
            newMaze = new Maze(size);
            recs = new Rectangle2D[newMaze.getSize().getHorizontalSize()][newMaze.getSize().getVerticalSize()];
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            playGame = false;
            int rs = newMaze.getSize().getRectangularSize();
            JComponent newMazeCreator = new JComponent() {
                @Override
                public Dimension getPreferredSize() {
                    return MazeConfig.MAZE_COMPONENT_SIZE;
                }

                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2D = (Graphics2D) g;
                    for (int x = 0; x < newMaze.getSize().getHorizontalSize(); x++)
                        for (int y = 0; y < newMaze.getSize().getVerticalSize(); y++) {
                            g2D.setColor(Color.GRAY);
                            Rectangle2D rec = new Rectangle2D.Double(x * rs, y * rs, rs, rs);
                            recs[x][y] = rec;
                            if (!newMaze.getMatrix()[x][y].isWay()) g2D.fill(rec);
                            g2D.setColor(Color.DARK_GRAY);
                            g2D.draw(rec);
                        }
                }
            };
            mainPanel.removeAll();
            mainPanel.add(newMazeCreator, BorderLayout.NORTH);
            JCheckBox drawOnMove = new JCheckBox("Draw on move");
            infoPanel.removeAll();
            infoPanel.add(drawOnMove);
            pack();
            repaint();
            MouseMotionListener mouseListener = new newMazeMouseListener(recs, newMaze);
            drawOnMove.addActionListener(a -> {
                if (drawOnMove.isSelected()) newMazeCreator.addMouseMotionListener(mouseListener);
                else newMazeCreator.removeMouseMotionListener(mouseListener);
            });
            newMazeCreator.addMouseListener((MouseListener) mouseListener);
        }

        private class newMazeMouseListener extends MouseAdapter {
            private Rectangle2D[][] recs;
            private Maze maze;

            public newMazeMouseListener(Rectangle2D[][] recs, Maze maze) {
                this.recs = recs;
                this.maze = maze;
                JButton saveButton = getSaveButton(maze);
                JButton resetButton = getResetButton(maze);
                infoPanel.add(saveButton);
                infoPanel.add(resetButton);
                infoPanel.repaint();
                pack();
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Point point = new Point(e.getPoint());
                for (int x = 0; x < maze.getMatrix().length; x++)
                    for (int y = 0; y < maze.getMatrix()[x].length; y++) {
                        if (recs[x][y].contains(point)) {
                            if (maze.getMatrix()[x][y].isWay()) maze.getMatrix()[x][y].setWay(false);
                            else maze.getMatrix()[x][y].setWay(true);
                        }
                    }
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point point = new Point(e.getPoint());
                for (int x = 0; x < maze.getMatrix().length; x++)
                    for (int y = 0; y < maze.getMatrix()[x].length; y++) {
                        if (recs[x][y].contains(point))
                            maze.getMatrix()[x][y].setWay(true);
                    }
                repaint();
            }
        }
    }

    private JButton getResetButton(Maze maze) {
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(actionEvent -> {
            for (Cell[] cells : maze.getMatrix())
                for (Cell cell : cells) {
                    cell.setWay(false);
                    repaint();
                }
        });
        return resetButton;
    }

    private JButton getSaveButton(Maze maze) {
        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(actionEvent -> {
            if (maze.isCorrect()) {
                maze.findNodes();
                mainPanel.removeAll();
                infoPanel.removeAll();
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                if (chooser.showSaveDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
                    String fileName = chooser.getSelectedFile().getName();
                    try {
                        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
                        out.writeObject(maze);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                reloadMaze(maze);
            } else {
                JOptionPane.showMessageDialog(null, "This is not correct maze");
            }
        });
        return saveButton;
    }
}