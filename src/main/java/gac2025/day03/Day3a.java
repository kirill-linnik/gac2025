package gac2025.day03;

import gac2025.Base;

import java.util.ArrayList;
import java.util.List;

public class Day3a extends Base {
    
    @Override
    public void solve() {
        List<String> lines = readLines("day03/input-a.txt");
        
        long totalVoltage = 0;
        for (String line : lines) {
            char[] chars = line.toCharArray();
            List<Short> digits = new ArrayList<>();
            for (int i = 0; i < chars.length; i++) {
                short c = Short.parseShort(String.valueOf(chars[i]));
                for (int j = i+1; j < chars.length; j++) {
                short d = Short.parseShort(String.valueOf(chars[j]));
                    short result = (short)(c * 10 + d);
                    digits.add(result);
                }
            }
            
            short maxVoltage = digits.stream().max(Short::compare).orElse((short)0);
            totalVoltage += maxVoltage;
        }
        System.out.println(totalVoltage);
    }
    
    public static void main(String[] args) {
        Day3a solution = new Day3a();
        solution.solve();
    }
}
