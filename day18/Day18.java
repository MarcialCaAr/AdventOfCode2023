package day18;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

import librerias.FileOpener;
import librerias.PrintingFormats;
import librerias.Tuple;

public class Day18 {

    private static final int BOARD_SIZE = 400;
    private static char[][] board;
    private static Tuple<Integer, Integer> pos = new Tuple<Integer,Integer>(230, 30);

    private static void run() {
        List<String> input = FileOpener.streamOpener(18, 1);

        
        board = IntStream.range(0, BOARD_SIZE) // Generate a stream of row indices
                .mapToObj(i -> IntStream.range(0, BOARD_SIZE) // For each row, generate a stream of column indices
                .mapToObj(j -> '.') // Map each column index to the fill character
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString() // Convert the StringBuilder to a String
                .toCharArray()) // Convert the String to a char array
                .toArray(char[][]::new); // Collect the char arrays into a 2D char array


        board[230][30] = '#';

        for(String line : input){
            char direction = line.charAt(0);
            int steps = Character.getNumericValue(line.charAt(2));

            // System.out.println(direction + " " + steps);

            try{
                movePos(direction, steps);
            } catch(Exception e){
                e.printStackTrace();
                return;
            }
        }

        // floodFill(0, 0);

        PrintingFormats.printBoardHorizontal(board);


        int count = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                if(board[i][j] == '#' || board[i][j] == '.'){
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    // 41672 too low
    // 41673 too low

    private static void movePos(char direction, int steps) throws Exception{
        switch (direction) {
            case 'U':
                for(int i = 1; i <= steps; i++){
                    board[pos.getFirst()-i][pos.getSecond()] = '#';
                }
                pos.setFirst(pos.getFirst()-steps);
                return;

            case 'L':
                for(int i = 1; i <= steps; i++){
                    board[pos.getFirst()][pos.getSecond()-i] = '#';
                }
                pos.setSecond(pos.getSecond()-steps);
                return;

            case 'D':
                for(int i = 1; i <= steps; i++){
                    board[pos.getFirst()+i][pos.getSecond()] = '#';
                }
                pos.setFirst(pos.getFirst()+steps);
                return;

            case 'R':
                for(int i = 1; i <= steps; i++){
                    board[pos.getFirst()][pos.getSecond()+i] = '#';
                }
                pos.setSecond(pos.getSecond()+steps);
                return;
        
            default:
                System.out.println(direction);
                throw new Exception("Not a valid direction");
        }

    }

    @SuppressWarnings("unused")
    private static void floodFill(int i, int j){
        board[i][j] = 'O';
        if(i < board.length-1 && board[i+1][j] == '.'){
            floodFill(i+1, j);
        }
        if(i > 0 && board[i-1][j] == '.'){
            floodFill(i-1, j);
        }
        if(j < board[0].length-1 && board[i][j+1] == '.'){
            floodFill(i, j+1);
        }
        if(j > 0 && board[i][j-1] == '.'){
            floodFill(i, j-1);
        }
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        run();
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
}
