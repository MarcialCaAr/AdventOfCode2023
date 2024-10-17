package day5;

import java.io.File;
import java.math.BigInteger;
import java.util.*;

import librerias.Tuple;


public class rangesTest {
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        // File fd = new File("day5/input_test.txt");
        File fd = new File("day5/input.txt");
        Scanner file = null;
        try {
            file = new Scanner(fd);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        file.next();
        ArrayList<Long> seeds = new ArrayList<>();

        for (String num : file.nextLine().substring(1).split(" ")) {
            seeds.add(new BigInteger(num).longValue());
        }

        List<Tuple<Long, Long>> ranges = new ArrayList<>();
        for(int i = 0; i < seeds.size(); i += 2){
            ranges.add(new Tuple<Long, Long>(seeds.get(i), seeds.get(i) + seeds.get(i+1)));
        }

        // Sort the ranges by end time
        Collections.sort(ranges);

        List<Tuple<Long, Long>> result = new ArrayList<>();
        long currentEnd = Long.MIN_VALUE;
        for (Tuple<Long, Long> range : ranges) {
            // If the current range does not overlap with the last added range, add it to the result
            if (currentEnd < range.getSecond()) {
                result.add(range);
                currentEnd = range.getFirst();
            }
        }
        System.out.println(ranges);

        // Print the result
        for (Tuple<Long, Long> range : result) {
            System.out.println("Range: " + range.getFirst() + " - " + range.getSecond());
        }
    }
}
