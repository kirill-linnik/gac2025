package gac2025.day07;

import gac2025.Base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day7b extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("gac2025/day07/input-b.txt");
        
        char[][] grid = new char[lines.size()][lines.get(0).length()];
        for ( int i = 0; i < lines.size(); i++ ) {
            grid[i] = lines.get(i).toCharArray();
        }

        int startingPoint = lines.get(0).indexOf('S');
        Set<Timeline> timelines = new HashSet<>();
        timelines.add(new Timeline(new ArrayList<>(), 0, startingPoint));
        for ( int i = 0; i < grid.length; i++ ) {
            if ( i == 0 ){
                if ( grid[i+1][startingPoint] == '.' ){
                    grid[i+1][startingPoint] = '|';
                    addNewPositionsToTimelines(timelines, i, startingPoint, i+1, startingPoint);
                }
                else if ( grid[i+1][startingPoint] == '^' ){
                    if ( startingPoint - 1 >= 0 ){
                        grid[i+1][startingPoint-1] = '|';
                        addNewPositionsToTimelines(timelines, i, startingPoint, i+1, startingPoint-1);
                    }
                    if ( startingPoint + 1 < grid[i+1].length ){
                        grid[i+1][startingPoint+1] = '|';
                        addNewPositionsToTimelines(timelines, i, startingPoint, i+1, startingPoint+1);
                    }
                }
            }
            else {
                for ( int j = 0; j < grid[i].length; j++ ) {
                    if ( grid[i][j] == '|' ){
                        if ( i + 1 < grid.length ){
                            if ( grid[i+1][j] == '^' ){
                                if ( j - 1 >= 0  ){
                                    grid[i+1][j-1] = '|';
                                    addNewPositionsToTimelines(timelines, i, j, i+1, j-1);
                                }
                                if ( j + 1 < grid[i+1].length ){
                                    grid[i+1][j+1] = '|';
                                    addNewPositionsToTimelines(timelines, i, j, i+1, j+1);
                                }
                            }
                            else {
                                grid[i+1][j] = '|';
                                addNewPositionsToTimelines(timelines, i, j, i+1, j);
                            }
                        }
                    }
                }
            }
        }
        Set<Timeline> timlinesAtTheLastRow = timelines.stream()
                .filter(timeline -> timeline.currentPosition.getX() == grid.length - 1)
                .collect(Collectors.toSet());

        System.out.println("Timelines at the last row: " + timlinesAtTheLastRow.size());
    }
    
    public static void main(String[] args) {
        Day7b solution = new Day7b();
        solution.solve();
    }

    private void addNewPositionsToTimelines(Set<Timeline> timelines, int oldX, int oldY, int newX, int newY) {
        Set<Timeline> timelinesAtPosition = getAllTimelinesAtPosition(timelines, oldX, oldY);
        for ( Timeline timeline : timelinesAtPosition ) {
            Timeline newTimeline = new Timeline(timeline.path, newX, newY);
            timelines.add(newTimeline);
        }
    }

    private Set<Timeline> getAllTimelinesAtPosition(Set<Timeline> timelines, int x, int y) {
        Set<Timeline> result = new HashSet<>();
        for ( Timeline timeline : timelines ) {
            Position pos = timeline.currentPosition;
            if ( pos.getX() == x && pos.getY() == y ){
                result.add(timeline);
            }
        }
        return result;
    }

    private class Position {
        private final int x;
        private final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    private class Timeline {
        List<Position> path;
        Position currentPosition;

        public Timeline(List<Position> path, int startX, int startY) {
            this.path = new ArrayList<>(path);
            this.path.add(new Position(startX, startY));
            this.currentPosition = new Position(startX, startY);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for ( Position pos : path ) {
                sb.append("(").append(pos.getX()).append(",").append(pos.getY()).append(")");
            }
            return sb.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if ( this == obj ) return true;
            if ( obj == null || getClass() != obj.getClass() ) return false;
            Timeline other = (Timeline) obj;
            return this.toString().equals(other.toString());
        }

        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
    }
}
