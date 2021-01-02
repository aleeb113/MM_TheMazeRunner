package mm.maze.cell;

public enum Directions {

    LEFT, RIGHT, UP, DOWN;

    public static Directions getOppositeDirection(Directions d) {

        switch (d) {
            case LEFT: {
                return RIGHT;
            }
            case RIGHT: {
                return LEFT;
            }
            case UP: {
                return DOWN;
            }
            case DOWN: {
                return UP;
            }
            default: {
                return null;
            }
        }
    }
}
