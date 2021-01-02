package mm.maze.gui;

import mm.maze.cell.Cell;
import mm.maze.cell.Directions;
import mm.maze.main.Maze;
import mm.maze.main.MazeConfig;
import mm.maze.main.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PlayMazeComponent extends MazeComponent {
    private int levelNumber;
    private Cell[][] matrix;
    private InputMap imap;
    Maze maze;
    Position playerPosition, finish;
    long time;
    MazeFrame frame;
    int stepCounter;


    public PlayMazeComponent(Maze maze, MazeFrame frame) {
        super(maze);
        this.maze = maze;
        this.matrix = this.maze.getMatrix();
        this.frame = frame;
        this.levelNumber = frame.levelNumber;

        stepCounter = 0;
        time = System.currentTimeMillis();
        playerPosition = maze.findStartPoints().iterator().next(); //todo getStartPoint()
        finish = maze.findEndPoints().iterator().next();           //todo getFinishLine()

        imap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW); //?

        imap.put(KeyStroke.getKeyStroke("DOWN"), "goDown");
        imap.put(KeyStroke.getKeyStroke("UP"), "goUp");
        imap.put(KeyStroke.getKeyStroke("RIGHT"), "goRight");
        imap.put(KeyStroke.getKeyStroke("LEFT"), "goLeft");

        ActionMap amap = getActionMap();

        Action downAction = new moveAction(Directions.DOWN);
        Action upAction = new moveAction(Directions.UP);
        Action rightAction = new moveAction(Directions.RIGHT);
        Action leftAction = new moveAction(Directions.LEFT);

        amap.put("goDown", downAction);
        amap.put("goUp", upAction);
        amap.put("goRight", rightAction);
        amap.put("goLeft", leftAction);

        if (frame.playGame) frame.infoTextArena.setText("Go to finish line as quick as You can!\n" +
                "Take as few steps as possible to get more points! ");
    }

    public PlayMazeComponent(int levelNumber, MazeFrame frame) {
        this(MazeConfig.LEVELS.get(levelNumber).getMaze(), frame);
        frame.infoPanel.remove(frame.buttons.get("CreateButton"));
        frame.infoPanel.add(frame.buttons.get("QuitGameButton"));
        this.levelNumber = levelNumber;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        int rs = maze.getSize().getRectangularSize();
        g2D.drawImage(Graphix.chessboard, finish.getX() * rs, finish.getY() * rs, rs, rs, null);
        g2D.drawImage(Graphix.playerImage, playerPosition.getX() * rs, playerPosition.getY() * rs, rs, rs, null);
    }

    private class moveAction extends AbstractAction {

        public moveAction(Directions direction) {
            putValue("direction", direction);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Directions direction = (Directions) getValue("direction");

            int x = playerPosition.getX();
            int y = playerPosition.getY();

            if ((direction.equals(Directions.DOWN)) && (y < matrix[x].length - 1)) y++;
            else if ((direction.equals(Directions.UP)) && (y > 0)) y--;
            else if ((direction.equals(Directions.RIGHT)) && (x < matrix.length - 1)) x++;
            else if ((direction.equals(Directions.LEFT)) && (x > 0)) x--;

            if (matrix[x][y].isWay()) {
                playerPosition.setY(y);
                playerPosition.setX(x);
                stepCounter++;
                frame.repaint();

                if (playerPosition.equals(finish)) {
                    imap.clear();
                    time = System.currentTimeMillis() - time;
                    int timeInSeconds = (int) (time / 1000);
                    int score = MazeConfig.LEVELS.get(levelNumber).getMaxPoints() - timeInSeconds - stepCounter;
                    if (score < 0) score = 0;
                    frame.totalScore = (score > 0) ? frame.totalScore + score : frame.totalScore;

                    if (frame.playGame) {
                        frame.infoTextArena.setText("Your time is : " + timeInSeconds + " seconds\n" +
                                "You took " + stepCounter + " steps\n" +
                                "Your score in this level is " + score + " points\n" +
                                "Your total score is " + frame.totalScore + " points");
                        if (levelNumber >= 0 && levelNumber < 5) {
                            frame.levelNumber++;
                            JOptionPane.showMessageDialog(null, "Press PLAY to start next level");
                        }
                        if (levelNumber == 5) JOptionPane.showMessageDialog(null, "Congratulation!\n" +
                                "Your score is " + frame.totalScore);
                    }
                }
            }
        }
    }
}
