# Advent of Code 2025 - Java Solutions

This project contains solutions for the Advent of Code 2025 challenges.

## Project Structure

```
gac2025/
├── Base.java              # Base class with file reading utilities
├── day01/                 # Day 1 solutions
│   ├── Day1a.java
│   ├── Day1b.java
│   └── input.txt
├── day02/                 # Day 2 solutions
│   ├── Day2a.java
│   ├── Day2b.java
│   └── input.txt
...
└── day24/                 # Day 24 solutions
    ├── Day24a.java
    ├── Day24b.java
    └── input.txt
```

## How to Use

1. **Add your input**: Copy the puzzle input from the Advent of Code website into the corresponding `input.txt` file for each day
2. **Implement the solution**: Edit the `DayXa.java` or `DayXb.java` file to implement your solution
3. **Compile**: `javac -d bin gac2025/Base.java gac2025/dayXX/DayXXa.java`
4. **Run**: `java -cp bin gac2025.dayXX.DayXXa`

## Base Class Features

The `Base` class provides three utility methods for reading input files:

- `readFile(String filename)`: Reads the entire file as a single string
- `readLines(String filename)`: Reads the file as a list of lines
- `readNonEmptyLines(String filename)`: Reads the file as a list of non-empty lines

All solution classes inherit from `Base` and can use these methods directly.

## Example

```java
package gac2025.day01;

import gac2025.Base;
import java.util.List;

public class Day1a extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("gac2025/day01/input.txt");
        
        // Your solution here
        int result = 0;
        for (String line : lines) {
            // Process each line
        }
        
        System.out.println("Result: " + result);
    }
    
    public static void main(String[] args) {
        Day1a solution = new Day1a();
        solution.solve();
    }
}
```

Good luck with Advent of Code 2025! 🎄
