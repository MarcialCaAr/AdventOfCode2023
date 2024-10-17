package day11;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import librerias.Tuple;
import librerias.FileOpener;

public class day11_2 {
    
    private static int addLines = 1000000; // 1000000
    private static List<List<Character>> image = new ArrayList<>();
    private static Tuple<Integer, Integer> mapDimensions;
    private static ArrayList<Integer> emptyLines = new ArrayList<>(), emptyColumns = new ArrayList<>();
    
    /**
     * This method reads a file, processes the data, and calculates the sum of distances between galaxies.
     */
    public static void run() {
        Scanner file = FileOpener.opener(11, 1); // day x , 0 test / 1 input

        int lineNum = 0;
        while (file.hasNextLine()) {
            String line = file.nextLine();
            List<Character> aux = line.chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.toList());

            if (line.chars().allMatch(x -> x == '.')) {
                emptyLines.add(lineNum);
            }
            image.add(aux);
            lineNum++;
        }
        mapDimensions = new Tuple<>(image.size(), image.get(0).size());

        for (int i = 0; i < mapDimensions.getSecond(); i++) {
            int columnIndex = i;
            String column = IntStream.range(0, mapDimensions.getFirst())
                    .mapToObj(j -> image.get(j).get(columnIndex))
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString();

            if (column.chars().allMatch(x -> x == '.')) {
                emptyColumns.add(i);
            }
        }

        List<Tuple<Integer, Integer>> galaxies = getGalaxies(image);

        for (Tuple<Integer, Integer> galaxy : galaxies) {
            for (int j = emptyLines.size() - 1; j >= 0; j--) {
                int galaxiePos = galaxy.getFirst();
                if (galaxiePos > emptyLines.get(j)) {
                    galaxy.setFirst(galaxy.getFirst() + (j + 1) * (addLines - 1));
                    break;
                }
            }
            for (int j = emptyColumns.size() - 1; j >= 0; j--) {
                int galaxiePos = galaxy.getSecond();
                if (galaxiePos > emptyColumns.get(j)) {
                    galaxy.setSecond(galaxy.getSecond() + (j + 1) * (addLines - 1));
                    break;
                }
            }
        }

        ArrayList<Long> distances = new ArrayList<>();

        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i; j < galaxies.size(); j++) {
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
