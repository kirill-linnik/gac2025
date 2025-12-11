package gac2025.day11;

import java.util.ArrayList;
import java.util.List;

import gac2025.Base;

public class Day11b extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("day11/input-b.txt");
        
        List<Rack> racks = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            if ( parts.length < 2 ) {
                System.err.println("Invalid line: " + line);
                continue;
            }

            // handle first part
            String rackName = parts[0].substring(0, parts[0].length() - 1); // "you:" -> "you"
            Rack rack = findRackByName(racks, rackName);
            if ( rack == null ) {
                rack = new Rack(rackName);
                racks.add(rack);
            }
            
            // handle remaining parts
            for ( int i = 1; i < parts.length; i++ ) {
                String connectedRackNName = parts[i];
                Rack connectedRack = findRackByName(racks, connectedRackNName);
                if ( connectedRack == null ) {
                    connectedRack = new Rack(connectedRackNName);
                    racks.add(connectedRack);
                }
                rack.connectedRacks.add(connectedRack);
            }
        }

        Rack svrRack = findRackByName(racks, "svr");
        Rack outRack = findRackByName(racks, "out");
        if ( svrRack == null || outRack == null ) {
            System.err.println("Missing 'svr' or 'out' rack");
            return;
        }

        List<List<Rack>> allPaths = findPaths(svrRack, outRack);
        System.out.println("Total paths from 'svr' to 'out': " + allPaths.size());

        int result = 0;
        for ( List<Rack> path : allPaths ) {
            boolean hasFftRack = path.stream().anyMatch(rack -> rack.name.equals("fft"));
            boolean hasDacRack = path.stream().anyMatch(rack -> rack.name.equals("dac"));
            if ( hasFftRack && hasDacRack ) {
                result++;
            }
        }
        System.out.println("Result (paths including 'fft' and 'dac'): " + result);
    }
    
    public static void main(String[] args) {
        Day11b solution = new Day11b();
        solution.solve();
    }

    private List<List<Rack>> findPaths(Rack start, Rack end) {
        List<List<Rack>> result = new ArrayList<>();
        findPathsHelper(start, end, new ArrayList<>(), result);
        return result;
    }

    private void findPathsHelper(Rack current, Rack target, List<Rack> path, List<List<Rack>> result) {
        path.add(current);
        if (current.equals(target)) {
            result.add(new ArrayList<>(path));
        } 
        else {
            for (Rack neighbor : current.connectedRacks) {
                if (!path.contains(neighbor)) {
                    findPathsHelper(neighbor, target, path, result);
                }
            }
        }
        path.remove(path.size() - 1);
    }

    private Rack findRackByName(List<Rack> racks, String name) {
        for ( Rack rack : racks ) {
            if ( rack.name.equals(name) ) {
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
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(name).append("->");
            for (Rack connectedRack : connectedRacks) {
                sb.append(connectedRack);
            }
            return sb.toString();
        }
    }
}
