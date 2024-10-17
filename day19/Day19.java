package day19;

import java.time.Duration;
import java.time.Instant;

public class Day19 {
    private static void run() {
        // List<String> input = FileOpener.streamOpener(18, 1);
    }


    public static void main(String[] args) {
        Instant start = Instant.now();
        run();
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
}
