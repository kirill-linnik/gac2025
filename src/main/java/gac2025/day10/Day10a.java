package gac2025.day10;

import gac2025.Base;
import java.util.*;

public class Day10a extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("day10/input-a.txt");
        
        List<Machine> machines = new ArrayList<>();
        for (String line : lines) {
            boolean[] indicators = new boolean[0];
            List<boolean[]> switches = new ArrayList<>();
            String[] parts = line.split(" ");
            for (String part : parts) {
                if ( part.startsWith("[")){
                    String rowData = part.substring(1, part.length() - 1); // Remove brackets
                    char[] rowChars = rowData.toCharArray();
                    indicators = new boolean[rowChars.length];
                    for (int i = 0; i < rowChars.length; i++) {
                        indicators[i] = rowChars[i] == '#';
                    }
                }
                else if ( part.startsWith("(")){
                    String rowData = part.substring(1, part.length() - 1); // Remove braces
                    String[] rowSwitches = rowData.split(",");
                    boolean[] switchRow = new boolean[indicators.length];
                    for ( String s : rowSwitches ){
                        int switchIndex = Integer.parseInt(s);
                        switchRow[switchIndex] = true;
                    }
                    switches.add(switchRow);
                }
            }
            Machine machine = new Machine(indicators, switches);
            machines.add(machine);
        }

        long result = 0;
        for ( Machine machine : machines ){
            System.out.println("Solving for machine: " + machine);
            long switchesUsed = getFewestNumberOfSwitchesUsedForIndicatorsOn(machine);
            System.out.println("Switches used: " + switchesUsed);
            result += switchesUsed;
        }
        System.out.println("Final result: " + result);
    }
    
    public static void main(String[] args) {
        Day10a solution = new Day10a();
        solution.solve();
    }

    private long getFewestNumberOfSwitchesUsedForIndicatorsOn(Machine machine) {
        // We need to find which buttons to press to reach the target state
        int numLights = machine.indicators.length;
        int numButtons = machine.switches.size();
        
        // Try all possible combinations
        int minPresses = Integer.MAX_VALUE;
        
        // Generate all possible combinations of button presses
        // Each button can be either pressed (true) or not pressed (false)
        int totalCombinations = (int) Math.pow(2, numButtons);
        
        for (int combination = 0; combination < totalCombinations; combination++) {
            // Convert the combination number to a boolean array
            // e.g., combination 5 (binary 101) = [true, false, true]
            boolean[] buttonPresses = new boolean[numButtons];
            int temp = combination;
            for (int button = 0; button < numButtons; button++) {
                buttonPresses[button] = (temp % 2 == 1);
                temp = temp / 2;
            }
            
            // Start with all lights off
            boolean[] currentState = new boolean[numLights];
            
            // Apply each button that we decided to press
            for (int button = 0; button < numButtons; button++) {
                if (buttonPresses[button]) {
                    // Press this button - toggle the lights it affects
                    for (int light = 0; light < numLights; light++) {
                        if (machine.switches.get(button)[light]) {
                            currentState[light] = !currentState[light];
                        }
                    }
                }
            }
            
            // Check if we reached the target
            if (statesAreEqual(currentState, machine.indicators)) {
                // Count how many buttons we pressed
                int presses = 0;
                for (boolean pressed : buttonPresses) {
                    if (pressed) presses++;
                }
                minPresses = Math.min(minPresses, presses);
            }
        }
        
        return minPresses == Integer.MAX_VALUE ? -1 : minPresses;
    }

    private boolean statesAreEqual(boolean[] state1, boolean[] state2) {
        if (state1.length != state2.length) {
            return false;
        }
        for (int i = 0; i < state1.length; i++) {
            if (state1[i] != state2[i]) {
                return false;
            }
        }
        return true;
    }

    private class Machine {
        boolean[] indicators;
        List<boolean[]> switches;
        
        public Machine(boolean[] indicators, List<boolean[]> switches) {
            this.indicators = indicators;
            this.switches = switches;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Indicators: [");
            for (boolean indicator : indicators) {
                sb.append(indicator ? "#" : ".");
            }
            sb.append("], Switches: ");
            for (boolean[] switchRow : switches) {
                sb.append("(");
                for (int i = 0; i < switchRow.length; i++) {
                    if (switchRow[i]) {
                        sb.append(i).append(",");
                    }
                }
                if (sb.charAt(sb.length() - 1) == ',') {
                    sb.deleteCharAt(sb.length() - 1); // Remove trailing comma
                }
                sb.append(") ");
            }
            return sb.toString();
        }
    }
}
