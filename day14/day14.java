package day14;

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import librerias.Tuple;
import librerias.FileOpener;

public class day14 {

    private static char[][] map;
    private static Tuple<Integer, Integer> mapDimensions;


    public static void run() {
        Scanner file = FileOpener.opener(14, 1); // day x , 0 test / 1 input


        mapDimensions = FileOpener.getDimensions("day14/input.txt");
        map = new char[mapDimensions.getFirst()][mapDimensions.getSecond()];

        int line = 0;
        while (file.hasNext()) {
            map[line++] = file.nextLine().toCharArray();
        }

        int totalLoad = 0;
        for(int i = 0; i < mapDimensions.getFirst(); i++){
            for(int j = 0; j < mapDimensions.getSecond(); j++){
                if(map[i][j] == 'O'){
                    totalLoad += mapDimensions.getFirst() - moveRockNorth(i, j);
                }
            }
        }

        System.out.println(totalLoad);
    }

    private static int moveRockNorth(int line, int column){
        if(line > 0 && map[line-1][column] == '.'){
            map[line-1][column] = 'O';
            map[line][column] = '.';
            return moveRockNorth(line-1, column);
        }
        return line;
    }


    public static void main(String[] args) {
        Instant start = Instant.now();
        run();
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }

    
}
