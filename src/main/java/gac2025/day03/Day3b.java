package gac2025.day03;

import gac2025.Base;
import java.util.List;

public class Day3b extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("day03/input-b.txt");
        
        long totalVoltage = 0;
        for (String line : lines) {
            short currentIndex = 0;
            StringBuilder voltageBuilder = new StringBuilder();
            for ( int i = 0; i < 12; i++ ) {
                for ( int j = 9; j >= 0; j-- ) {
                    int index = line.indexOf(String.valueOf(j), currentIndex);
                    if ( index == -1 || index > line.length() - 12 + voltageBuilder.length() ) {
                        continue;
                    }
                    currentIndex = (short)(index + 1);
                    voltageBuilder.append(j);
                    break;
                }
            }
            totalVoltage += Long.parseLong(voltageBuilder.toString());
        }
        System.out.println(totalVoltage);
    }
    
    public static void main(String[] args) {
        Day3b solution = new Day3b();
        solution.solve();
    }
}
