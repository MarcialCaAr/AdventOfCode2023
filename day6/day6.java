package day6;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

import librerias.Tuple;
import librerias.FileOpener;

public class day6 {

    public static void run() {
        Scanner file = FileOpener.opener(6, 1); // day x , 0 test / 1 input

        ArrayList<Tuple<Integer, Integer>> races = new ArrayList<>();

        Scanner linesScanner = new Scanner(file.nextLine());

        linesScanner.next();
        while (linesScanner.hasNext()) {
            races.add(new Tuple<Integer,Integer>(linesScanner.nextInt(), null));
        }

        linesScanner.close();
        linesScanner = new Scanner(file.nextLine());

        int aux = 0;
        linesScanner.next();
        while (linesScanner.hasNext()) {
            races.get(aux).setSecond(linesScanner.nextInt());
            aux++;
        }
        linesScanner.close();


        int res = 1;
        for(int j = 0; j < races.size(); j++){
            int distance, wins = 0;
            for(int i = 0; i < races.get(j).getFirst(); i++){
                distance = i * (races.get(j).getFirst()-i);
                if(distance > races.get(j).getSecond()){
                    wins++;
                }
                // System.out.println(distance);

            }
            res *= wins;
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
