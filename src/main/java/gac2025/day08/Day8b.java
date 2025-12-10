package gac2025.day08;

import gac2025.Base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8b extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("day08/input-b.txt");
        
        List<JunctionBox> boxes = parseInput(lines);
        
        List<PairWithDistance> allPairs = new ArrayList<>();
        for ( int i = 0; i < boxes.size(); i++ ) {
            JunctionBox boxA = boxes.get(i);
            for ( int j = i + 1; j < boxes.size(); j++ ) {
                JunctionBox boxB = boxes.get(j);
                allPairs.add(new PairWithDistance(boxA, boxB));
            }
        }

        allPairs.sort((p1, p2) -> Double.compare(p1.distance, p2.distance));

        int currentConnectionId = 1;
        boolean connectedWithTheSameConnectionId = false;
        long longestConnectionDistance = 0;
        for ( int i = 0; i < allPairs.size() && !connectedWithTheSameConnectionId; i++ ) {
            PairWithDistance pair = allPairs.get(i);
            JunctionBox boxA = pair.boxA;
            JunctionBox boxB = pair.boxB;
            
            if ( !canConnect(boxA, boxB) ) {
                continue;
            }
            
            if ( boxA.connectionId == 0 && boxB.connectionId == 0 ) {
                boxA.connectionId = currentConnectionId;
                boxB.connectionId = currentConnectionId;
                currentConnectionId++;
            }
            else if ( boxA.connectionId != 0 && boxB.connectionId == 0 ) {
                boxB.connectionId = boxA.connectionId;
            }
            else if ( boxA.connectionId == 0 && boxB.connectionId != 0 ) {
                boxA.connectionId = boxB.connectionId;
            }
            else {
                int oldId = boxB.connectionId;
                int newId = boxA.connectionId;
                for ( JunctionBox box : boxes ) {
                    if ( box.connectionId == oldId ) {
                        box.connectionId = newId;
                    }
                }
            }

            connectedWithTheSameConnectionId = checkIfAnyBoxesConnectedWithTheSameConnectionId(boxes);
            if ( connectedWithTheSameConnectionId ) {
                longestConnectionDistance = boxA.x * boxB.x;
            }
        }
        
        System.err.println("all connected: " + connectedWithTheSameConnectionId);
        System.out.println("Result: " + longestConnectionDistance);
    }
    
    public static void main(String[] args) {
        Day8b solution = new Day8b();
        solution.solve();
    }

    private boolean checkIfAnyBoxesConnectedWithTheSameConnectionId(List<JunctionBox> boxes) {
        Map<Integer, Integer> connectionIdCounts = new HashMap<>();
        for (JunctionBox box : boxes) {
            if (box.connectionId != 0) {
                connectionIdCounts.putIfAbsent(box.connectionId, 0);
                connectionIdCounts.put(box.connectionId, connectionIdCounts.get(box.connectionId) + 1);
            }
            else {
                return false;
            }
        }
        return connectionIdCounts.size() == 1;
    }

    private boolean canConnect(JunctionBox a, JunctionBox b) {
        if ( a.connectionId == 0 || b.connectionId == 0 ) {
            return true;
        }
        if ( a.connectionId == b.connectionId ) {
            return false;
        }
        return true;
    }

    private double calculateDistance(JunctionBox a, JunctionBox b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        double dz = a.z - b.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    private List<JunctionBox> parseInput(List<String> lines) {
        return lines.stream().map(line -> {
            String[] parts = line.split(",");
            long x = Long.parseLong(parts[0]);
            long y = Long.parseLong(parts[1]);
            long z = Long.parseLong(parts[2]);
            return new JunctionBox(x, y, z);
        }).toList();
    }

    private class PairWithDistance {
        JunctionBox boxA;
        JunctionBox boxB;
        double distance;

        public PairWithDistance(JunctionBox boxABox, JunctionBox boxB) {
            this.boxA = boxABox;
            this.boxB = boxB;
            this.distance = calculateDistance(boxA, boxB);
        }

        @Override
        public String toString() {
            return "PairWithDistance{" +
                    "boxA=" + boxA +
                    ", boxB=" + boxB +
                    ", distance=" + distance +
                    '}';
        }
    }

    private class JunctionBox {
        long x;
        long y;
        long z;
        int connectionId;

        public JunctionBox(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public String toString() {
            return "JunctionBox{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    ", connectionId=" + connectionId +
                    '}';
        }   
    }
}
