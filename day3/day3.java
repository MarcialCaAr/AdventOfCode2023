package day3;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import librerias.FileOpener;


public class day3 {

    private static int size = 140; // 10 test 140 normal
    private static char schematic[][] = new char[size][size];

    public static void run() {
        Scanner file = FileOpener.opener(3, 1); // day x , 0 test / 1 input



        int line = 0;
        while (file.hasNextLine()) {
            schematic[line++] = file.nextLine().toCharArray();
        }

        // printBoardHorizontal(schematic);

        String num = "";
        int sum = 0;
        boolean isPart = false;
        for(int i = 0; i < schematic.length; i++){
            for(int j = 0; j < schematic[i].length; j++){
                if(Character.isDigit(schematic[i][j])){
                    num += schematic[i][j];
                } else {
                    if(isPart){
                        sum += Integer.parseInt(num);
                        isPart = false;
                    }
                    num = "";
                }

                if(!num.equals("") && !isPart){
                    isPart = checkPart(i, j);
                }
            }
        }

        System.out.println(sum);

    }


    private static boolean checkPart(int i, int j){
        // * * *
        // x * *
        // * * *
        if(i > 0 && schematic[i-1][j] != '.' && !Character.isDigit(schematic[i-1][j]))
            return true;
        // x * *
        // * * *
        // * * *
        if(i > 0 && j > 0 && schematic[i-1][j-1] != '.' && !Character.isDigit(schematic[i-1][j-1]))
            return true;
        // * * *
        // * * *
        // x * *
        if(i > 0 && j < size-1 && schematic[i-1][j+1] != '.' && !Character.isDigit(schematic[i-1][j+1]))
            return true;
        // * * *
        // * * x
        // * * *
        if(i < size-1 && schematic[i+1][j] != '.' && !Character.isDigit(schematic[i+1][j]))
            return true;
        // * * x
        // * * *
        // * * *            
        if(i < size-1 && j > 0 && schematic[i+1][j-1] != '.' && !Character.isDigit(schematic[i+1][j-1]))
            return true;
        // * * *
        // * * *
        // * * x
        if(i < size-1 && j < size-1 && schematic[i+1][j+1] != '.' && !Character.isDigit(schematic[i+1][j+1]))
            return true;
        // * x *
        // * * *
        // * * *
        if(j > 0 && schematic[i][j-1] != '.' && !Character.isDigit(schematic[i][j-1]))
            return true;
        // * * *
        // * * *
        // * x *
        if(j < size-1 && schematic[i][j+1] != '.' && !Character.isDigit(schematic[i][j+1]))
            return true;

        return false;
    }


    public static void main(String[] args) {
        Instant start = Instant.now();
		run();
		Instant end = Instant.now();
		System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
    
}
