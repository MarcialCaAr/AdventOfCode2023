package day2;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Scanner;

import librerias.FileOpener;

public class day2_2 {

    @SuppressWarnings("resource")
    public static void run() {

        HashMap<String, Integer> map = new HashMap<>();

        Scanner file = FileOpener.opener(2, 1); // day x , 0 test / 1 input


        int sum = 0;
        String line = "";
        while (file.hasNextLine()) {
            line = file.nextLine();

            Scanner lineScanner = null;
            try {
                lineScanner = new Scanner(line);

            } catch (Exception e) {
                System.out.println(e.toString());
            }
            lineScanner.next();
            lineScanner.next();

            int cubeNum = 0;
            String cubeColour = "";
            map.put("red", 0);
            map.put("green", 0);
            map.put("blue", 0);


            while (lineScanner.hasNext()) {

                cubeNum = lineScanner.nextInt();
                cubeColour = lineScanner.next();
                if (cubeColour.substring(cubeColour.length() - 1, cubeColour.length()).equals(";")
                        || cubeColour.substring(cubeColour.length() - 1, cubeColour.length()).equals(",")) {
                    cubeColour = cubeColour.substring(0, cubeColour.length()-1);
                }
                if (map.containsKey(cubeColour) && cubeNum > map.get(cubeColour)) {
                    map.put(cubeColour, cubeNum);
                }
            }

            int aux = 1;
            for (String key: map.keySet()) {
                if(map.get(key) == 0)
                    continue;
                aux = aux * map.get(key);
            }
            // System.out.println(map);
            sum += aux;
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
