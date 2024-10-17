package day15;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import librerias.FileOpener;

public class Day15_2 {


    private static void run() {
        List<String> input = FileOpener.streamOpener(15, 1);
        input = Arrays.asList(input.get(0).split(","));

        @SuppressWarnings("unchecked")
        LinkedHashMap<String, Integer>[] boxes = IntStream.range(0, 257)
            .mapToObj(i -> new LinkedHashMap<String, Integer>())
            .toArray(LinkedHashMap[]::new);

        for (String code : input) {
            String label = "";
            boolean isDash = false;
            for (char character : code.toCharArray()) {
                if(character == '=' || character == '-'){
                    isDash = character == '-';
                    break;
                }
                label += character;
            }
            if(isDash){
                boxes[hashCode(label)].remove(label);
                continue;
            }
            int focalLength = Character.getNumericValue(code.toCharArray()[code.toCharArray().length-1]);
            boxes[hashCode(label)].put(label, focalLength);
            // System.out.println(label + "    " + isDash + "  " + focalLength);
        }

        AtomicInteger value = new AtomicInteger(0);
        for(int i = 0; i < boxes.length; i++){
            if(boxes[i].isEmpty()){
                continue;
            }
            
            AtomicInteger pos = new AtomicInteger(1);
            boxes[i].forEach((k, v) -> {
                value.addAndGet(hashCode(k) * pos.get() * v);
                // System.out.println(hashCode(k) + " " + pos.get() + " " + v);
                pos.addAndGet(1);
            });
            // System.out.println(i + " " + boxes[i]);
        }
        System.out.println(value);


        // System.out.println(hashCode("rn"));
    }

    private static int hashCode(String code) {
        int value = 0;
        for (char character : code.toCharArray()) {
            value += character;
            value *= 17;
            value %= 256;
        }
        return value+1;
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        run();
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
}
