package gac2025.day09;

import gac2025.Base;
import java.util.List;

public class Day9b extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("gac2025/day09/input-b.txt");
        
        long[][] coords = parseInput(lines);
        long maxSquare = Long.MIN_VALUE;

        for ( int i = 0; i < coords.length; i++ ) {
            long x1 = coords[i][0];
            long y1 = coords[i][1];
            for ( int j = i + 1; j < coords.length; j++ ) {
                long x2 = coords[j][0];
                long y2 = coords[j][1];
                long dx = Math.abs(x1 - x2) + 1;
                long dy = Math.abs(y1 - y2) + 1;
                long square = dx * dy;
                if ( square > maxSquare ) {
                    maxSquare = square;
                }
            }
        }
        System.out.println("Maximum square: " + maxSquare);
    }

    private long[][] parseInput(List<String> lines) {
        long[][] coords = new long[lines.size()][2];
        for ( int i = 0; i < lines.size(); i++ ) {
            String line = lines.get(i);
            String[] parts = line.split(",");
            coords[i][0] = Long.parseLong(parts[0].trim());
            coords[i][1] = Long.parseLong(parts[1].trim());
        }
        return coords;
    }
    
    public static void main(String[] args) {
        Day9b solution = new Day9b();
        solution.solve();
    }
}
