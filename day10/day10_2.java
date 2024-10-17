package day10;

import java.util.ArrayList;
import java.util.Scanner;

import librerias.Tuple;
import librerias.FileOpener;

public class day10_2 {

    private static int mapSize;
    private static Tuple<Integer, Integer> sPos = new Tuple<Integer,Integer>(null, null); 
    private static char[][] input; // test 5 - real 140
    private static int[][] costs; // test 5 - real 140
    private static ArrayList<Tuple<Integer, Integer>> nextPos = new ArrayList<>();
    
    
    public static void main(String[] args) {
        Scanner file = FileOpener.opener(10, 1); // day x , 0 test / 1 iCnput
        // Scanner file = fileOpener.opener("day10/input_test2.txt");
        
        

        int line = 1;
        mapSize = file.nextLine().length();
        while (file.hasNextLine()) {
            file.nextLine();
            line++;
        }
        input = new char[line][mapSize];
        costs = new int[line][mapSize];
        file = FileOpener.opener(10, 1); // day x , 0 test / 1 input
        // file = fileOpener.opener("day10/input_test2.txt");
        line = 0;
        while (file.hasNextLine()) {
            input[line++] = file.nextLine().toCharArray();
        }

        getSPos();
        costs[sPos.getFirst()][sPos.getSecond()] = -1;

        getSConnections();
        // System.out.println(nextPos);
        // System.out.println(sPos);

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

        }


        for(int i = 0; i < input.length; i++){
            if(costs[i][0] == 0){
                checkNotEnclosed(i, 0);
            }
            if(costs[i][mapSize-1] == 0){
                checkNotEnclosed(i, mapSize-1);
            }
        }
        for(int i = 0; i < mapSize; i++){
            if(costs[0][i] == 0){
                checkNotEnclosed(0, i);
            }
            if(costs[input.length-1][i] == 0){
                checkNotEnclosed(input.length-1, i);
            }
        }


        int maxCost = 0;
        for(int i = input.length / 4; i < input.length/4*3; i++){
            for(int j = mapSize /4; j < mapSize/4*3; j++){
                if(costs[i][j] == 0){
                    maxCost++;
                }
            }
        }

        System.out.println(maxCost);
        // System.out.println(input[2][0]);
        // PrintingFormats.printBoardHorizontal(costs);

    }

    // 833 too hight

    private static void getSConnections(){
        char pos = input[sPos.getFirst() + 1][sPos.getSecond()];
        if(pos == '|' || pos == 'J' || pos == 'L'){
            nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst()+1, sPos.getSecond()));
            costs[sPos.getFirst()+1][sPos.getSecond()] = 1;
        }
        pos = input[sPos.getFirst()-1][sPos.getSecond()];
        if(pos == '|' || pos == '7' || pos == 'F'){
            nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst()-1, sPos.getSecond()));
            costs[sPos.getFirst()-1][sPos.getSecond()] = 1;
        }
        pos = input[sPos.getFirst()][sPos.getSecond()+1];
        if(pos == '-' || pos == '7' || pos == 'J'){
            nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst(), sPos.getSecond()+1));
            costs[sPos.getFirst()][sPos.getSecond()+1] = 1;
        }
        pos = input[sPos.getFirst() + 1][sPos.getSecond()];
        if(pos == '-' || pos == 'F' || pos == 'L'){
            nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst(), sPos.getSecond()-1));
            costs[sPos.getFirst()][sPos.getSecond()-1] = 1;
        }
    }

    private static void checkNotEnclosed(int i, int j){
        costs[i][j] = -1;
        if(i < input.length-1 && costs[i+1][j] == 0){
            checkNotEnclosed(i+1, j);
        }
        if(i > 0 && costs[i-1][j] == 0){
            checkNotEnclosed(i-1, j);
        }
        if(j < mapSize-1 && costs[i][j+1] == 0){
            checkNotEnclosed(i, j+1);
        }
        if(j > 0 && costs[i][j-1] == 0){
            checkNotEnclosed(i, j-1);
        }
    }

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

    private static void getSPos(){
        for(int i = 0; i < input.length; i++){
            for(int j = 0; j < input[i].length; j++){
                if(input[i][j] == 'S'){
                    sPos.setFirst(i);
                    sPos.setSecond(j);
                    break;
                }
            }
        }
    }
    
}
