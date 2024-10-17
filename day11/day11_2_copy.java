package day11;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import librerias.Tuple;
import librerias.FileOpener;

public class day11_2_copy {
    
    private static int addLines = 1000000; // 1000000
    private static List<List<Character>> image = new ArrayList<>();
    private static Tuple<Integer, Integer> mapDimensions;
    private static ArrayList<Integer> emptyLines = new ArrayList<>(), emptyColumns = new ArrayList<>();
    
    public static void run(){
        Scanner file = FileOpener.opener(11, 1); // day x , 0 test / 1 input        
        
        int lineNum = 0;
        while (file.hasNextLine()) {
            ArrayList<Character> aux = new ArrayList<>();
            String line = file.nextLine();
            for(char cha : line.toCharArray()){
                aux.add((Character)cha);
            }
            if(line.chars().allMatch(x -> x == '.')){
                emptyLines.add(lineNum);
            }
            image.add(aux);
            lineNum++;
        }
        mapDimensions = new Tuple<Integer,Integer>(image.size(), image.get(0).size());

        for(int i = 0; i < mapDimensions.getSecond(); i++){
            String column = "";
            for(int j = mapDimensions.getFirst()-1; j >= 0; j--){
                column += image.get(j).get(i);
            }
            // System.out.println(column);
            if(column.chars().allMatch(x -> x == '.')){
                emptyColumns.add(i);
            }
        }

        List<Tuple<Integer, Integer>> galaxies = getGalaxies(image);

        for(int i = 0; i < galaxies.size(); i++){
            for(int j = emptyLines.size() -1; j >= 0; j--){
                int galaxiePos = galaxies.get(i).getFirst();
                if(galaxiePos > emptyLines.get(j)){
                    galaxies.get(i).setFirst(galaxies.get(i).getFirst() + (j+1)*(addLines-1));
                    break;
                }
            }
            for(int j = emptyColumns.size() -1; j >= 0; j--){
                int galaxiePos = galaxies.get(i).getSecond();
                if(galaxiePos > emptyColumns.get(j)){
                    galaxies.get(i).setSecond(galaxies.get(i).getSecond() + (j+1)*(addLines-1));
                    break;
                }
            }
        }

        // 618719584 too low


        ArrayList<Long> distances = new ArrayList<>();

        for(int i = 0; i < galaxies.size(); i++){
            for(int j = i; j < galaxies.size(); j++){
                long distanceI = Math.abs(galaxies.get(i).getFirst() - galaxies.get(j).getFirst());
                long distanceJ = Math.abs(galaxies.get(i).getSecond() - galaxies.get(j).getSecond());
                distances.add(distanceI + distanceJ);
            }
        }

        long sum = distances.stream().reduce(0L, Long::sum);
        System.out.println(sum);
    }
    

    private static List<Tuple<Integer, Integer>> getGalaxies(List<List<Character>> image){
        List<Tuple<Integer, Integer>> res = new ArrayList<>();

        for(int i = 0; i < mapDimensions.getFirst(); i++){
            for(int j = 0; j < mapDimensions.getSecond(); j++){
                if(image.get(i).get(j) == '#'){
                    res.add(new Tuple<Integer,Integer>(i, j));
                }
            }
        }


        return res;
    }
    
    public static void main(String[] args) {
        Instant start = Instant.now();
        run();
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
    
    
}
