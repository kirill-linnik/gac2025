package gac2025.day04;

import gac2025.Base;
import java.util.List;

public class Day4b extends Base {
    
    private static final char PAPER = '@';
    private static final char MARKED_PAPER = 'X';
    
    @Override
    public void solve() {
        List<String> lines = readLines("day04/input-b.txt");
        
        char[][] positions = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            positions[i] = lines.get(i).toCharArray();
        }

        int totalResult = 0;
        int result = 0;
        do {
            result = 0;
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
            totalResult += result;
        }
        while (result > 0);
        
        System.out.println("Result: " + totalResult);
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
                if (positionChar == PAPER ) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public static void main(String[] args) {
        Day4b solution = new Day4b();
        solution.solve();
    }
}
