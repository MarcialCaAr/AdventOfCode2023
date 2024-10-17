package day10;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

import librerias.Tuple;
import librerias.FileOpener;


public class day10_22 {
    // IMPORTANT use -Xss4m option on execution

    private static Tuple<Integer, Integer> mapSize;
    private static Tuple<Integer, Integer> sPos = new Tuple<Integer,Integer>(null, null); 
    private static char[][] input;
    private static char[][] bigInput;
    private static int[][] costs;
    private static ArrayList<Tuple<Integer, Integer>> nextPos = new ArrayList<>();
    
    
    public static void run() {
        Scanner file = FileOpener.opener(10, 1); // day x , 0 test / 1 iCnput
        // Scanner file = fileOpener.opener("day10/input_test2.txt");
        
        

        mapSize = FileOpener.getDimensions("day10/input.txt");

        input = new char[mapSize.getFirst()][mapSize.getSecond()];
        costs = new int[mapSize.getFirst()][mapSize.getSecond()];
        bigInput = new char[mapSize.getFirst()*3][mapSize.getSecond()*3];

        int line = 0;
        while (file.hasNextLine()) {
            input[line++] = file.nextLine().toCharArray();
        }

        

        makeLoop(true);
        bigInput[sPos.getFirst()*3+1][sPos.getSecond()*3+1] = 'S';
        costs = new int[mapSize.getFirst()*3][mapSize.getSecond()*3];
        makeLoop(false);



        for(int i = 0; i < bigInput.length; i++){
            if(costs[i][0] == 0){
                checkNotEnclosed(i, 0);
            }
            if(costs[i][mapSize.getSecond()*3-1] == 0){
                checkNotEnclosed(i, mapSize.getSecond()-1);
            }
        }
        for(int i = 0; i < mapSize.getSecond()*3; i++){
            if(costs[0][i] == 0){
                checkNotEnclosed(0, i);
            }
            if(costs[bigInput.length-1][i] == 0){
                checkNotEnclosed(bigInput.length-1, i);
            }
        }


        int maxCost = 0;
        for(int i = 1; i < bigInput.length; i+=3){
            for(int j = 1; j < mapSize.getSecond()*3; j+=3){
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

    private static void makeLoop(boolean makeBig){
        if(makeBig){
            getSPos(input);
            getSConnections(input, makeBig);
        } else {
            getSPos(bigInput);
            getSConnections(bigInput, makeBig);
        }
        costs[sPos.getFirst()][sPos.getSecond()] = 1;
        // System.out.println(nextPos);
        // System.out.println(sPos);
        

        Tuple<Tuple<Integer,Integer>,Tuple<Integer,Integer>> pipeConnections;
        while (!nextPos.isEmpty()) {
            Tuple<Integer, Integer> pipe = nextPos.get(0);
            pipeConnections = getConnections(pipe, makeBig);
            // System.out.println(nextPos.get(0));
            nextPos.remove(0);
            if(costs[pipeConnections.getFirst().getFirst()][pipeConnections.getFirst().getSecond()] == 0){
                costs[pipeConnections.getFirst().getFirst()][pipeConnections.getFirst().getSecond()] = 1;
                nextPos.add(pipeConnections.getFirst());
            }
            if(costs[pipeConnections.getSecond().getFirst()][pipeConnections.getSecond().getSecond()] == 0){
                costs[pipeConnections.getSecond().getFirst()][pipeConnections.getSecond().getSecond()] = 1;
                nextPos.add(pipeConnections.getSecond());
            }
        }
    }

    private static void getSConnections(char[][] pipes,boolean makeBig){
        char pos = pipes[sPos.getFirst() + 1][sPos.getSecond()];
        if(pos == '|' || pos == 'J' || pos == 'L'){
            nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst()+1, sPos.getSecond()));
            costs[sPos.getFirst()+1][sPos.getSecond()] = 1;
            if(makeBig){
                bigInput[sPos.getFirst()*3+2][sPos.getSecond()*3+1] = '|';
            }
        }
        if(sPos.getFirst() > 0){
            pos = pipes[sPos.getFirst()-1][sPos.getSecond()];
            if(pos == '|' || pos == '7' || pos == 'F'){
                nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst()-1, sPos.getSecond()));
                costs[sPos.getFirst()-1][sPos.getSecond()] = 1;
                if(makeBig){
                    bigInput[sPos.getFirst()*3][sPos.getSecond()*3+1] = '|';
                }
            }
        }
        pos = pipes[sPos.getFirst()][sPos.getSecond()+1];
        if(pos == '-' || pos == '7' || pos == 'J'){
            nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst(), sPos.getSecond()+1));
            costs[sPos.getFirst()][sPos.getSecond()+1] = 1;
            if(makeBig){
                bigInput[sPos.getFirst()*3+1][sPos.getSecond()*3+2] = '-';
            }
        }
        pos = pipes[sPos.getFirst()][sPos.getSecond()-1];
        if(pos == '-' || pos == 'F' || pos == 'L'){
            nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst(), sPos.getSecond()-1));
            costs[sPos.getFirst()][sPos.getSecond()-1] = 1;
            if(makeBig){
                bigInput[sPos.getFirst()*3+1][sPos.getSecond()*3] = '-';
            }
        }
    }

    private static void checkNotEnclosed(int i, int j){
        costs[i][j] = 8;
        if(i < bigInput.length-1 && costs[i+1][j] == 0){
            checkNotEnclosed(i+1, j);
        }
        if(i > 0 && costs[i-1][j] == 0){
            checkNotEnclosed(i-1, j);
        }
        if(j < mapSize.getSecond()*3-1 && costs[i][j+1] == 0){
            checkNotEnclosed(i, j+1);
        }
        if(j > 0 && costs[i][j-1] == 0){
            checkNotEnclosed(i, j-1);
        }
    }


    private static Tuple<Tuple<Integer,Integer>,Tuple<Integer,Integer>> getConnections(Tuple<Integer, Integer> pipe, boolean makeBig){
        char pipeType = ' ';
        if(makeBig){
            pipeType = input[pipe.getFirst()][pipe.getSecond()];
        } else {
            pipeType = bigInput[pipe.getFirst()][pipe.getSecond()];
        }
        Tuple<Tuple<Integer,Integer>,Tuple<Integer,Integer>> res = new Tuple<Tuple<Integer,Integer>,Tuple<Integer,Integer>>(null, null);
        switch (pipeType) {
            case '|':
                res.setFirst(new Tuple<Integer,Integer>(pipe.getFirst()-1, pipe.getSecond()));
                res.setSecond(new Tuple<Integer,Integer>(pipe.getFirst()+1, pipe.getSecond()));
                if(makeBig){
                    bigInput[pipe.getFirst()*3][pipe.getSecond()*3+1] = '|';
                    bigInput[pipe.getFirst()*3+1][pipe.getSecond()*3+1] = '|';
                    bigInput[pipe.getFirst()*3+2][pipe.getSecond()*3+1] = '|';
                }
                break;
            case '-':
                res.setFirst(new Tuple<Integer,Integer>(pipe.getFirst(), pipe.getSecond()+1));
                res.setSecond(new Tuple<Integer,Integer>(pipe.getFirst(), pipe.getSecond()-1));
                if(makeBig){
                    bigInput[pipe.getFirst()*3+1][pipe.getSecond()*3] = '-';
                    bigInput[pipe.getFirst()*3+1][pipe.getSecond()*3+1] = '-';
                    bigInput[pipe.getFirst()*3+1][pipe.getSecond()*3+2] = '-';
                }
                break;
            case 'L':
                res.setFirst(new Tuple<Integer,Integer>(pipe.getFirst()-1, pipe.getSecond()));
                res.setSecond(new Tuple<Integer,Integer>(pipe.getFirst(), pipe.getSecond()+1));
                if(makeBig){
                    bigInput[pipe.getFirst()*3][pipe.getSecond()*3+1] = '|';
                    bigInput[pipe.getFirst()*3+1][pipe.getSecond()*3+1] = 'L';
                    bigInput[pipe.getFirst()*3+1][pipe.getSecond()*3+2] = '-';
                }
                break;
            case 'J':
                res.setFirst(new Tuple<Integer,Integer>(pipe.getFirst()-1, pipe.getSecond()));
                res.setSecond(new Tuple<Integer,Integer>(pipe.getFirst(), pipe.getSecond()-1));
                if(makeBig){
                    bigInput[pipe.getFirst()*3][pipe.getSecond()*3+1] = '|';
                    bigInput[pipe.getFirst()*3+1][pipe.getSecond()*3+1] = 'J';
                    bigInput[pipe.getFirst()*3+1][pipe.getSecond()*3] = '-';
                }
                break;
            case '7':
                res.setFirst(new Tuple<Integer,Integer>(pipe.getFirst(), pipe.getSecond()-1));
                res.setSecond(new Tuple<Integer,Integer>(pipe.getFirst()+1, pipe.getSecond()));
                if(makeBig){
                    bigInput[pipe.getFirst()*3+1][pipe.getSecond()*3] = '-';
                    bigInput[pipe.getFirst()*3+1][pipe.getSecond()*3+1] = '7';
                    bigInput[pipe.getFirst()*3+2][pipe.getSecond()*3+1] = '|';
                }
                break;
            case 'F':
                res.setFirst(new Tuple<Integer,Integer>(pipe.getFirst(), pipe.getSecond()+1));
                res.setSecond(new Tuple<Integer,Integer>(pipe.getFirst()+1, pipe.getSecond()));
                if(makeBig){
                    bigInput[pipe.getFirst()*3+1][pipe.getSecond()*3+2] = '-';
                    bigInput[pipe.getFirst()*3+1][pipe.getSecond()*3+1] = 'F';
                    bigInput[pipe.getFirst()*3+2][pipe.getSecond()*3+1] = '|';
                }
                break;
            default:
                System.out.println("error on pipe: " + pipe + " not a valid type of pipe");
                break;
        }


        return res;
    }

    private static void getSPos(char[][] aux){
        for(int i = 0; i < aux.length; i++){
            for(int j = 0; j < aux[i].length; j++){
                if(aux[i][j] == 'S'){
                    sPos.setFirst(i);
                    sPos.setSecond(j);
                    return;
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
