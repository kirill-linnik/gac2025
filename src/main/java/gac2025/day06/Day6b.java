package gac2025.day06;

import gac2025.Base;

import java.util.ArrayList;
import java.util.List;

public class Day6b extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("day06/input-b.txt");
        
        long result = 0;
        
        String lastLine = lines.get(lines.size() - 1);
        char[] lastLineChars = lastLine.toCharArray();
        
        for ( int i = 0; i < lastLineChars.length; i++ ) {
            char ch = lastLineChars[i];
            if ( ch == ' ' ){
                continue;
            }

            int columnStart = i;
            int columnEnd = i;
            for ( int j = i + 1; j < lastLineChars.length; j++ ) {
                char ch2 = lastLineChars[j];
                if ( ch2 == ' ' ){
                    columnEnd = j;
                }
                else {
                    break;
                }
            }
            
            List<Long> columnNumbers = new ArrayList<>();
            for ( int j = columnEnd; j >= columnStart; j-- ) {
                StringBuilder sb = new StringBuilder();
                for ( int k = 0; k < lines.size()-1; k++ ) {
                    String line = lines.get(k);
                    char ch1 = line.charAt(j);
                    if ( ch1 == ' ' ){
                        continue;
                    }
                    sb.append(ch1);
                }
                if ( sb.length() > 0 ){
                    Long number = Long.valueOf(sb.toString());
                    columnNumbers.add(number);
                }
            }
            
            if ( ch == '+' ){
                long tempResult = columnNumbers.stream().mapToLong(Long::longValue).sum();
                result += tempResult;
            }
            else if ( ch == '*' ){
                long tempResult = columnNumbers.stream().reduce(1L, (a, b) -> a * b);
                result += tempResult;
            }

            i = columnEnd;
        }

        System.err.println("Result: " + result);
    }
        
    
    public static void main(String[] args) {
        Day6b solution = new Day6b();
        solution.solve();
    }
}
