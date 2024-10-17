package day5;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

import librerias.FileOpener;

public class day5_2 {

    private static ArrayList<ArrayList<Long>> seedTransformations = new ArrayList<>();

    public static void run() {
        Scanner file = FileOpener.opener(5, 1); // day x , 0 test / 1 input


        file.next();
        ArrayList<Long> seeds = new ArrayList<>();

        for (String num : file.nextLine().substring(1).split(" ")) {
            seeds.add(new BigInteger(num).longValue());
        }

        // System.out.println(seeds);
        // System.out.println(file.nextLine());

        file.nextLine();
        file.nextLine();

        ArrayList<Long> aux = new ArrayList<>();
        while (file.hasNextLine()) {
            String line = file.nextLine();
            if (line.equals("")) {
                seedTransformations.add(aux);
                file.nextLine();
                aux = new ArrayList<>();
                continue;
            }
            Scanner lineScanner = new Scanner(line);

            aux.add(new BigInteger(lineScanner.next()).longValue());
            aux.add(new BigInteger(lineScanner.next()).longValue());
            aux.add(new BigInteger(lineScanner.next()).longValue());

            lineScanner.close();
        }
        seedTransformations.add(aux);

        Long lowestSeed = Long.MAX_VALUE, transSeed ;
        for (int i = 0; i < seeds.size(); i += 2) {
            System.out.println(i);
            for (Long seed = seeds.get(i); seed < seeds.get(i) + seeds.get(i + 1); seed += 1) {
                transSeed = applyTransformation(seed);
                if (transSeed.compareTo(lowestSeed) < 0) {
                    lowestSeed = transSeed;
                    System.out.println(lowestSeed);
                }
            }
        }

        // for(int i = 0; i < seeds.size(); i++){
        //     transSeed= applyTransformation(seeds.get(i));
        //         if (transSeed.compareTo(lowestSeed) < 0) {
        //             lowestSeed = transSeed;
        //             System.out.println(lowestSeed);
        //         }
        // }


        // System.out.println(applyTransformation(11379441L));

        // System.out.println(seedTransformations.get(6));

        System.out.println(lowestSeed);
        // Collections.sort(seeds);
        // System.out.println(seeds.get(0));
        // System.out.println(seedTransformations);
    }

    private static long applyTransformation(Long seed) {
        Long destinatioRangeStart, sourceRangeStart, rangeLength;

        for (ArrayList<Long> trans : seedTransformations) {
            for (int i = 0; i < trans.size(); i += 3) {
                destinatioRangeStart = trans.get(i);
                sourceRangeStart = trans.get(i + 1);
                rangeLength = trans.get(i + 2);

                if (seed >= sourceRangeStart && seed < (sourceRangeStart + rangeLength)) {
                    seed = destinatioRangeStart + seed - sourceRangeStart;
                    // System.out.println(seed);
                    break;
                }
            }
        }

        return seed;
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
		run();
		Instant end = Instant.now();
		System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
}
