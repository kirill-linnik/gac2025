package gac2025.day12;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import gac2025.Base;

public class Day12a extends Base {

    @Override
    public void solve() {
        List<String> lines = readLines("day12/input-a.txt");

        List<Present> presents = new ArrayList<>();
        List<Shape> shapes = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int colonIndex = line.indexOf(':');
            if (colonIndex > 0) {
                if (line.indexOf('x') > 0) { // shape
                    String sizePart = line.substring(0, colonIndex);
                    String[] sizeParts = sizePart.split("x");
                    int width = Integer.parseInt(sizeParts[0]);
                    int height = Integer.parseInt(sizeParts[1]);
                    String countsPart = line.substring(colonIndex + 1).trim();
                    String[] countStrings = countsPart.split(" ");
                    int[] presentCount = new int[countStrings.length];
                    for (int j = 0; j < countStrings.length; j++) {
                        presentCount[j] = Integer.parseInt(countStrings[j]);
                    }
                    shapes.add(new Shape(width, height, presentCount));
                } else { // present
                    int presentIndex = Integer.parseInt(line.substring(0, colonIndex));
                    boolean[][] shape = new boolean[3][3];
                    int area = 0;
                    for (int k = i + 1; k < i + 4; k++) {
                        String shapeLine = lines.get(k);
                        char[] chars = shapeLine.toCharArray();
                        for (int l = 0; l < chars.length; l++) {
                            boolean filled = chars[l] == '#';
                            if (filled) {
                                area++;
                            }
                            shape[k - (i + 1)][l] = filled;
                        }
                    }
                    presents.add(new Present(presentIndex, shape, area));
                }
            }
        }
        System.err.println("Parsed " + presents.size() + " presents and " + shapes.size() + " shapes.");

        presents.sort((p1, p2) -> Integer.compare(p2.area, p1.area)); // order by area descending
        int countOfShapesToBeFilledWithPresents = getCountOfShapesToBeFilledWithPresents(presents, shapes);
        System.out.println("Result (shapes that can be filled with presents): " + countOfShapesToBeFilledWithPresents);
    }

    public static void main(String[] args) {
        Day12a solution = new Day12a();
        solution.solve();
    }

    private int getCountOfShapesToBeFilledWithPresents(List<Present> presents, List<Shape> shapes) {
        int count = 0;
        for (Shape shape : shapes) {
            int totalPresentArea = getTotalPresentArea(shape, presents);
            if (totalPresentArea > shape.shapeArea) {
                System.err.println("Shape " + shape.width + "x" + shape.height + " can't be filled (present area: " + totalPresentArea + ", shape area: " + shape.shapeArea + ")");
                continue;
            }
            boolean canFill = getCanFillShapeWithPresents(shape, presents);
            if (!canFill) {
                System.err.println("Shape " + shape.width + "x" + shape.height + " can't be filled with presents");
                continue;
            }
            count++;
        }
        return count;
    }

    private boolean getCanFillShapeWithPresents(Shape shape, List<Present> presents) {
        return true;
    }

    private int getTotalPresentArea(Shape shape, List<Present> presents) {
        int totalArea = 0;
        for (int i = 0; i < shape.presentCount.length; i++) {
            int presentCount = shape.presentCount[i];
            int presentIndex = i;
            if (presentCount > 0) {
                Present present = presents.stream()
                        .filter(p -> p.presentIndex == presentIndex)
                        .findFirst()
                        .orElse(null);
                totalArea += present.area * presentCount; // assume present is found
            }
        }
        return totalArea;
    }

    private class Present {

        int presentIndex;
        Set<PresentShape> shapes;
        int area;

        public Present(int presentIndex, boolean[][] shape, int area) {
            this.presentIndex = presentIndex;
            this.shapes = generateTransformations(shape);
            this.area = area;
        }

        private Set<PresentShape> generateTransformations(boolean[][] shape) {
            Set<PresentShape> transformations = new HashSet<>();
            for (int i = 0; i < 4; i++) {
                shape = rotate90(shape);
                transformations.add(new PresentShape(shape));
                transformations.add(new PresentShape(flipHorizontal(shape)));
            }
            return transformations;
        }

        private boolean[][] rotate90(boolean[][] shape) {
            int rows = shape.length;
            int cols = shape[0].length;
            boolean[][] rotated = new boolean[cols][rows];
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    rotated[x][rows - 1 - y] = shape[y][x];
                }
            }
            return rotated;
        }

        private boolean[][] flipHorizontal(boolean[][] shape) {
            int rows = shape.length;
            int cols = shape[0].length;
            boolean[][] flipped = new boolean[rows][cols];
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    flipped[y][cols - 1 - x] = shape[y][x];
                }
            }
            return flipped;
        }
    }

    private class PresentShape {

        boolean[][] shape;

        public PresentShape(boolean[][] shape) {
            this.shape = shape;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (boolean[] row : shape) {
                for (boolean cell : row) {
                    sb.append(cell ? '#' : '.');
                }
                sb.append('\n');
            }
            return sb.toString();
        }

        @Override
        public int hashCode() {
            return toString().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            PresentShape other = (PresentShape) obj;
            return this.toString().equals(other.toString());
        }
    }

    private class Shape {

        int width;
        int height;
        int shapeArea;
        int[] presentCount;

        public Shape(int width, int height, int[] presentCount) {
            this.width = width;
            this.height = height;
            this.shapeArea = width * height;
            this.presentCount = presentCount;
        }
    }
}
