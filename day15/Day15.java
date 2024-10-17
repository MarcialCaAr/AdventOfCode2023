package day15;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import librerias.FileOpener;

public class Day15 {

    private static void run(){
        List<String> input = FileOpener.streamOpener(15,1); 
        input = Arrays.asList(input.get(0).split(","));

        int res = 0;
        for(String code : input){
            int value = 0;
            for(char character : code.toCharArray()){
                value += character;
                value *= 17;
                value %= 256;
            }
            res += value;
        }

        System.out.println(res);
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        run();
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
    
}
