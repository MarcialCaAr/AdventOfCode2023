package day23;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import librerias.FileOpener;
import librerias.Tuple;

public class Day23 {

    private static Tuple<Integer, Integer> START, END;
    private static Integer longestPath = 0;
    
    private static void run(){
        char[][] map;
        map = FileOpener.streamOpener(23, 1).stream().map(String::toCharArray).toArray(char[][]::new);
        START = new Tuple<Integer, Integer>(0,1);
        END = new Tuple<Integer,Integer>(map.length-1, map[0].length-2);

        pathLength(START.getFirst(), START.getSecond(), map, 0);

        System.out.println(longestPath);
    }

    private static void pathLength(int i, int j, char[][] map, int count){
        if(i == END.getFirst() && j == END.getSecond()) {
            if(count > longestPath){
                longestPath = count;
            }
            return;
        }
        map[i][j] = 'O';
        if(i > 0 && (map[i-1][j] == '.' || map[i-1][j] == '^')){
            char[][] copiedMap = Arrays.stream(map).map(char[]::clone).toArray(char[][]::new);
            pathLength(i-1, j, copiedMap, count+1);
        }
        if(map[i+1][j] == '.' || map[i+1][j] == 'v'){
            char[][] copiedMap = Arrays.stream(map).map(char[]::clone).toArray(char[][]::new);
            pathLength(i+1, j, copiedMap, count+1);
        }
        if(map[i][j-1] == '.' || map[i][j-1] == '<'){
            char[][] copiedMap = Arrays.stream(map).map(char[]::clone).toArray(char[][]::new);
            pathLength(i, j-1, copiedMap, count+1);
        }
        if(map[i][j+1] == '.' || map[i][j+1] == '>'){
            char[][] copiedMap = Arrays.stream(map).map(char[]::clone).toArray(char[][]::new);
            pathLength(i, j+1, copiedMap, count+1);
        }


    }



    public static void main(String[] args) {
        Instant start = Instant.now();
        run();
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
}
