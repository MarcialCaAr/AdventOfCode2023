package day2;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import librerias.FileOpener;

public class day2 {

    @SuppressWarnings("resource")
    public static void run() {
        Scanner file = FileOpener.opener(2, 1); // day x , 0 test / 1 input


        Map<String, Integer> map = new HashMap<>();
        map.put("red", 12);
        map.put("green", 13);
        map.put("blue", 14);


        int numLine = 0, sum = 0;
        String line = "";
        while (file.hasNextLine()) {
            line = file.nextLine();

            Scanner lineScanner = null;
            try {
                lineScanner = new Scanner(line);

            } catch (Exception e) {
                System.out.println(e.toString());
            }
            numLine++;
            lineScanner.next();
            lineScanner.next();

            int cubeNum = 0;
            String cubeColour = "";
            boolean isPossibleGame = true;
            while (lineScanner.hasNext()) {

                cubeNum = lineScanner.nextInt();
                cubeColour = lineScanner.next();
                if (cubeColour.substring(cubeColour.length() - 1, cubeColour.length()).equals(";")
                        || cubeColour.substring(cubeColour.length() - 1, cubeColour.length()).equals(",")) {
                    cubeColour = cubeColour.substring(0, cubeColour.length()-1);
                }
                if (map.containsKey(cubeColour) && cubeNum > map.get(cubeColour)) {
                    isPossibleGame = false;
                    break;
                }
            }

            if(isPossibleGame){
                sum += numLine;
                // System.out.println(numLine);
            }
        }

        System.out.println(sum);

    }


    public static void main(String[] args) {
        Instant start = Instant.now();
		run();
		Instant end = Instant.now();
		System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
}
