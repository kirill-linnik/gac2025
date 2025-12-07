package gac2025.day07;

import gac2025.Base;
import java.util.List;

public class Day7a extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("gac2025/day07/input-a.txt");
        
        char[][] grid = new char[lines.size()][lines.get(0).length()];
        for ( int i = 0; i < lines.size(); i++ ) {
            grid[i] = lines.get(i).toCharArray();
        }

        int splitterCount = 0;
        int startingPoint = lines.get(0).indexOf('S');
        for ( int i = 1; i < grid.length; i++ ) {
            if ( i == 1 ){
                if ( grid[i+1][startingPoint] == '.' ){
                    grid[i+1][startingPoint] = '|';
                }
                else if ( grid[i+1][startingPoint] == '^' ){
                    splitterCount++;
                    if ( startingPoint - 1 >= 0 ){
                        grid[i+1][startingPoint-1] = '|';
                    }
                    if ( startingPoint + 1 < grid[i+1].length ){
                        grid[i+1][startingPoint+1] = '|';
                    }
                }
            }
            else {
                for ( int j = 0; j < grid[i].length; j++ ) {
                    if ( grid[i][j] == '|' ){
                        if ( i + 1 < grid.length ){
                            if ( grid[i+1][j] == '.' ){
                                grid[i+1][j] = '|';
                            }
                            else if ( grid[i+1][j] == '^' ){
                                splitterCount++;
                                if ( j - 1 >= 0 ){
                                    grid[i+1][j-1] = '|';
                                }
                                if ( j + 1 < grid[i+1].length ){
                                    grid[i+1][j+1] = '|';
                                }
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Result: " + splitterCount);
    }
    
    public static void main(String[] args) {
        Day7a solution = new Day7a();
        solution.solve();
    }
}
