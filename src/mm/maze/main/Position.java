package mm.maze.main;

import mm.maze.cell.Directions;

import java.io.Serializable;

public class Position implements Serializable {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(Directions direction) {
        if (direction.equals(Directions.DOWN)) {
            this.y++;
        }
        if (direction.equals(Directions.UP)) {
            this.y--;
        }
        if (direction.equals(Directions.LEFT)) {
            this.x--;
        }
        if (direction.equals(Directions.RIGHT)) {
            this.x++;
        }
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    @Override
    public String toString() { return "\nP:[X=" + x + ", Y=" + y + "]";
    }
}
