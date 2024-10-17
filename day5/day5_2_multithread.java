package day5;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.LongAdder;

import librerias.Tuple;
import librerias.FileOpener;

public class day5_2_multithread {

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

        ArrayList<Tuple<Long, Long>> ranges = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i += 2) {
            ranges.add(new Tuple<Long, Long>(seeds.get(i), seeds.get(i) + seeds.get(i + 1)));
        }

        LongAdder lowestSeed = new LongAdder();
        lowestSeed.add(Long.MAX_VALUE);

        ranges = divideRanges(ranges, 1000000);
        System.out.println(ranges.size());

        ranges.parallelStream().forEach(seedRange -> {
            for (Long seed = seedRange.getFirst(); seed < seedRange.getSecond(); seed++) {
                Long transSeed = applyTransformation(seed);
                if (transSeed < lowestSeed.longValue()) {
                    synchronized (lowestSeed) {
                        lowestSeed.reset();
                        lowestSeed.add(transSeed);
                        System.out.println(lowestSeed);
                    }
                }
            }
        });

        // ranges.clear();
        // ranges.add(new Tuple<Long, Long>(1000L,1234L));
        // ranges.add(new Tuple<Long, Long>(2321L,3234L));

        // ranges = divideRanges(ranges, 100);

        // System.out.println(ranges);

        System.out.println(lowestSeed);
        // Collections.sort(seeds);
        // System.out.println(seeds);
        // System.out.println(seedTransformations);
    }

    /*
     * Apply all transformations to a seed until know the location
     */
    private static long applyTransformation(Long seed) {
        Long destinatioRangeStart, sourceRangeStart, rangeLength;

        for (ArrayList<Long> trans : seedTransformations) {
            for (int i = 0; i < trans.size(); i += 3) {
                destinatioRangeStart = trans.get(i);
                sourceRangeStart = trans.get(i + 1);
                rangeLength = trans.get(i + 2);

                if (seed >= sourceRangeStart && seed < (sourceRangeStart + rangeLength)) {
                    seed = destinatioRangeStart + seed - sourceRangeStart;
                    break;
                }
            }
        }

        return seed;
    }

    private static ArrayList<Tuple<Long, Long>> divideRanges(ArrayList<Tuple<Long, Long>> ranges, int trames) {
        ArrayList<Tuple<Long, Long>> res = new ArrayList<>();

        ranges.forEach(range -> {
            Long i;
            for (i = range.getFirst(); i < range.getSecond() - trames; i += trames) {
                res.add(new Tuple<Long, Long>(i, i + trames));
            }
            res.add(new Tuple<Long, Long>(i, range.getSecond()));
        });

        return res;
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
		run();
		Instant end = Instant.now();
		System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
}
