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

public class day12_2 {


    public static void run() {
        // Scanner file = fileOpener.opener(12, 1); // day x , 0 test / 1 input

        Path path = Paths.get("day12/input_test.txt"); // Replace with your file path
        ArrayList<String> lines = new ArrayList<>();
        try (Stream<String> lineStream = Files.lines(path)) {
            lines = lineStream.collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
        }

        LongAdder allPossibleCount = new LongAdder();
        lines.parallelStream().forEach(currentLine -> {
            String[] line = currentLine.split(" ");

            line[0] = line[0] + "?" + line[0] + "?" + line[0] + "?" + line[0] + "?" + line[0];
            line[1] = line[1] + "," + line[1] + "," + line[1] + "," + line[1] + "," + line[1];

            int[] workingGroups = Arrays.stream(line[1].split(",")).mapToInt(Integer::parseInt).toArray();
            int unknownCount = (int) line[0].chars().filter(ch -> ch == '?').count();
            int aux = generateCombinations(workingGroups, "", unknownCount, line[0]);
            synchronized (allPossibleCount) {
                allPossibleCount.add(aux);
            }
            // System.out.println(validGroups);
        });

        System.out.println(allPossibleCount);
    }

    private static boolean isValid(String line, int[] workingGroups) {
        String[] aux2 = line.replaceAll("\\." + "+", ".").replaceFirst("^" + "\\." + "+", "").split("\\.");

        if (aux2.length != workingGroups.length) {
            return false;
        }
        boolean isValid = true;
        // for(int i = 0;)
        for (int i = 0; i < aux2.length; i++) {
            if (aux2[i].length() != workingGroups[i]) {
                isValid = false;
                break;
            }
        }
        if (isValid) {
            return true;
        }

        return false;
    }

    private static int generateCombinations(int[] workingGroups, String word, int size, String line) {
        String chars = "#.";
        int res = 0;
        if (word.length() == size) {
            for (char cha : word.toCharArray()) {
                line = line.replaceFirst("\\?", String.valueOf(cha));
            }
            if(isValid(line, workingGroups)){
                return 1;
            } else {
                return 0;
            }
        }
        for (int i = 0; i < chars.length(); i++) {
            res += generateCombinations(workingGroups, word + chars.charAt(i), size, line);
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
