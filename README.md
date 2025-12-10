# Advent of Code 2025 - Java Solutions

This project contains solutions for the Advent of Code 2025 challenges.

## Project Structure

```
gac2025/
├── Base.java              # Base class with file reading utilities
├── lib/                   # External dependencies (e.g., Z3 solver)
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

### Standard Solutions

1. **Add your input**: Copy the puzzle input from the Advent of Code website into the corresponding `input.txt` file for each day
2. **Implement the solution**: Edit the `DayXa.java` or `DayXb.java` file to implement your solution
3. **Compile**: `javac -d bin gac2025/Base.java gac2025/dayXX/DayXXa.java`
4. **Run**: `java -cp bin gac2025.dayXX.DayXXa`

### Day 10 Part B - Z3 Solver Solution

Day 10 Part B uses the Microsoft Z3 SMT (Satisfiability Modulo Theories) solver for constraint optimization.

**Why Z3?** Part B is essentially a system of linear equations with optimization:
- Each button press increments specific counters
- We need to find non-negative integer values for button presses
- We want to minimize the total number of button presses

This is perfect for Z3's constraint solving and optimization capabilities.

#### Setup

**Automated (Recommended):**
1. Run `.\download-deps.ps1` (downloads Z3 library to `lib/`)
2. Run `.\run-day10b.ps1` (compiles and runs)

**Manual:**
1. Download [z3-turnkey-4.13.0.jar](https://repo1.maven.org/maven2/tools/aqua/z3-turnkey/4.13.0/z3-turnkey-4.13.0.jar) to `lib/`
2. Compile: `javac -cp "lib\*" --enable-preview --source 25 gac2025\Base.java gac2025\day10\Day10b.java`
3. Run: `java -cp "lib\*;." --enable-preview gac2025.day10.Day10b`

#### How It Works

The Z3 solver models the problem as:
1. **Variables**: Integer variable for each button (press count)
2. **Constraints**: For each counter, sum of affecting button presses = target value; all counts ≥ 0
3. **Optimization**: Minimize total button presses
4. **Solution**: Z3 finds the optimal configuration in milliseconds

Example: For buttons `(3)`, `(1,3)`, `(2)` and targets `{3,5,4,7}`, Z3 creates equations like:
- Counter 0: button₄ + button₅ = 3
- Counter 1: button₁ + button₅ = 5
- etc.

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
