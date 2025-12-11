package gac2025.day11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gac2025.Base;

public class Day11b extends Base {

    @Override
    public void solve() {
        List<String> lines = readLines("day11/input-b.txt");

        List<Rack> racks = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            if (parts.length < 2) {
                System.err.println("Invalid line: " + line);
                continue;
            }

            // handle first part
            String rackName = parts[0].substring(0, parts[0].length() - 1); // "you:" -> "you"
            Rack rack = findRackByName(racks, rackName);
            if (rack == null) {
                rack = new Rack(rackName);
                racks.add(rack);
            }

            // handle remaining parts
            for (int i = 1; i < parts.length; i++) {
                String connectedRackNName = parts[i];
                Rack connectedRack = findRackByName(racks, connectedRackNName);
                if (connectedRack == null) {
                    connectedRack = new Rack(connectedRackNName);
                    racks.add(connectedRack);
                }
                rack.connectedRacks.add(connectedRack);
            }
        }

        Rack svrRack = findRackByName(racks, "svr");
        Rack outRack = findRackByName(racks, "out");
        Rack fftRack = findRackByName(racks, "fft");
        Rack dacRack = findRackByName(racks, "dac");
        if (svrRack == null || outRack == null || fftRack == null || dacRack == null) {
            System.err.println("Missing 'svr', 'out', 'fft', or 'dac' rack");
            return;
        }

        long result = countPaths(svrRack, outRack, fftRack, dacRack);
        System.out.println("Result (paths including 'fft' and 'dac'): " + result);
    }

    public static void main(String[] args) {
        Day11b solution = new Day11b();
        solution.solve();
    }

    private long countPaths(Rack start, Rack end, Rack fft, Rack dac) {
        Map<String, Long> memo = new HashMap<>();
        Set<Rack> visited = new HashSet<>();
        return pathFinderHelper(start, end, fft, dac, false, false, visited, memo);
    }

    private long pathFinderHelper(Rack current, Rack target, Rack fft, Rack dac,
            boolean hasFft, boolean hasDac, Set<Rack> visited, Map<String, Long> memo) {
        // Update state based on current node
        if (current.equals(fft)) {
            hasFft = true;
        }
        if (current.equals(dac)) {
            hasDac = true;
        }

        // Base case: reached target
        if (current.equals(target)) {
            return (hasFft && hasDac) ? 1 : 0;
        }

        // Check memo (only if not in current path - to avoid cycles)
        String key = getMemoKey(current, hasFft, hasDac);
        if (!visited.contains(current) && memo.containsKey(key)) {
            return memo.get(key);
        }

        visited.add(current);
        long count = 0;

        for (Rack neighbor : current.connectedRacks) {
            if (!visited.contains(neighbor)) {
                count += pathFinderHelper(neighbor, target, fft, dac, hasFft, hasDac, visited, memo);
            }
        }

        visited.remove(current);
        memo.put(key, count);
        return count;
    }

    private String getMemoKey(Rack rack, boolean hasFft, boolean hasDac) {
        return rack.name + ":" + hasFft + ":" + hasDac;
    }

    private Rack findRackByName(List<Rack> racks, String name) {
        for (Rack rack : racks) {
            if (rack.name.equals(name)) {
                return rack;
            }
        }
        return null;
    }

    private class Rack {

        String name;
        List<Rack> connectedRacks = new ArrayList<>();

        public Rack(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Rack other = (Rack) obj;
            return name.equals(other.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
}
