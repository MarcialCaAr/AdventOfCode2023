package day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class day12_copy {

    public static void run() {
        // Scanner file = fileOpener.opener(12, 1); // day x , 0 test / 1 input

        Path path = Paths.get("day12/input.txt"); // Replace with your file path
        ArrayList<String> lines = new ArrayList<>();
        try (Stream<String> lineStream = Files.lines(path)) {
            lines = lineStream.collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LongAdder allPossibleCount = new LongAdder();
        lines.parallelStream().forEach(currentLine -> {
            String[] line = currentLine.split(" ");
            
            int[] workingGroups = Arrays.stream(line[1].split(",")).mapToInt(Integer::parseInt).toArray();
            int validGroups = 0;
            for(String aux : getCombinations(line[0])){
                String[] aux2 = aux.replaceAll("\\." + "+", ".").replaceFirst("^" + "\\." + "+", "").split("\\.");
                // System.out.println(aux);
                if(aux2.length != workingGroups.length){
                    continue;
                }
                boolean isValid = true;
                // for(int i = 0;)
                for(int i = 0; i < aux2.length; i++){
                    if(aux2[i].length() != workingGroups[i]){
                        isValid = false;
                        break;
                    }
                }
                if(isValid){
                    validGroups++;
                }
            }
            synchronized (allPossibleCount) {
                allPossibleCount.add(validGroups);
            }
            // System.out.println(validGroups);
        });

        System.out.println(allPossibleCount);
    }


    private static ArrayList<String> getCombinations(String line){
        ArrayList<String> combinations = new ArrayList<>();
        ArrayList<String> basicCombinations = new ArrayList<>();
        int unknownCount = (int) line.chars().filter(ch -> ch == '?').count();

        // System.out.println(unknownCount);

        generateCombinations(basicCombinations, "", unknownCount);

        for (String comb : basicCombinations) {
            String lineCopy = new String(line);
            for (char cha : comb.toCharArray()) {
                lineCopy = lineCopy.replaceFirst("\\?", String.valueOf(cha));
            }
            combinations.add(lineCopy);
        }
        
        return combinations;
    }

    private static void generateCombinations(ArrayList<String> combinations,String word, int size){
        String chars = "#.";
        if (word.length() == size) {
            combinations.add(word);
            return;
        }
        for (int i = 0; i < chars.length(); i++) {
            generateCombinations(combinations, word + chars.charAt(i), size);
        }
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        run();
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
}
