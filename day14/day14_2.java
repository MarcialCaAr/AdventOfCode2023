package day14;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import librerias.Tuple;
import librerias.FileOpener;

public class day14_2 {

    private static Tuple<Integer, Integer> mapDimensions;
    private static int cycles = 1000, realCycles = 1000000000;
    private static HashMap<String, ArrayList<ArrayList<Character>>> memoization = new HashMap<>();
    private static ArrayList<ArrayList<Character>> map = new ArrayList<>();
    private static ArrayList<Integer> mapValues = new ArrayList<>();

    public static void run() {
        Scanner file = FileOpener.opener(14, 1); // day x , 0 test / 1 input

        mapDimensions = FileOpener.getDimensions("day14/input.txt");

        while (file.hasNext()) {
            ArrayList<Character> aux = new ArrayList<Character>();
            for (char cha : file.nextLine().toCharArray()) {
                aux.add(cha);
            }
            map.add(aux);
        }

        for (int i = 0; i < cycles; i++) {
            mapValues.add(calculateLoad());
            if (memoization.containsKey(map.toString())) {
                // System.out.println(map.toString());
                // System.out.println(memoization.get(map.toString()));
                map = memoization.get(map.toString()).stream().map(ArrayList::new)
                        .collect(Collectors.toCollection(ArrayList::new));
                // System.out.println(i + " copy");
                // System.out.println("-----------------");
                // System.out.println(memoization.size());
            } else {
                String aux = map.toString();
                executeCycle();
                ArrayList<ArrayList<Character>> aux2 = map.stream().map(ArrayList::new)
                        .collect(Collectors.toCollection(ArrayList::new));
                memoization.put(aux, aux2);
                // System.out.println(i);
            }
            // System.out.println(calculateLoad());
            // PrintingFormats.printBoardHorizontal(map);
            // System.out.println();

        }


        Tuple<Integer, Integer> sequence = findStartOfRepeatingSequence(mapValues);
        if(sequence == null){
            System.out.println("error");
            return;
        }
        int res = mapValues.get((realCycles - sequence.getFirst()) % sequence.getSecond() + sequence.getFirst());
        System.out.println(res);



        // PrintingFormats.printBoardHorizontal(map);
        // System.out.println(calculateLoad());
    }

    private static Tuple<Integer, Integer> findStartOfRepeatingSequence(List<Integer> input) {
        for (int length = 2; length <= input.size() / 2; length++) {
            for (int i = 0; i <= input.size() - length*2-1; i++) {
                List<Integer> sequence = input.subList(i, i + length);
                List<Integer> nextSequence = input.subList(i + length, i + length * 2);
                if (sequence.equals(nextSequence)) {
                    return new Tuple<Integer,Integer>(i, sequence.size());

                }
            }
        }
        return null; // Return -1 if no repeating sequence is found
    }
    private static void executeCycle() {
        for (int i = 0; i < mapDimensions.getFirst(); i++) {
            for (int j = 0; j < mapDimensions.getSecond(); j++) {
                if (map.get(i).get(j) == 'O') {
                    moveRockNorth(i, j);
                }
            }
        }
        // PrintingFormats.printBoardHorizontal(map);
        // System.out.println();

        for (int j = 0; j < mapDimensions.getSecond(); j++) {
            for (int i = 0; i < mapDimensions.getFirst(); i++) {
                if (map.get(i).get(j) == 'O') {
                    moveRockWest(i, j);
                }
            }
        }
        // PrintingFormats.printBoardHorizontal(map);
        // System.out.println();
        for (int i = mapDimensions.getFirst() - 1; i >= 0; i--) {
            for (int j = 0; j < mapDimensions.getSecond(); j++) {
                if (map.get(i).get(j) == 'O') {
                    moveRockSouth(i, j);
                }
            }
        }
        // PrintingFormats.printBoardHorizontal(map);
        // System.out.println();
        for (int j = mapDimensions.getSecond() - 1; j >= 0; j--) {
            for (int i = 0; i < mapDimensions.getFirst(); i++) {
                if (map.get(i).get(j) == 'O') {
                    moveRockEast(i, j);
                }
            }
        }
    }

    private static void moveRockNorth(int line, int column) {
        if (line > 0 && map.get(line - 1).get(column) == '.') {
            map.get(line - 1).set(column, 'O');
            map.get(line).set(column, '.');
            moveRockNorth(line - 1, column);
        }
    }

    private static void moveRockWest(int line, int column) {
        if (column > 0 && map.get(line).get(column - 1) == '.') {
            map.get(line).set(column - 1, 'O');
            map.get(line).set(column, '.');
            moveRockWest(line, column - 1);
        }
    }

    private static void moveRockSouth(int line, int column) {
        if (line < mapDimensions.getFirst() - 1 && map.get(line + 1).get(column) == '.') {
            map.get(line + 1).set(column, 'O');
            map.get(line).set(column, '.');
            moveRockSouth(line + 1, column);
        }
    }

    private static void moveRockEast(int line, int column) {
        if (column < mapDimensions.getSecond() - 1 && map.get(line).get(column + 1) == '.') {
            map.get(line).set(column + 1, 'O');
            map.get(line).set(column, '.');
            moveRockEast(line, column + 1);
        }
    }

    private static int calculateLoad() {
        int totalLoad = 0;
        for (int i = 0; i < mapDimensions.getFirst(); i++) {
            for (int j = 0; j < mapDimensions.getSecond(); j++) {
                if (map.get(i).get(j) == 'O') {
                    totalLoad += mapDimensions.getFirst() - i;
                }
            }
        }

        return totalLoad;
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        run();
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }

}
