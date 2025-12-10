# Advent of Code 2025 - Java Solutions (Gradle)

This project contains solutions for the Advent of Code 2025 challenges, built with Gradle.

## Prerequisites

- **Java JDK 25** (with preview features enabled)
- **No need to install Gradle** - the project uses Gradle Wrapper

## Quick Start

### First Time Setup

1. The Gradle wrapper is already included - no installation needed!
2. Dependencies (including Z3) are automatically managed by Gradle

### Running Solutions

**Run any day using the custom task:**
```bash
# Windows
.\gradlew runDay -Pday=01 -Ppart=a
.\gradlew runDay -Pday=10 -Ppart=b

# Linux/Mac
./gradlew runDay -Pday=01 -Ppart=a
./gradlew runDay -Pday=10 -Ppart=b
```

**Run specific day classes directly:**
```bash
# Windows
.\gradlew runDay01a
.\gradlew runDay10b
.\gradlew runDay24b

# Linux/Mac
./gradlew runDay01a
./gradlew runDay10b
./gradlew runDay24b
```

**List all available tasks:**
```bash
.\gradlew listDays    # Windows
./gradlew listDays    # Linux/Mac
```

### Building

```bash
# Compile all classes
.\gradlew build       # Windows
./gradlew build       # Linux/Mac

# Clean and rebuild
.\gradlew clean build
```

## Project Structure

```
gac2025/
├── build.gradle           # Gradle build configuration
├── settings.gradle        # Project settings
├── gradle.properties      # Build properties
├── gradlew / gradlew.bat  # Gradle wrapper scripts
├── gradle/wrapper/        # Gradle wrapper files
├── src/main/java/gac2025/ # Source directory (Gradle standard layout)
│   ├── Base.java          # Base class with utilities
│   ├── day01/             # Day 1 solutions
│   │   ├── Day1a.java
│   │   ├── Day1b.java
│   │   ├── input-a.txt    # Input files next to Java sources
│   │   └── input-b.txt
│   ├── day02/             # Day 2 solutions
│   │   ├── Day2a.java
│   │   ├── Day2b.java
│   │   ├── input-a.txt
│   │   └── input-b.txt
│   ...
│   └── day24/             # Day 24 solutions
└── build/                 # Build output (generated)
    ├── classes/
    ├── libs/
    └── resources/         # .txt files copied here during build
```

## Dependencies

### Z3 Solver

This project uses the **Z3-TurnKey** library (v4.13.0) for constraint solving (used in Day 10 Part B).

**Why Z3?** Some puzzles involve constraint satisfaction and optimization:
- System of linear equations with constraints
- Find non-negative integer solutions
- Minimize/maximize objective functions

Z3 is automatically downloaded by Gradle from Maven Central - no manual setup required!

## IDE Integration

### IntelliJ IDEA
```bash
.\gradlew idea
# Then open the project in IntelliJ
```

### Eclipse
```bash
.\gradlew eclipse
# Then import as existing project
```

### VS Code
Simply open the folder - VS Code will detect the Gradle project automatically.

## Advanced Usage

### Run with custom arguments
```bash
.\gradlew run --args="custom arguments"
```

### Run tests (if added)
```bash
.\gradlew test
```

### Generate dependency report
```bash
.\gradlew dependencies
```

### Build without tests
```bash
.\gradlew build -x test
```

## Making Classes Easy to Run

Every solution class has:
1. **A `main` method** - Can be run directly from IDE
2. **A Gradle task** - Generated automatically for each day/part
3. **Package structure** - Organized by day for clarity

Example structure:
```java
package gac2025.day01;

import gac2025.Base;
import java.util.List;

public class Day1a extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("day01/input-a.txt");
        
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
