package mm.maze.gui;

import java.awt.*;

public class GraphicMazeSize {

    static final Dimension MAZE_COMPONENT_SIZE = new Dimension(1200, 700);
    private int recSize,hSize,vSize;

    public GraphicMazeSize(int horizontalSize, int verticalSize) {

        this.hSize = horizontalSize;
        this.vSize = verticalSize;
        setRecsSize(horizontalSize,verticalSize);
    }

    private void setRecsSize(int x, int y) {

        recSize = (int) (MAZE_COMPONENT_SIZE.getWidth() / x)-1;
        int tmpSize = (int) (MAZE_COMPONENT_SIZE.getHeight() / y)-1;
        if (tmpSize < recSize) recSize = tmpSize;
    }


    public int getRecSize() {
        return recSize;
    }

    public void setRecSize(int recSize) {
        this.recSize = recSize;
    }

    public int getHorizontalSize() {
        return hSize;
    }

    public void sethSize(int hSize) {
        this.hSize = hSize;
    }

    public int getVerticalSize() {
        return vSize;
    }

    public void setvSize(int vSize) {
        this.vSize = vSize;
    }
}


