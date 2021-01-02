package mm.maze.track;

import mm.maze.cell.Directions;
import mm.maze.main.Maze;
import mm.maze.main.Position;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Track {

    private int trackLength = 0;
    private final Set<Position> track;
    private final Maze maze;

    public Track(Position position, Maze maze) {
        this.trackLength = 1;
        this.track = new HashSet<>();
        this.maze = maze;

        this.track.add(position);
        go(position, Directions.DOWN);
    }

    private Track(Track track, Position position, Directions direction) throws InterruptedException {
        this.trackLength = track.getTrackLength();
        this.track = track.getTrackCopy();   //TODO clone?
        this.maze = track.getMaze();
        go(position, direction);


    }

    private void go(Position from, Directions direction) {

        Directions oppositeDirection = Directions.getOppositeDirection(direction);
        Position to = new Position(from.getX(), from.getY());
        to.update(direction);

        if (isAlreadyOnTrack(to)) {
            return;
        }

        this.trackLength++;
        this.track.add(to);

        if (isStartingPoint(to)) {
            return;
        }

        if (isEndpoint(to)) {
          //  this.maze.addTrackToWaysOut(this.track);
            return;
        }

        List<Directions> possibleDirections = this.maze.getMatrix()[to.getX()][to.getY()].getPossibleDirections();

        switch (possibleDirections.size()) {
            case 1: {
                return;
            }
            case 2: {
                if (possibleDirections.get(0).equals(oppositeDirection)) {
                    direction = possibleDirections.get(1);
                } else {
                    direction = possibleDirections.get(0);
                }
                go(new Position(to.getX(), to.getY()), direction);
            }
            default: {
                for (Directions dir : possibleDirections) {
                    if (!dir.equals(oppositeDirection)) {
                        try {
                            new Track(this, new Position(to.getX(), to.getY()), dir);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    private boolean isStartingPoint(Position position) {

        List<Position> startingPoints = this.maze.findStartPoints();
        for (Position p : startingPoints) {
            if (p.equals(position)) {
                return true;
            }
        }
        return false;
    }

    private boolean isEndpoint(Position position) {

        Set<Position> endpoints = this.maze.findEndPoints();
        for (Position p : endpoints) {
            if (p.equals(position)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAlreadyOnTrack(Position position) {
        for (Position p : this.track) {
            if (p.equals(position)) {
                return true;
            }
        }
        return false;
    }

    private int getTrackLength() {
        return trackLength;
    }

    private Set<Position> getTrackCopy() {
        Set<Position> trackCopy = new HashSet<>();
        for (Position p : this.track) {
            trackCopy.add(p);
        }
        return trackCopy;
    }

    private Maze getMaze() {
        return maze;
    }
}
