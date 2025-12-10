package gac2025.day01;

import gac2025.Base;
import java.util.List;

public class Day1a extends Base {

    private int currentPosition = 50;
    
    @Override
    public void solve() {
        List<String> lines = readLines("day01/input-a.txt");
        
        int zeroCount = 0;
        for (String line : lines) {
            char command = line.charAt(0);
            int value = Integer.parseInt(line.substring(1));
            if (command == 'L') {
                currentPosition -= value;
            } else if (command == 'R') {
                currentPosition += value;
            }
            while ( currentPosition < 0 ) {
                currentPosition += 100;
            } 
            while ( currentPosition >= 100 ) {
                currentPosition -= 100;
            }
            if ( currentPosition == 0 ) {
                zeroCount++;
            }
        }
        System.out.println("Final Position: " + currentPosition);
        System.out.println("Zero Count: " + zeroCount);
    }
    
    public static void main(String[] args) {
        Day1a solution = new Day1a();
        solution.solve();
    }
}
