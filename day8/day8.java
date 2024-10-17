package day8;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Scanner;

import librerias.Tuple;
import librerias.FileOpener;

public class day8 {
    public static void run() {
        Scanner file = FileOpener.opener(8, 1); // day x , 0 test / 1 input
        // Scanner file = fileOpener.opener("day8/input_test2.txt");


        HashMap<String, Tuple<String,String>> map = new HashMap<>();
        char[] moves = file.nextLine().toCharArray();
        file.nextLine();

        while (file.hasNextLine()) {
            String from = file.next();
            file.next();
            String toLeft = file.next().substring(1,4);
            String toRight = file.next().substring(0,3);

            map.put(from, new Tuple<String,String>(toLeft, toRight));
        }

        String pos = "AAA";
        int stepCount = 0;
        while (!pos.equals("ZZZ")) {
            for(char move : moves){
                if(move == 'L'){
                    pos = map.get(pos).getFirst();
                }
                if(move == 'R'){
                    pos = map.get(pos).getSecond();
                }

                stepCount++;
                if(pos.equals("ZZZ")){
                    break;
                }
            }
        }

        System.out.println(stepCount);

        // System.out.println(start);
        // System.out.println(map);


    }

    public static void main(String[] args) {
        Instant start = Instant.now();
		run();
		Instant end = Instant.now();
		System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
    
}
