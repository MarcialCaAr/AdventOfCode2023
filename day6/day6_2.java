package day6;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import librerias.Tuple;
import librerias.FileOpener;

public class day6_2 {

    public static void run() {
        Scanner file = FileOpener.opener(6, 1); // day x , 0 test / 1 input

        Tuple<Long, Long> race = new Tuple<>(0L, 0L);

        Scanner linesScanner = new Scanner(file.nextLine());

        String aux = "";
        linesScanner.next();
        while (linesScanner.hasNext()) {
            aux += linesScanner.next();
        }
        race.setFirst(Long.parseLong(aux));


        linesScanner.close();
        linesScanner = new Scanner(file.nextLine());

        linesScanner.next();
        aux = "";
        while (linesScanner.hasNext()) {
            aux += linesScanner.next();
        }
        race.setSecond(Long.parseLong(aux));
        linesScanner.close();


            Long distance, wins = 0L;
            for(Long i = 0L; i < race.getFirst(); i++){
                distance = i * (race.getFirst()-i);
                if(distance > race.getSecond()){
                    wins++;
                }
                // System.out.println(distance);

            }


        System.out.println(wins);

    }

    public static void main(String[] args) {
        Instant start = Instant.now();
		run();
		Instant end = Instant.now();
		System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }

}
