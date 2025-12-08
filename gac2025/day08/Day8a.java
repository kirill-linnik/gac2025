package gac2025.day08;

import gac2025.Base;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8a extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("gac2025/day08/input-a.txt");
        
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
        int connectionsMade = 0;
        int maxConnections = 10;
        for ( PairWithDistance pair : allPairs ) {
            JunctionBox boxA = pair.boxA;
            JunctionBox boxB = pair.boxB;

            if ( !canConnect(boxA, boxB) ) {
                continue;
            }
            connectionsMade++;

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

            if ( connectionsMade >= maxConnections ) {
                break;
            }
        }
        

        Map<Integer, Long> connectionSizes = new HashMap<>();
        for (JunctionBox box : boxes) {
            if (box.connectionId == 0) {
                continue;
            }
            connectionSizes.putIfAbsent(box.connectionId, 0L);
            connectionSizes.put(box.connectionId, connectionSizes.get(box.connectionId) + 1);
        }

        long result = connectionSizes.values().stream().reduce(1L, (a, b) -> a * b);
        System.out.println("Result: " + result);
    }
    
    public static void main(String[] args) {
        Day8a solution = new Day8a();
        solution.solve();
    }

    private JunctionBox getClosestBox(List<JunctionBox> boxes, JunctionBox boxA) {
        JunctionBox closest = null;
        double minDistance = Double.MAX_VALUE;
            
        for ( JunctionBox boxB : boxes ) {
            if ( boxA.x == boxB.x && boxA.y == boxB.y && boxA.z == boxB.z ) {
                continue;
            }
            if ( !canConnect(boxA, boxB) ) {
                continue;
            }
            double distance = calculateDistance(boxA, boxB);
            if ( distance < minDistance ) {
                minDistance = distance;
                closest = boxB;
            }
        }
        return closest;
    }

    private boolean canConnect(JunctionBox a, JunctionBox b) {
        return !(a.connectionId != 0 && b.connectionId != 0);
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
    }
}
