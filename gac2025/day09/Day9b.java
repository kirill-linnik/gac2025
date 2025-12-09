package gac2025.day09;

import gac2025.Base;

import java.lang.classfile.ClassFile.Option;
import java.util.*;

public class Day9b extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("gac2025/day09/input-b.txt");

        System.out.println("Number of lines: " + lines.size());
        
        // Parse points
        List<Point> points = new ArrayList<>();
        Set<Integer> allX = new HashSet<>();
        Set<Integer> allY = new HashSet<>();
        
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
        Collections.sort(xCoords);
        Collections.sort(yCoords);

        // Create a grid for compressed coordinates
        boolean[][] compGrid = new boolean[yCoords.size()][xCoords.size()];

        // Mark points in the compressed grid
        for (int i = 0; i < yCoords.size(); i++) {
            int y = yCoords.get(i);
            for (int j = 0; j < xCoords.size(); j++) {
                int x = xCoords.get(j);
                Optional<Point> pointAt = points.stream()
                    .filter(p -> p.x == x && p.y == y)
                    .findFirst();
                if (pointAt.isPresent()) {
                    compGrid[i][j] = true;
                    pointAt.get().compX = j;
                    pointAt.get().compY = i;
                }
            }
        }

        // Fill perimeter - connect consecutive points in the list
        for (int i = 0; i < points.size(); i++) {
            Point current = points.get(i);
            Point next = points.get(i + 1 == points.size() ? 0 : i + 1); // Return to first point
            
            int x1 = current.compX;
            int y1 = current.compY;
            int x2 = next.compX;
            int y2 = next.compY;
            
            // Draw line from current to next
            if (x1 == x2) {
                // Vertical line
                int minY = Math.min(y1, y2);
                int maxY = Math.max(y1, y2);
                for (int y = minY + 1; y < maxY; y++) {
                    compGrid[y][x1] = true;
                }
            } 
            else if (y1 == y2) {
                // Horizontal line
                int minX = Math.min(x1, x2);
                int maxX = Math.max(x1, x2);
                for (int x = minX + 1; x < maxX; x++) {
                    compGrid[y1][x] = true;
                }
            }
        }
        System.out.println("Perimeter filled.");

        // Fill interior
        for ( int i = 0; i < compGrid.length; i++ ) {
            for ( int j = 0; j < compGrid[0].length; j++ ) {
                if ( !compGrid[i][j] ) {
                    if ( isEnclosed(compGrid, j, i, compGrid[0].length, compGrid.length) ) {
                        compGrid[i][j] = true;
                    }
                }
            }
        }
        
        System.out.println("Grid filled, checking rectangles.");

        // Generate rectangles
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                rectangles.add(new Rectangle(points.get(i), points.get(j)));
            }
        }
        
        // Sort rectangles by area descending
        rectangles.sort((r1, r2) -> Long.compare(r2.area, r1.area));

        for (Rectangle rect : rectangles) {
            Point p1 = rect.p1;
            Point p2 = rect.p2;

            // Use compressed coordinates directly from the points
            int compMinX = Math.min(p1.compX, p2.compX);
            int compMaxX = Math.max(p1.compX, p2.compX);
            int compMinY = Math.min(p1.compY, p2.compY);
            int compMaxY = Math.max(p1.compY, p2.compY);

            // Check if all cells in the compressed grid rectangle are filled
            boolean valid = true;
            for (int y = compMinY; y <= compMaxY; y++) {
                for (int x = compMinX; x <= compMaxX; x++) {
                    if (!compGrid[y][x]) {
                        valid = false;
                        break;
                    }
                }
                if (!valid) {
                    break;
                }
            }
                        
            if (valid) {
                System.out.println("Valid rectangle found with area: " + rect.area +
                                   " between points: " + p1 + " and " + p2);
                break;
            }
        }
    }
    
    private boolean isEnclosed(boolean[][] grid, int x, int y, int width, int height) {
        boolean left = false;
        
        for (int i = x - 1; i >= 0; i--) {
            if (grid[y][i]) { 
                left = true; 
                break; 
            }
        }
        if ( !left ) {
            return false;
        }

        boolean right = false;
        for (int i = x + 1; i < width; i++) {
            if (grid[y][i]) { 
                right = true; 
                break; 
            }
        }
        if ( !right ) {
            return false;
        }

        boolean up = false;
        for (int i = y - 1; i >= 0; i--) {
            if (grid[i][x]) { 
                up = true; 
                break; 
            }
        }
        if ( !up ) {
            return false;
        }

        boolean down = false;
        for (int i = y + 1; i < height; i++) {
            if (grid[i][x]) { 
                down = true; 
                break; 
            }
        }
        if ( !down ) {
            return false;
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        Day9b solution = new Day9b();
        solution.solve();
    }

    private class Rectangle {
        Point p1, p2;
        long area;

        public Rectangle(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
            
            this.area = (Math.abs((long)(p1.x - p2.x)) + 1) * (Math.abs((long)(p1.y - p2.y)) + 1);
        }
    }

    private class Point {
        int x, y;
        int compX, compY;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
}
