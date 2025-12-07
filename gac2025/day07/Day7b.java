package gac2025.day07;

import gac2025.Base;

import java.util.List;

public class Day7b extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("gac2025/day07/input-b.txt");
        
        char[][] grid = new char[lines.size()][lines.get(0).length()];
        for ( int i = 0; i < lines.size(); i++ ) {
            grid[i] = lines.get(i).toCharArray();
        }

        int startingPoint = lines.get(0).indexOf('S');
        int lineCount = lines.size();
        long[][] timelineHeatmap = new long[lineCount][lines.get(0).length()];
        for ( int i = 0; i < grid.length; i++ ) {
            if ( i == 0 ){
                if ( grid[i+1][startingPoint] == '.' ){
                    grid[i+1][startingPoint] = '|';
                    timelineHeatmap[i+1][startingPoint] += 1;
                }
                else if ( grid[i+1][startingPoint] == '^' ){
                    if ( startingPoint - 1 >= 0 ){
                        grid[i+1][startingPoint-1] = '|';
                        timelineHeatmap[i+1][startingPoint-1] += 1;
                    }
                    if ( startingPoint + 1 < grid[i+1].length ){
                        grid[i+1][startingPoint+1] = '|';
                        timelineHeatmap[i+1][startingPoint+1] += 1;
                    }
                }
            }
            else {
                for ( int j = 0; j < grid[i].length; j++ ) {
                    if ( grid[i][j] == '|' ){
                        long currentCount = timelineHeatmap[i][j];
                        if ( i + 1 < grid.length ){
                            if ( grid[i+1][j] == '^' ){
                                if ( j - 1 >= 0  ){
                                    grid[i+1][j-1] = '|';
                                    timelineHeatmap[i+1][j-1] += currentCount;
                                }
                                if ( j + 1 < grid[i+1].length ){
                                    grid[i+1][j+1] = '|';
                                    timelineHeatmap[i+1][j+1] += currentCount;
                                }
                            }
                            else {
                                grid[i+1][j] = '|';
                                timelineHeatmap[i+1][j] += currentCount;
                            }
                        }
                    }
                }
            }
        }
        
        long result = 0;
        for ( int i = 0; i < timelineHeatmap[lineCount - 1].length; i++ ) {
           result += timelineHeatmap[lineCount - 1][i];
        }

        
        System.out.println("Result: " + result);
    }
    
    public static void main(String[] args) {
        Day7b solution = new Day7b();
        solution.solve();
    }
}
