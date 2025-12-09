package gac2025.day09;

import gac2025.Base;

import java.util.*;

public class Day9b extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("gac2025/day09/input-b.txt");

        System.out.println("Number of lines: " + lines.size());
        
        // Parse points
        List<Point> points = new ArrayList<>();
        TreeSet<Integer> allX = new TreeSet<>();
        TreeSet<Integer> allY = new TreeSet<>();
        
        for (String line : lines) {
            String[] parts = line.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            points.add(new Point(x, y));
            allX.add(x);
            allY.add(y);
        }

        System.out.println("Points parsed: " + points.size());

        // Coordinate compression: map actual coordinates to compressed indices
        List<Integer> xCoords = new ArrayList<>(allX);
        List<Integer> yCoords = new ArrayList<>(allY);
        Map<Integer, Integer> xToIndex = new HashMap<>();
        Map<Integer, Integer> yToIndex = new HashMap<>();
        
        for (int i = 0; i < xCoords.size(); i++) {
            xToIndex.put(xCoords.get(i), i);
        }
        for (int i = 0; i < yCoords.size(); i++) {
            yToIndex.put(yCoords.get(i), i);
        }
        
        // Compress points
        for (Point p : points) {
            p.x = xToIndex.get(p.x);
            p.y = yToIndex.get(p.y);
        }
        
        System.out.println("Coordinates compressed to grid: " + xCoords.size() + "x" + yCoords.size());

        // Build spatial indexes with compressed coordinates
        Map<Integer, TreeSet<Integer>> pointsByX = new HashMap<>();
        Map<Integer, TreeSet<Integer>> pointsByY = new HashMap<>();
        
        for (Point p : points) {
            pointsByX.computeIfAbsent(p.x, k -> new TreeSet<>()).add(p.y);
            pointsByY.computeIfAbsent(p.y, k -> new TreeSet<>()).add(p.x);
        }

        // Build compact grid
        int gridWidth = xCoords.size();
        int gridHeight = yCoords.size();
        boolean[][] grid = new boolean[gridHeight][gridWidth];

        System.out.println("Building grid...");

        // Draw segments between adjacent points
        for (Point p : points) {
            TreeSet<Integer> ysAtX = pointsByX.get(p.x);
            Integer nextY = ysAtX.higher(p.y);
            if (nextY != null) {
                for (int y = p.y; y <= nextY; y++) {
                    grid[y][p.x] = true;
                }
            }
            
            TreeSet<Integer> xsAtY = pointsByY.get(p.y);
            Integer nextX = xsAtY.higher(p.x);
            if (nextX != null) {
                for (int x = p.x; x <= nextX; x++) {
                    grid[p.y][x] = true;
                }
            }
        }

        System.out.println("Perimeter drawn, filling interior...");

        // Fill interior - a point is inside if blocked in all 4 directions
        for (int y = 0; y < gridHeight; y++) {
            for (int x = 0; x < gridWidth; x++) {
                if (!grid[y][x] && isEnclosed(grid, x, y, gridWidth, gridHeight)) {
                    grid[y][x] = true;
                }
            }
        }

        System.out.println("Grid filled, checking rectangles...");

        // Generate and check rectangles
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                rectangles.add(new Rectangle(points.get(i), points.get(j)));
            }
        }
        
        rectangles.sort((r1, r2) -> {
            // Sort by real area in original coordinates
            Point p1 = r1.p1, p2 = r1.p2;
            int origX1_1 = xCoords.get(p1.x), origY1_1 = yCoords.get(p1.y);
            int origX2_1 = xCoords.get(p2.x), origY2_1 = yCoords.get(p2.y);
            long dx1 = Math.abs(origX1_1 - origX2_1) + 1;
            long dy1 = Math.abs(origY1_1 - origY2_1) + 1;
            long area1 = dx1 * dy1;
            
            p1 = r2.p1; p2 = r2.p2;
            int origX1_2 = xCoords.get(p1.x), origY1_2 = yCoords.get(p1.y);
            int origX2_2 = xCoords.get(p2.x), origY2_2 = yCoords.get(p2.y);
            long dx2 = Math.abs(origX1_2 - origX2_2) + 1;
            long dy2 = Math.abs(origY1_2 - origY2_2) + 1;
            long area2 = dx2 * dy2;
            
            return Long.compare(area2, area1);
        });

        for (Rectangle rect : rectangles) {
            // Get original coordinates
            int origX1 = xCoords.get(rect.p1.x);
            int origY1 = yCoords.get(rect.p1.y);
            int origX2 = xCoords.get(rect.p2.x);
            int origY2 = yCoords.get(rect.p2.y);
            
            int origMinX = Math.min(origX1, origX2);
            int origMaxX = Math.max(origX1, origX2);
            int origMinY = Math.min(origY1, origY2);
            int origMaxY = Math.max(origY1, origY2);
            
            // Find the range of compressed coordinates that cover this rectangle
            int compMinX = -1, compMaxX = -1, compMinY = -1, compMaxY = -1;
            
            for (int i = 0; i < xCoords.size(); i++) {
                if (xCoords.get(i) <= origMinX && (compMinX == -1 || xCoords.get(i) >= xCoords.get(compMinX))) {
                    compMinX = i;
                }
                if (xCoords.get(i) >= origMaxX && (compMaxX == -1 || xCoords.get(i) <= xCoords.get(compMaxX))) {
                    compMaxX = i;
                }
            }
            
            for (int i = 0; i < yCoords.size(); i++) {
                if (yCoords.get(i) <= origMinY && (compMinY == -1 || yCoords.get(i) >= yCoords.get(compMinY))) {
                    compMinY = i;
                }
                if (yCoords.get(i) >= origMaxY && (compMaxY == -1 || yCoords.get(i) <= yCoords.get(compMaxY))) {
                    compMaxY = i;
                }
            }
            
            // Check if all compressed cells in this range are filled
            boolean allFilled = true;
            if (compMinX >= 0 && compMaxX >= 0 && compMinY >= 0 && compMaxY >= 0) {
                for (int compY = compMinY; compY <= compMaxY && allFilled; compY++) {
                    for (int compX = compMinX; compX <= compMaxX && allFilled; compX++) {
                        if (!grid[compY][compX]) {
                            allFilled = false;
                        }
                    }
                }
            } else {
                allFilled = false;
            }
            
            if (allFilled) {
                long dx = origMaxX - origMinX + 1;
                long dy = origMaxY - origMinY + 1;
                long realArea = dx * dy;
                
                System.out.println("Found largest square with area: " + realArea);
                System.out.println("Between points: Point{x=" + origX1 + ", y=" + origY1 + 
                                 "} and Point{x=" + origX2 + ", y=" + origY2 + "}");
                return;
            }
        }
        
        System.out.println("No valid rectangle found");
    }
    
    private boolean isEnclosed(boolean[][] grid, int x, int y, int width, int height) {
        boolean left = false, right = false, up = false, down = false;
        
        for (int i = x - 1; i >= 0; i--) {
            if (grid[y][i]) { left = true; break; }
        }
        for (int i = x + 1; i < width; i++) {
            if (grid[y][i]) { right = true; break; }
        }
        for (int i = y - 1; i >= 0; i--) {
            if (grid[i][x]) { up = true; break; }
        }
        for (int i = y + 1; i < height; i++) {
            if (grid[i][x]) { down = true; break; }
        }
        
        return left && right && up && down;
    }
    
    public static void main(String[] args) {
        Day9b solution = new Day9b();
        solution.solve();
    }

    private class Rectangle {
        Point p1, p2;

        public Rectangle(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }
    }

    private class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
