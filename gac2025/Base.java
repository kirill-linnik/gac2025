package gac2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    
    /**
     * Main solve method to be implemented by each solution
     */
    public abstract void solve();
}
