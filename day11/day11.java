package day11;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import librerias.Tuple;
import librerias.FileOpener;

public class day11 {
    
    private static List<List<Character>> image = new ArrayList<>();
    private static Tuple<Integer, Integer> mapDimensions;
    
    /**
    * Runs the program. This is a static method to avoid problems with Java's built - in classes
    */
    @SuppressWarnings("unchecked")
    public static void run(){
        Scanner file = FileOpener.opener(11, 1); // day x , 0 test / 1 input        
        
        // Returns an array of all the characters in the file.
        while (file.hasNextLine()) {
            ArrayList<Character> aux = new ArrayList<>();
            String line = file.nextLine();
            for(char cha : line.toCharArray()){
                aux.add((Character)cha);
            }
            // Add an aux to the image if it is a dot.
            if(line.chars().allMatch(x -> x == '.')){
                image.add(aux);
            }
            image.add((ArrayList<Character>)aux.clone());
        }
        mapDimensions = new Tuple<Integer,Integer>(image.size(), image.get(0).size());
        // This method is used to generate a column of the image.
        for(int i = mapDimensions.getSecond()-1; i >= 0; i--){
            String column = "";
            // Calculates the column of the column of the map.
            for(int j = mapDimensions.getFirst()-1; j >= 0; j--){
                column += image.get(j).get(i);
            }
            // Add the. to the image.
            if(column.chars().allMatch(x -> x == '.')){
                // Add the dots to the image.
                for(int j = 0; j < mapDimensions.getFirst(); j++){
                    image.get(j).add(i+1, '.');
                }
            }
        }
        mapDimensions = new Tuple<Integer,Integer>(image.size(), image.get(0).size());

        List<Tuple<Integer, Integer>> galaxies = getGalaxies(image);
        ArrayList<Integer> distances = new ArrayList<>();

        // Calculates the distance between two galaxies.
        for(int i = 0; i < galaxies.size(); i++){
            // Calculates the distance between two galaxies.
            for(int j = i; j < galaxies.size(); j++){
                int distanceI = Math.abs(galaxies.get(i).getFirst() - galaxies.get(j).getFirst());
                int distanceJ = Math.abs(galaxies.get(i).getSecond() - galaxies.get(j).getSecond());
                distances.add(distanceI + distanceJ);
            }
        }

        int sum = distances.stream().reduce(0, Integer::sum);
        System.out.println(sum);
    }
    

    /**
    * Gets galaxies of the image. This is used to determine which gals are present in the image
    * 
    * @param image - List of characters to check
    * 
    * @return Integer of the number of galaxies and Integer of the coordinate of the ground. It is a list
    */
    private static List<Tuple<Integer, Integer>> getGalaxies(List<List<Character>> image){
        List<Tuple<Integer, Integer>> res = new ArrayList<>();

        // returns a tuple of integers and integers
        for(int i = 0; i < mapDimensions.getFirst(); i++){
            // Add a tuple of integers to the list of tuples of integers and integers in the image.
            for(int j = 0; j < mapDimensions.getSecond(); j++){
                // Add a tuple to the list of tuples i j
                if(image.get(i).get(j) == '#'){
                    res.add(new Tuple<Integer,Integer>(i, j));
                }
            }
        }


        return res;
    }
    
    /**
    * Runs the benchmark and prints the runtime. This is a convenience method for testing purposes. It should not be used in production code.
    * 
    * @param args - command line arguments not used in this test but
    */
    public static void main(String[] args) {
        Instant start = Instant.now();
        run();
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
    
    
}
