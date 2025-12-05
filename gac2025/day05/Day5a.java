package gac2025.day05;

import gac2025.Base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day5a extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("gac2025/day05/input-a.txt");
        
        List<Range> ranges = new ArrayList<>();
        Set<Long> ids = new HashSet<>();
        for (String line : lines) {
            if ( line.indexOf("-") > 0) {
                String parts[] = line.split("-");
                long start = Long.parseLong(parts[0]);
                long end = Long.parseLong(parts[1]);
                Range range = new Range(start, end);
                ranges.add(range);
            }
            else {
                try {
                    long id = Long.parseLong(line);
                    ids.add(id);
                } catch (NumberFormatException e) {}
            }
        }

        long result = ids.stream()
                .filter(id -> ranges.stream().anyMatch(range -> id >= range.getStart() && id <= range.getEnd()))
                .count();
        System.out.println("Result: " + result);
    }
    
    public static void main(String[] args) {
        Day5a solution = new Day5a();
        solution.solve();
    }

    private class Range {
        private final long start;
        private final long end;

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public long getStart() {
            return start;
        }

        public long getEnd() {
            return end;
        }
    }
}
