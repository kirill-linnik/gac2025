package gac2025.day05;

import gac2025.Base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day5b extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("gac2025/day05/input-b.txt");
        
        List<Range> ranges = new ArrayList<>();
        for (String line : lines) {
            if ( line.indexOf("-") > 0) {
                String parts[] = line.split("-");
                long start = Long.parseLong(parts[0]);
                long end = Long.parseLong(parts[1]);
                Range range = new Range(start, end);
                ranges.add(range);
            }
            else {
                break;
            }
        }

        ranges.sort((r1, r2) -> Long.compare(r1.getStart(), r2.getStart()));

        for (int i = 1; i < ranges.size(); i++) {
            Range previous = ranges.get(i - 1);
            Range current = ranges.get(i);
            if ( current.getStart() <= previous.getEnd() + 1) {
                long newEnd = Math.max(previous.getEnd(), current.getEnd());
                Range merged = new Range(previous.getStart(), newEnd);
                ranges.set(i - 1, merged);
                ranges.remove(i);
                i--;
            }
        }

        long result = ranges.stream()
                .mapToLong(range -> range.getEnd() - range.getStart() + 1)
                .sum();
        System.out.println("Result: " + result);
    }
    
    public static void main(String[] args) {
        Day5b solution = new Day5b();
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
