package gac2025.day10;

import gac2025.Base;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.microsoft.z3.*;

public class Day10b extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("day10/input-b.txt");
        
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
        // Use Z3 solver to find minimum button presses, and it is the first time I had to use external library at Google Advent Calendar :(
        try (Context ctx = new Context()) {
            Optimize opt = ctx.mkOptimize();
            IntExpr presses = ctx.mkIntConst("presses");
            
            // Create a variable for each button representing how many times it's pressed
            IntExpr[] buttonVars = new IntExpr[machine.switches.size()];
            for (int i = 0; i < machine.switches.size(); i++) {
                buttonVars[i] = ctx.mkIntConst("button" + i);
            }
            
            // Map each counter (joltage index) to the buttons that affect it
            Map<Integer, List<IntExpr>> countersToButtons = new HashMap<>();
            for (int i = 0; i < machine.switches.size(); i++) {
                IntExpr buttonVar = buttonVars[i];
                boolean[] switchRow = machine.switches.get(i);
                for (int j = 0; j < switchRow.length; j++) {
                    if (switchRow[j]) {
                        countersToButtons.computeIfAbsent(j, k -> new ArrayList<>()).add(buttonVar);
                    }
                }
            }
            
            // Collect all constraints
            List<BoolExpr> constraints = new ArrayList<>();
            
            // Add constraint: for each counter, sum of button presses must equal target joltage
            for (Map.Entry<Integer, List<IntExpr>> entry : countersToButtons.entrySet()) {
                int counterIndex = entry.getKey();
                List<IntExpr> counterButtons = entry.getValue();
                
                IntExpr targetValue = ctx.mkInt(machine.joltage[counterIndex]);
                IntExpr[] buttonPressesArray = counterButtons.toArray(new IntExpr[0]);
                IntExpr sumOfButtonPresses = (IntExpr) ctx.mkAdd(buttonPressesArray);
                
                BoolExpr equation = ctx.mkEq(targetValue, sumOfButtonPresses);
                constraints.add(equation);
            }
            
            // Add constraint: all button presses must be non-negative
            IntExpr zero = ctx.mkInt(0);
            for (IntExpr buttonVar : buttonVars) {
                BoolExpr nonNegative = ctx.mkGe(buttonVar, zero);
                constraints.add(nonNegative);
            }
            
            // Define total presses as sum of all button variables
            IntExpr sumOfAllButtonVars = (IntExpr) ctx.mkAdd(buttonVars);
            BoolExpr totalPressesEq = ctx.mkEq(presses, sumOfAllButtonVars);
            constraints.add(totalPressesEq);
            
            // Add all constraints at once
            opt.Add(constraints.toArray(new BoolExpr[0]));
            
            // Minimize total presses
            opt.MkMinimize(presses);
            
            // Check and get result (empty array means no additional assumptions)
            BoolExpr[] emptyAssumptions = new BoolExpr[0];
            Status status = opt.Check(emptyAssumptions);
            
            if (status == Status.SATISFIABLE) {
                Model model = opt.getModel();
                IntNum outputValue = (IntNum) model.evaluate(presses, false);
                return outputValue.getInt();
            } else if (status == Status.UNSATISFIABLE) {
                System.out.println("Problem is UNSATISFIABLE (no solution exists).");
                return -1;
            } else {
                System.out.println("Optimization could not be determined (" + status + ").");
                return -1;
            }
        }
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
