package gac2025.day02;

import gac2025.Base;

public class Day2a extends Base {
    
    @Override
    public void solve() {
        long result = 0;
        String[] ranges = readFile("gac2025/day02/input-a.txt").split(",");
        for (String range : ranges) {
            String[] bounds = range.split("-");
            long start = Long.parseLong(bounds[0]);
            long end = Long.parseLong(bounds[1]);
            for (long i = start; i <= end; i++) {
                String number = String.valueOf(i);
                if ( number.length() % 2 == 0 ){
                    String firstHalf = number.substring(0, number.length() / 2);
                    if ( number.endsWith(firstHalf) ){
                        result += i;
                    }
                }
            }
        }
        System.out.println(result);
    }
    
    public static void main(String[] args) {
        Day2a solution = new Day2a();
        solution.solve();
    }
}
