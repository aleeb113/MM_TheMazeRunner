package mm.maze.main;

import java.io.Serializable;

public class Size implements Serializable {

    private int horizontalSize;
    private int verticalSize;
    private int rectangularSize ;

    Size(int x, int y) {
        this.horizontalSize = x;
        this.verticalSize = y;

    }

    private void setRecsSize(int x, int y) {

        rectangularSize = (int) (MazeConfig.MAZE_COMPONENT_SIZE.getWidth() / x)-1;
        int tmpSize = (int) (MazeConfig.MAZE_COMPONENT_SIZE.getHeight() / y)-1;
        if (tmpSize < rectangularSize) rectangularSize = tmpSize;
    }

    public int getRectangularSize() {
        setRecsSize(getHorizontalSize(),getVerticalSize());
        return rectangularSize;
    }

    public int getHorizontalSize() {
        return horizontalSize;
    }

    void setHorizontalSize(int horizontalSize) {
        this.horizontalSize = horizontalSize;
    }

    public int getVerticalSize() {
        return verticalSize;
    }

    void setVerticalSize(int verticalSize) {
        this.verticalSize = verticalSize;
    }


}
