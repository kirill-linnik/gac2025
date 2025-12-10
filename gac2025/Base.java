package gac2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base class for Advent of Code solutions
 * Provides utility methods for reading input files
 */
public abstract class Base {
    
    /**
     * Reads the entire input file as a single string
     * @param filename Name of the input file (e.g., "input.txt")
     * @return Content of the file as a string
     */
    protected String readFile(String filename) {
        try {
            return Files.readString(Paths.get(filename));
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            e.printStackTrace();
            return "";
        }
    }
    
    /**
     * Reads the input file as a list of lines
     * @param filename Name of the input file (e.g., "input.txt")
     * @return List of lines from the file
     */
    protected List<String> readLines(String filename) {
        try {
            return Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            e.printStackTrace();
            return List.of();
        }
    }
    
    /**
     * Reads the input file as a list of non-empty lines
     * @param filename Name of the input file (e.g., "input.txt")
     * @return List of non-empty lines from the file
     */
    protected List<String> readNonEmptyLines(String filename) {
        try {
            return Files.readAllLines(Paths.get(filename))
                    .stream()
                    .filter(line -> !line.trim().isEmpty())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            e.printStackTrace();
            return List.of();
        }
    }

    protected <T> List<List<T>> getAllPermutations(List<T> items) {
        List<List<T>> result = new ArrayList<>();
        generatePermutations(new ArrayList<>(items), 0, result);
        return result;
    }
    
    private <T> void generatePermutations(List<T> items, int index, List<List<T>> result) {
        if (index == items.size() - 1) {
            result.add(new ArrayList<>(items));
            return;
        }
        
        for (int i = index; i < items.size(); i++) {
            swap(items, index, i);
            generatePermutations(items, index + 1, result);
            swap(items, index, i);
        }
    }
    
    private <T> void swap(List<T> items, int i, int j) {
        T temp = items.get(i);
        items.set(i, items.get(j));
        items.set(j, temp);
    }
        
    
    /**
     * Main solve method to be implemented by each solution
     */
    public abstract void solve();
}
