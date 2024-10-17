package day9;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import librerias.FileOpener;

public class day9 {
    @SuppressWarnings("resource")
    public static void run(boolean part2) {
        Scanner file = FileOpener.opener(9, 1); // day x , 0 test / 1 input

        ArrayList<Integer> line = new ArrayList<>();
        long res = 0;
        while (file.hasNextLine()) {
            Scanner lineScanner = new Scanner(file.nextLine());
            line.clear();
            while (lineScanner.hasNext()) {
                line.add(lineScanner.nextInt());
            }
            if(part2)
                Collections.reverse(line); // part 2
            res += nextNumber(line);
        }

        System.out.println(res);
    }


    private static int nextNumber(List<Integer> list){
        if(list.stream().allMatch(x -> x == 0)){
            return 0;
        }


        ArrayList<Integer> nextSequence = new ArrayList<>();
        for(int i = 0; i < list.size()-1; i++){
            nextSequence.add(list.get(i+1)-list.get(i));
        }

        return list.get(list.size()-1) + nextNumber(nextSequence);
    }


    public static void main(String[] args) {
        Instant start = Instant.now();
		run(false);
		Instant end = Instant.now();
		System.out.println("Runtime part1: " + Duration.between(start, end).toMillis() + " ms.");
        start = Instant.now();
		run(true);
		end = Instant.now();
		System.out.println("Runtime part2: " + Duration.between(start, end).toMillis() + " ms.");
    }
}
