package day22;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import librerias.FileOpener;

public class Day22_2 {

    private static int[][][] board = new int[10][10][337];  // 336 : 9
    private static LinkedHashMap<Integer, Brick> bricks;

    private static void run() {
        List<String[][]> input = FileOpener.streamOpener(22, 1).stream()
                .map(s -> Arrays.stream(s.split("~"))
                        .map(p -> p.split(","))
                        .toArray(String[][]::new))
                .collect(Collectors.toList());

        input.sort(new Comparator<String[][]>() {
            @Override
            public int compare(String[][] o1, String[][] o2) {
                if (Integer.parseInt(o1[0][2]) < Integer.parseInt(o2[0][2])) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        // System.out.println(input.get(input.size()-1)[1][2]);
        bricks = new LinkedHashMap<>();
        for (int i = 0; i < input.size(); i++) {
            Brick aux = new Brick(input.get(i)[0][0], input.get(i)[0][1], input.get(i)[0][2], input.get(i)[1][0],
                    input.get(i)[1][1], input.get(i)[1][2]);
            bricks.put(i + 1, aux);
        }

        for (Integer key : bricks.keySet()) {
            while (!goDown(key)) {
            }
        }

        int removableBricks = 0;
        for (Integer key : bricks.keySet()) {
            if(!isRemovable(bricks.get(key))){
                removableBricks += fallingBricks(key, new HashSet<>());
            }
            // removableBricks = isRemovable(bricks.get(key)) ? removableBricks+1 : removableBricks;
        }

        // removableBricks = fallingBricks(1, new HashSet<Integer>());

        System.out.println(removableBricks);
        // System.out.println(input.get(0)[0][2]);
        // printBricks();
    }

    private static int fallingBricks(int brickNum, HashSet<Integer> removedBricks){
        Brick actualBrick = bricks.get(brickNum);
        removedBricks.add(brickNum);
        LinkedList<Integer> nextBricks = new LinkedList<>();
        for (Integer suportedBrick : actualBrick.suportedBricks) {
            @SuppressWarnings("unchecked")
            HashSet<Integer> aux = (HashSet<Integer>)bricks.get(suportedBrick).suportingBricks.clone();
            aux.removeAll(removedBricks);
            if(aux.size() < 1){
                removedBricks.add(suportedBrick);
                nextBricks.add(suportedBrick);
            }
        }

        for(int brick : nextBricks){
            fallingBricks(brick, removedBricks);
        }


        return removedBricks.size()-1;
    }


    private static boolean isRemovable(Brick actualBrick) {
        for (Integer suportedBrick : actualBrick.suportedBricks) {
            if(bricks.get(suportedBrick).suportingBricks.size() < 2){
                return false;
            }
        }

        return true;
    }

    private static boolean goDown(Integer key) {
        Brick v = bricks.get(key);
        if (v.startZ == 1) {
            for (int x = v.startX; x <= v.endX; x++) {
                for (int y = v.startY; y <= v.endY; y++) {
                    for (int z = v.startZ; z <= v.endZ; z++) {
                        board[x][y][z] = key;
                    }
                }
            }
            return true;
        }
        boolean isSupported = false;
        for (int x = v.startX; x <= v.endX; x++) {
            for (int y = v.startY; y <= v.endY; y++) {
                if (board[x][y][v.startZ - 1] != 0) {
                    isSupported = true;
                    bricks.get(board[x][y][v.startZ - 1]).suportedBricks.add(key);
                    v.suportingBricks.add(board[x][y][v.startZ - 1]);
                }
            }
        }
        if (isSupported) {
            for (int x = v.startX; x <= v.endX; x++) {
                for (int y = v.startY; y <= v.endY; y++) {
                    for (int z = v.startZ; z <= v.endZ; z++) {
                        board[x][y][z] = key;
                    }
                }
            }
            return true;
        }

        v.startZ = v.startZ - 1;
        v.endZ = v.endZ - 1;

        return false;
    }

    @SuppressWarnings("unused")
    private static void printBricks() {
        for (int z = 1; z < board[0][0].length; z++) {
            for (int x = 0; x < board.length; x++) {
                for (int y = 0; y < board[0].length; y++) {
                    System.out.print(board[x][y][z] + " ");
                }
                System.out.println();
            }
            System.out.println("-----");
        }
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        run();
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
}
