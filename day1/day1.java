package day1;

import java.util.Scanner;

import librerias.FileOpener;

public class day1 {
    public static void main(String[] args) {

        Scanner file = FileOpener.opener(1, 1); // day x , 0 test / 1 input

        Character firstNumber = null, lastNumber = null;
        int sum = 0;
        while (file.hasNextLine()) {
            for (Character cha : file.nextLine().toCharArray()) {
                if(Character.isDigit(cha)){
                    if(firstNumber == null){
                        firstNumber = cha;
                        lastNumber = cha;
                    } else {
                        lastNumber = cha;
                    }
                }
            }
            sum += Integer.parseInt(String.valueOf(firstNumber)+String.valueOf(lastNumber));
            firstNumber=null;
        }
        System.out.println(sum);
        
    }
}
