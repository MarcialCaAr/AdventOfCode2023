package day1;

import java.util.Scanner;

import librerias.FileOpener;

public class day1_2 {
    public static void main(String[] args) {

        Scanner file = FileOpener.opener(1, 1); // day x , 0 test / 1 input
        // Scanner file = fileOpener.opener("day1/input_test.txt");

        String firstNumber = null, lastNumber = null;
        String word = "";
        int sum = 0;
        String aux = "";
        while (file.hasNextLine()) {
            for (Character cha : file.nextLine().toCharArray()) {

                word += cha;
                if((aux = getNumber(word)) != ""){
                    if(firstNumber == null){
                        firstNumber = aux;
                        lastNumber = aux;
                    } else {
                        lastNumber = aux;
                    }
                    word = word.substring(word.length()-1,word.length());
                    continue;
                }
                if(Character.isDigit(cha)){
                    if(firstNumber == null){
                        firstNumber = String.valueOf(cha);
                        lastNumber = String.valueOf(cha);;
                    } else {
                        lastNumber = String.valueOf(cha);;
                    }
                    word = word.substring(word.length()-1,word.length());
                    // word = "";
                }
            }
            // System.out.println(firstNumber+lastNumber);
            sum += Integer.parseInt(firstNumber+lastNumber);
            firstNumber=null;
        }
        System.out.println(sum);
        
    }


    private static String getNumber(String word){

        if(word.contains("one"))
            return "1";
        if(word.contains("two"))
            return "2";
        if(word.contains("three"))
            return "3";
        if(word.contains("four"))
            return "4";
        if(word.contains("five"))
            return "5";
        if(word.contains("six"))
            return "6";
        if(word.contains("seven"))
            return "7";
        if(word.contains("eight"))
            return "8";
        if(word.contains("nine"))
            return "9";

        return "";
    }
}
