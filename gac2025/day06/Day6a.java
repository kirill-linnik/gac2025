package gac2025.day06;

import gac2025.Base;

import java.util.ArrayList;
import java.util.List;

public class Day6a extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("gac2025/day06/input-a.txt");
        
        List<List<Long>> input = new ArrayList<>();
        long result = 0;
        for (String line : lines) {
            String[] parts = line.split(" ");
            int index = 0;
            for (String part : parts) {
                if ( part.equals("") ){
                    continue;
                }
                try {
                    Long number = Long.valueOf(part);
                    if (input.size() <= index) {
                        input.add(new ArrayList<>());
                    }
                    input.get(index).add(number);
                } catch (NumberFormatException e) {
                    long tempResult = 0;
                    if ( part.equals("+") ){
                        tempResult = input.get(index).stream().mapToLong(Long::longValue).sum();
                    }
                    else if ( part.equals("*") ){
                        tempResult = input.get(index).stream().reduce(1L, (a, b) -> a * b);
                    }
                    result += tempResult;
                }
                index++;
            }
        }

        System.err.println("Result: " + result);
    }
    
    public static void main(String[] args) {
        Day6a solution = new Day6a();
        solution.solve();
    }
}
