package day3;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import librerias.FileOpener;


public class day3_2 {

    private static int size = 140; // 10 test 140 normal
    private static char schematic[][] = new char[size][size];

    public static void run() {
        Scanner file = FileOpener.opener(3, 1); // day x , 0 test / 1 input



        int line = 0;
        while (file.hasNextLine()) {
            schematic[line++] = file.nextLine().toCharArray();
        }

        // printBoardHorizontal(schematic);

        int sum = 0;
        for(int i = 0; i < schematic.length; i++){
            for(int j = 0; j < schematic[i].length; j++){
                if(schematic[i][j] == '*'){
                    sum += checkGear(i, j);
                }
            }
        }

        System.out.println(sum);

    }


    private static int checkGear(int i, int j){
        Set<Integer> gearParts = new HashSet<>();
        // * * *
        // x * *
        // * * *
        if(Character.isDigit(schematic[i-1][j]))
            gearParts.add(getNumber(i-1, j));
        // x * *
        // * * *
        // * * *
        if(Character.isDigit(schematic[i-1][j-1]))
            gearParts.add(getNumber(i-1, j-1));
        // * * *
        // * * *
        // x * *
        if(Character.isDigit(schematic[i-1][j+1]))
            gearParts.add(getNumber(i-1, j+1));
        // * * *
        // * * x
        // * * *
        if(Character.isDigit(schematic[i+1][j]))
            gearParts.add(getNumber(i+1, j));
        // * * x
        // * * *
        // * * *            
        if(Character.isDigit(schematic[i+1][j-1]))
            gearParts.add(getNumber(i+1, j-1));
        // * * *
        // * * *
        // * * x
        if(Character.isDigit(schematic[i+1][j+1]))
            gearParts.add(getNumber(i+1, j+1));
        // * x *
        // * * *
        // * * *
        if(Character.isDigit(schematic[i][j-1]))
            gearParts.add(getNumber(i, j-1));
        // * * *
        // * * *
        // * x *
        if(Character.isDigit(schematic[i][j+1]))
            gearParts.add(getNumber(i, j+1));

        // System.out.println(gearParts);
        if(gearParts.size() != 2){
            return 0;
        }

        int res = 1;
        for(Integer k : gearParts){
            res *= k;
        }
        return res;
    }


    private static int getNumber(int i, int j){
        if(j > 0 && Character.isDigit(schematic[i][j-1])){
            return getNumber(i, j-1);
        }

        String res = "";
        while (j < size && Character.isDigit(schematic[i][j])) {
            res += schematic[i][j];
            j++;
        }

        return Integer.parseInt(res);
    }


    public static void main(String[] args) {
        Instant start = Instant.now();
		run();
		Instant end = Instant.now();
		System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
    
}
