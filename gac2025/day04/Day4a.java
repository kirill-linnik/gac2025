package gac2025.day04;

import gac2025.Base;
import java.util.List;

public class Day4a extends Base {

    private static final char PAPER = '@';
    private static final char MARKED_PAPER = 'X';
    
    @Override
    public void solve() {
        List<String> lines = readLines("gac2025/day04/input-a.txt");
        
        char[][] positions = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            positions[i] = lines.get(i).toCharArray();
        }

        int result = 0;
        for (int i = 0; i < positions.length; i++){
            for (int j = 0; j < positions[i].length; j++) {
                if ( positions[i][j] != PAPER) {
                    continue;
                }
                short numberOfPapersAround = getNumberOfPapersAround(positions, i, j);
                if (numberOfPapersAround < 4) {
                    result++;
                    positions[i][j] = MARKED_PAPER;
                }
            }
        }
        System.out.println("Result: " + result);
        for (int i = 0; i < positions.length; i++) {
            System.out.println(positions[i]);
        }
    }

    private short getNumberOfPapersAround(char[][] positions, int row, int col) {
        short count = 0;
        for ( int i = Math.max(row-1, 0); i <= Math.min(row+1, positions.length-1); i++) {
            for ( int j = Math.max(col-1, 0); j <= Math.min(col+1, positions[i].length-1); j++) {
                if (i == row && j == col) {
                    continue;
                }
                char positionChar = positions[i][j];
                if (positionChar == PAPER || positionChar == MARKED_PAPER) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public static void main(String[] args) {
        Day4a solution = new Day4a();
        solution.solve();
    }
}
