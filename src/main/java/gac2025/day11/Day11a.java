package gac2025.day11;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gac2025.Base;

public class Day11a extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("day11/input-a.txt");
        
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

        int result = getNumberOfPathsFromYouToOut(racks);
        System.out.println("Result: " + result);
    }

    private int getNumberOfPathsFromYouToOut(List<Rack> racks) {
        Rack youRack = findRackByName(racks, "you");
        Rack outRack = findRackByName(racks, "out");
        if ( youRack == null || outRack == null ) {
            System.err.println("Missing 'you' or 'out' rack");
            return 0;
        }
        
        Set<String> visited = new HashSet<>();
        return countPaths(youRack, outRack, visited);
    }

    private int countPaths(Rack current, Rack target, Set<String> visited) {
        if ( current.name.equals(target.name) ) {
            return 1;
        }
        
        visited.add(current.name);
        int pathCount = 0;
        for ( Rack neighbor : current.connectedRacks ) {
            if ( !visited.contains(neighbor.name) ) {
                pathCount += countPaths(neighbor, target, visited);
            }
        }
        visited.remove(current.name);
        return pathCount;
    }
    
    public static void main(String[] args) {
        Day11a solution = new Day11a();
        solution.solve();
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
