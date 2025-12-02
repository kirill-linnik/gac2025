package gac2025.day02;

import gac2025.Base;

public class Day2b extends Base {
    
    @Override
    public void solve() {
        long result = 0;
        String[] ranges = readFile("gac2025/day02/input-b.txt").split(",");
        for (String range : ranges) {
            String[] bounds = range.split("-");
            long start = Long.parseLong(bounds[0]);
            long end = Long.parseLong(bounds[1]);
            for (long i = start; i <= end; i++) {
                String number = String.valueOf(i);
                int numberLength = number.length();
                for ( int j = 1; j <= numberLength / 2; j++ ) {
                    if ( numberLength % j == 0 ) {
                        String segment = number.substring(0, j);
                        StringBuilder repeated = new StringBuilder();
                        int repeatCount = numberLength / j;
                        for ( int k = 0; k < repeatCount; k++ ) {
                            repeated.append(segment);
                        }
                        if ( repeated.toString().equals(number) ) {
                            result += i;
                            break;
                        }
                    }
                }
            }
        }
        System.out.println(result);
    }
    
    public static void main(String[] args) {
        Day2b solution = new Day2b();
        solution.solve();
    }
}
