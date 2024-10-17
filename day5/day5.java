package day5;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import librerias.FileOpener;


public class day5 {

    public static void run() {
        Scanner file = FileOpener.opener(5, 1); // day x , 0 test / 1 input


        file.next();
        ArrayList<Long> seeds = new ArrayList<>();
        
        for(String num : file.nextLine().substring(1).split(" ")){
            seeds.add(new BigInteger(num).longValue());
        }
        
        // System.out.println(seeds);
        // System.out.println(file.nextLine());
        
        Long destinatioRangeStart, sourceRangeStart, rangeLength;
        file.nextLine();
        file.nextLine();
        boolean[] modified = new boolean[seeds.size()];

        while(file.hasNextLine()) {
            String line = file.nextLine();
            if(line.equals("")){
                file.nextLine();
                modified = new boolean[seeds.size()];
                continue;
            }
            Scanner lineScanner = new Scanner(line);

            destinatioRangeStart = new BigInteger(lineScanner.next()).longValue();
            sourceRangeStart = new BigInteger(lineScanner.next()).longValue();
            rangeLength = new BigInteger(lineScanner.next()).longValue();

            for(int i = 0; i < seeds.size(); i++){
                if(!modified[i] && seeds.get(i) >= sourceRangeStart && seeds.get(i) < (sourceRangeStart + rangeLength)){
                    seeds.set(i, destinatioRangeStart + seeds.get(i) - sourceRangeStart);
                    modified[i] = true;
                }
            }
            
            lineScanner.close();
        }
        
        Collections.sort(seeds);
        System.out.println(seeds.get(0));
        // System.out.println(seeds);
        // System.out.println(destinatioRangeStart + " " + sourceRangeStart + " " + rangeLength);
    }
    

    public static void main(String[] args) {
        Instant start = Instant.now();
		run();
		Instant end = Instant.now();
		System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
}
