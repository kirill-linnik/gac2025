package gac2025.day01;

import gac2025.Base;
import java.util.List;

public class Day1b extends Base {
    
    private int currentPosition = 50;
    
    @Override
    public void solve() {
        List<String> lines = readLines("day01/input-b.txt");
        
        int zeroCount = 0;
        for (String line : lines) {
            char command = line.charAt(0);
            int value = Integer.parseInt(line.substring(1));
            int nextPosition = command == 'L' ? currentPosition - value : currentPosition + value;
            boolean zeroStart = currentPosition == 0;
            while ( nextPosition < 0 ) {
                nextPosition += 100;
                if ( zeroStart ) {
                    zeroStart = false;
                }
                else {
                    zeroCount++;
                }
            }
            while ( nextPosition >= 100 ) {
                nextPosition -= 100;
                zeroCount++;
                if ( nextPosition == 0 ) {
                    zeroCount--;
                }
            }
            if ( nextPosition == 0 ) {
                zeroCount++;
            }
            currentPosition = nextPosition;
        }
        System.out.println("Final Position: " + currentPosition);
        System.out.println("Zero Count: " + zeroCount);
    }
    
    public static void main(String[] args) {
        Day1b solution = new Day1b();
        solution.solve();
    }
}
