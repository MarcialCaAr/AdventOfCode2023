package day10;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

import librerias.Tuple;
import librerias.FileOpener;


public class day10_22_copy {
    // IMPORTANT use -Xss4m option on execution

    private static Tuple<Integer, Integer> mapSize;
    private static Tuple<Integer, Integer> sPos = new Tuple<Integer,Integer>(null, null); 
    private static char[][] input;
    private static char[][] bigInput;
    private static ArrayList<Tuple<Integer, Integer>> nextPos = new ArrayList<>();
    
    
    public static void run() {
        Scanner file = FileOpener.opener(10, 1); // day x , 0 test / 1 iCnput
        // Scanner file = fileOpener.opener("day10/input_test2.txt");
        
        

        mapSize = FileOpener.getDimensions("day10/input.txt");

        input = new char[mapSize.getFirst()][mapSize.getSecond()];
        bigInput = new char[mapSize.getFirst()*3][mapSize.getSecond()*3];

        int line = 0;
        while (file.hasNextLine()) {
            input[line++] = file.nextLine().toCharArray();
        }

        

        makeLoop(input,true);
        bigInput[sPos.getFirst()*3+1][sPos.getSecond()*3+1] = 'S';
        makeLoop(bigInput,false);

        
        for(int i = 0; i < bigInput.length; i++){
            if(bigInput[i][0] == '\u0000'){
                checkNotEnclosed(i, 0);
            }
            if(bigInput[i][mapSize.getSecond()*3-1] == '\u0000'){
                checkNotEnclosed(i, mapSize.getSecond()-1);
            }
        }
        for(int i = 0; i < mapSize.getSecond()*3; i++){
            if(bigInput[0][i] == '\u0000'){
                checkNotEnclosed(0, i);
            }
            if(bigInput[bigInput.length-1][i] == 0){
                checkNotEnclosed(bigInput.length-1, i);
            }
        }
        
        
        int maxCost = 0;
        for(int i = 1; i < bigInput.length; i+=3){
            for(int j = 1; j < mapSize.getSecond()*3; j+=3){
                if(bigInput[i][j] == '\u0000'){
                    maxCost++;
                }
                
            }
        }
        // PrintingFormats.printBoardHorizontal(bigInput);
        
        System.out.println(maxCost);
        // System.out.println(input[2][0]);
        // PrintingFormats.printBoardHorizontal(costs);

    }

    // 833 too hight

    private static void makeLoop(char[][] map,boolean makeBig){
        getSPos(map);
        getSConnections(map, makeBig);
        map[sPos.getFirst()][sPos.getSecond()] = '@';
        // System.out.println(nextPos);
        // System.out.println(sPos);
        

        Tuple<Tuple<Integer,Integer>,Tuple<Integer,Integer>> pipeConnections;
        while (!nextPos.isEmpty()) {
            Tuple<Integer, Integer> pipe = nextPos.get(0);
            pipeConnections = getConnections(pipe, makeBig);
            map[pipe.getFirst()][pipe.getSecond()] = '@';
            nextPos.remove(0);
            if(pipeConnections == null){
                continue;
            }
            if(map[pipeConnections.getFirst().getFirst()][pipeConnections.getFirst().getSecond()] != '@'){
                nextPos.add(pipeConnections.getFirst());
            }
            if(map[pipeConnections.getSecond().getFirst()][pipeConnections.getSecond().getSecond()] != '@'){
                nextPos.add(pipeConnections.getSecond());
            }
        }
    }

    private static void getSConnections(char[][] pipes,boolean makeBig){
        char pos = pipes[sPos.getFirst() + 1][sPos.getSecond()];
        if(pos == '|' || pos == 'J' || pos == 'L'){
            nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst()+1, sPos.getSecond()));
            if(makeBig){
                bigInput[sPos.getFirst()*3+2][sPos.getSecond()*3+1] = '|';
            }
        }
        if(sPos.getFirst() > 0){
            pos = pipes[sPos.getFirst()-1][sPos.getSecond()];
            if(pos == '|' || pos == '7' || pos == 'F'){
                nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst()-1, sPos.getSecond()));
                if(makeBig){
                    bigInput[sPos.getFirst()*3][sPos.getSecond()*3+1] = '|';
                }
            }
        }
        pos = pipes[sPos.getFirst()][sPos.getSecond()+1];
        if(pos == '-' || pos == '7' || pos == 'J'){
            nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst(), sPos.getSecond()+1));
            if(makeBig){
                bigInput[sPos.getFirst()*3+1][sPos.getSecond()*3+2] = '-';
            }
        }
        pos = pipes[sPos.getFirst()][sPos.getSecond()-1];
        if(pos == '-' || pos == 'F' || pos == 'L'){
            nextPos.add(new Tuple<Integer,Integer>(sPos.getFirst(), sPos.getSecond()-1));
            if(makeBig){
                bigInput[sPos.getFirst()*3+1][sPos.getSecond()*3] = '-';
            }
        }
    }

    private static void checkNotEnclosed(int i, int j){
        bigInput[i][j] = '.';
        if(i < bigInput.length-1 && bigInput[i+1][j] == '\u0000'){
            checkNotEnclosed(i+1, j);
        }
        if(i > 0 && bigInput[i-1][j] == '\u0000'){
            checkNotEnclosed(i-1, j);
        }
        if(j < mapSize.getSecond()*3-1 && bigInput[i][j+1] == '\u0000'){
            checkNotEnclosed(i, j+1);
        }
        if(j > 0 && bigInput[i][j-1] == '\u0000'){
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
            case '@':
                return null;
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
