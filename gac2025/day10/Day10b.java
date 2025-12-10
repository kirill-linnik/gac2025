package gac2025.day10;

import gac2025.Base;
import java.util.ArrayList;
import java.util.List;

public class Day10b extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("gac2025/day10/input-b.txt");
        
        List<Machine> machines = new ArrayList<>();
        for (String line : lines) {
            boolean[] indicators = new boolean[0];
            List<boolean[]> switches = new ArrayList<>();
            int[] joltage = new int[0];
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
                else if ( part.startsWith("{")){
                    String rowData = part.substring(1, part.length() - 1); // Remove braces
                    String[] rowJoltage = rowData.split(",");
                    joltage = new int[rowJoltage.length];
                    for (int i = 0; i < rowJoltage.length; i++) {
                        joltage[i] = Integer.parseInt(rowJoltage[i]);
                    }
                }
            }
            Machine machine = new Machine(indicators, switches, joltage);
            machines.add(machine);
        }
        
        long result = 0;
        for ( Machine machine : machines ){
            System.out.println("Processing machine: " + machine);
            long machineResult = getMinimumButtonPressesToAchieveJoltage(machine);
            System.out.println("Result for machine: " + machineResult);
            result += machineResult;
        }
        System.out.println("Final result: " + result);
    }
    
    public static void main(String[] args) {
        Day10b solution = new Day10b();
        solution.solve();
    }

    private long getMinimumButtonPressesToAchieveJoltage(Machine machine) {
        long result = 0;
        int[] currentJoltage = new int[machine.indicators.length];
        while ( !statesAreEqual(currentJoltage, machine.joltage) ){
            boolean[] bestNextSwitch = getBestNextSwitch(machine, currentJoltage);
            applySwitch(currentJoltage, bestNextSwitch);
            result++;
        }

        return result; 
    }

    private void applySwitch(int[] currentJoltage, boolean[] switchRow) {
        for ( int i = 0; i < switchRow.length; i++ ){
            if ( switchRow[i] ){
                currentJoltage[i] += 1; // Example effect of switch
            }
        }
    }

    private boolean[] getBestNextSwitch(Machine machine, int[] currentJoltage) {
        // Find the switch that gets us closest to the target joltage
        boolean[] bestSwitch = null;
        int bestImprovement = Integer.MAX_VALUE;

        for ( boolean[] switchRow : machine.switches ){
            int[] newJoltage = currentJoltage.clone();
            for ( int i = 0; i < switchRow.length; i++ ){
                if ( switchRow[i] ){
                    newJoltage[i] += 1; // Example effect of switch
                }
            }
            int improvement = calculateImprovement(newJoltage, machine.joltage);
            if ( improvement < bestImprovement ){
                bestImprovement = improvement;
                bestSwitch = switchRow;
            }
        }
        return bestSwitch;
    }

    private int calculateImprovement(int[] newState, int[] target) {
        int improvement = 0;
        for ( int i = 0; i < newState.length; i++ ){
            int voltageDiff = target[i] - newState[i];
            if ( voltageDiff >= 0 ){
                improvement += voltageDiff;
            }
            else {
                return Integer.MAX_VALUE; // Penalize overshooting
            }
        }
        return improvement;
    }
   

    private boolean statesAreEqual(int[] state1, int[] state2) {
        if ( state1.length != state2.length ) {
            return false;
        }
        for ( int i = 0; i < state1.length; i++ ){
            if ( state1[i] != state2[i] ) { 
                return false;
            }
        }
        return true;
    }

    private class Machine {
        boolean[] indicators;
        List<boolean[]> switches;
        int[] joltage;
        
        public Machine(boolean[] indicators, List<boolean[]> switches, int[] joltage) {
            this.indicators = indicators;
            this.switches = switches;
            this.joltage = joltage;
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
            sb.append(", Joltage: {");
            for (int j : joltage) {
                sb.append(j).append(",");
            }
            if (sb.charAt(sb.length() - 1) == ',') {
                sb.deleteCharAt(sb.length() - 1); // Remove trailing comma
            }
            sb.append("}");
            return sb.toString();
        }
    }
}
