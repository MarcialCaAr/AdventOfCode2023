package day13;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import librerias.FileOpener;

public class day13_2 {

    
    public static void run() {
        Scanner file = FileOpener.opener(13, 1); // day x , 0 test / 1 input
        

        int sum = 0;
        ArrayList<List<Character>> board = new ArrayList<>();
        while (file.hasNext()) {
            while (file.hasNextLine()) {
                String line = file.nextLine();
                if(line.equals("")){
                    break;
                }
                List<Character> aux = line.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
                
                board.add(aux);
            }

            sum += checkVerticalReflection(board,1);
            sum += checkHorizontalReflection(board, 1) * 100;

            // PrintingFormats.printBoardHorizontal(board);
            // System.out.println(board.size());
            // System.out.println(Math.min(4, board.size()-4));
            // System.out.println(checkVerticalReflection(board,1));
            // System.out.println(checkHorizontalReflection(board, 1));
            // System.out.println("----------------");

            board.clear();
        }
        System.out.println(sum);
    }

    private static int checkVerticalReflection(List<List<Character>> board, int column){
        int reflectionsize = Math.min(column, board.get(0).size()-column);
        int smudgeCount = 0;
        for(int i = 1; i <= reflectionsize; i++){
            for(int j = 0; j < board.size(); j++){
                char a = board.get(j).get(column - i);
                char b = board.get(j).get(column + i -1);

                if(a != b){
                    smudgeCount++;
                }
            }
        }
        
        if(smudgeCount != 1){
            if(board.get(0).size()-1 > column){
                return checkVerticalReflection(board, column+1);
            } else {
                return 0;
            }
        }
        return column;
    }

    private static int checkHorizontalReflection(List<List<Character>> board, int line){
        int reflectionsize = Math.min(line, board.size()-line);
        int smudgeCount = 0;
        for(int i = 1; i <= reflectionsize; i++){
            for(int j = 0; j < board.get(i).size(); j++){
                char a = board.get(line - i).get(j);
                char b = board.get(line + i - 1).get(j);

                if(a != b){
                    smudgeCount++;
                }
            }
        }

        if(smudgeCount == 1){
            return line;
        }

        if(board.size()-1 > line){
            return checkHorizontalReflection(board, line+1);
        } else {
            return 0;
        }
        
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        run();
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }

    // 26966 too low
    
}
