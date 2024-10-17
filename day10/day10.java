package day10;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

import librerias.Tuple;
import librerias.FileOpener;

public class day10 {

    private static Tuple<Integer, Integer> mapSize;
    private static Tuple<Integer, Integer> sPos = new Tuple<Integer,Integer>(null, null); 
    private static char[][] input; // test 5 - real 140
    private static int[][] costs; // test 5 - real 140
    private static ArrayList<Tuple<Integer, Integer>> nextPos = new ArrayList<>();
    
    
    public static void run() {
        Scanner file = FileOpener.opener(10, 0); // day x , 0 test / 1 input
        
        mapSize = FileOpener.getDimensions("day10/input_test.txt");

        input = new char[mapSize.getFirst()][mapSize.getSecond()];
        costs = new int[mapSize.getFirst()][mapSize.getSecond()];
        
        int line = 0;
        while (file.hasNextLine()) {
            input[line++] = file.nextLine().toCharArray();
        }

        getSPos();
        costs[sPos.getFirst()][sPos.getSecond()] = -1;

        getSConnections(input);

        int maxCost2 = 0;
        Tuple<Tuple<Integer,Integer>,Tuple<Integer,Integer>> pipeConnections;
        while (!nextPos.isEmpty()) {
            Tuple<Integer, Integer> pipe = nextPos.get(0);
            pipeConnections = getConnections(pipe);
            nextPos.remove(0);
            if(costs[pipeConnections.getFirst().getFirst()][pipeConnections.getFirst().getSecond()] == 0){
                nextPos.add(pipeConnections.getFirst());
                costs[pipeConnections.getFirst().getFirst()][pipeConnections.getFirst().getSecond()] = costs[pipe.getFirst()][pipe.getSecond()]+1;
            }
            if(costs[pipeConnections.getSecond().getFirst()][pipeConnections.getSecond().getSecond()] == 0){
                nextPos.add(pipeConnections.getSecond());
                costs[pipeConnections.getSecond().getFirst()][pipeConnections.getSecond().getSecond()] = costs[pipe.getFirst()][pipe.getSecond()]+1;
            }
            // PrintingFormats.printBoardHorizontal(costs);
            // System.out.println("+++++++++++++++++");
            maxCost2++;
        }

        // int maxCost = 0;
        // for(int i = 0; i < mapSize.getFirst(); i++){
        //     for(int j = 0; j < mapSize.getSecond(); j++){
        //         if(costs[i][j] > maxCost){
        //             maxCost = costs[i][j];
        //         }
        //     }
        // }
        // System.out.println(maxCost);


        System.out.println((int)Math.ceil((double)maxCost2/2));
        // PrintingFormats.printBoardHorizontal(input);

    }

    // 6637 too low

    private static Tuple<Tuple<Integer,Integer>,Tuple<Integer,Integer>> getConnections(Tuple<Integer, Integer> pipe){
        char pipeType = input[pipe.getFirst()][pipe.getSecond()];
        Tuple<Tuple<Integer,Integer>,Tuple<Integer,Integer>> res = new Tuple<Tuple<Integer,Integer>,Tuple<Integer,Integer>>(null, null);
        switch (pipeType) {
            case '|':
                res.setFirst(new Tuple<Integer,Integer>(pipe.getFirst()-1, pipe.getSecond()));
                res.setSecond(new Tuple<Integer,Integer>(pipe.getFirst()+1, pipe.getSecond()));
                break;
            case '-':
                res.setFirst(new Tuple<Integer,Integer>(pipe.getFirst(), pipe.getSecond()+1));
                res.setSecond(new Tuple<Integer,Integer>(pipe.getFirst(), pipe.getSecond()-1));
                break;
            case 'L':
                res.setFirst(new Tuple<Integer,Integer>(pipe.getFirst()-1, pipe.getSecond()));
                res.setSecond(new Tuple<Integer,Integer>(pipe.getFirst(), pipe.getSecond()+1));
                break;
            case 'J':
                res.setFirst(new Tuple<Integer,Integer>(pipe.getFirst()-1, pipe.getSecond()));
                res.setSecond(new Tuple<Integer,Integer>(pipe.getFirst(), pipe.getSecond()-1));
                break;
            case '7':
                res.setFirst(new Tuple<Integer,Integer>(pipe.getFirst(), pipe.getSecond()-1));
                res.setSecond(new Tuple<Integer,Integer>(pipe.getFirst()+1, pipe.getSecond()));
                break;
            case 'F':
                res.setFirst(new Tuple<Integer,Integer>(pipe.getFirst(), pipe.getSecond()+1));
                res.setSecond(new Tuple<Integer,Integer>(pipe.getFirst()+1, pipe.getSecond()));
                break;
            default:
                System.out.println("error");
                break;
        }


        return res;
    }

    private static void getSConnections(char[][] pipes){
        char pos = pipes[sPos.getFirst() + 1][sPos.getSecond()];
        if(pos == '|' || pos == 'J' || pos == 'L'){
            nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst()+1, sPos.getSecond()));
            costs[sPos.getFirst()+1][sPos.getSecond()] = 1;
        }
        if(sPos.getFirst() > 0){
            pos = pipes[sPos.getFirst()-1][sPos.getSecond()];
            if(pos == '|' || pos == '7' || pos == 'F'){
                nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst()-1, sPos.getSecond()));
                costs[sPos.getFirst()-1][sPos.getSecond()] = 1;
            }
        }
        pos = pipes[sPos.getFirst()][sPos.getSecond()+1];
        if(pos == '-' || pos == '7' || pos == 'J'){
            nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst(), sPos.getSecond()+1));
            costs[sPos.getFirst()][sPos.getSecond()+1] = 1;
        }
        if(sPos.getSecond() > 0){
            pos = pipes[sPos.getFirst()][sPos.getSecond()-1];
            if(pos == '-' || pos == 'F' || pos == 'L'){
                nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst(), sPos.getSecond()-1));
                costs[sPos.getFirst()][sPos.getSecond()-1] = 1;
            }
        }
    }

    private static void getSPos(){
        for(int i = 0; i < mapSize.getFirst(); i++){
            for(int j = 0; j < mapSize.getSecond(); j++){
                if(input[i][j] == 'S'){
                    sPos.setFirst(i);
                    sPos.setSecond(j);
                    break;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        Instant start = Instant.now();
		run();
		Instant end = Instant.now();
		System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
}
