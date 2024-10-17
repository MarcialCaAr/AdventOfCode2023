package day20;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import librerias.FileOpener;
import librerias.Tuple;

public class Day20_2 {

    private static HashMap<String, Module> moduls = new HashMap<>();
    private static List<Long> activeBeforeLast = new ArrayList<>();

    private static void run() {
        List<String> input = FileOpener.streamOpener(20, 1);
        String nodeBefore_rx = "";

        for (String line : input) {
            String[] splitLine = line.split(" -> ");
            boolean isFlipFlop = splitLine[0].charAt(0) == '%' ? true : false;
            Module currentModule = new Module(isFlipFlop, splitLine[1].split(", "), new HashMap<>());
            moduls.put(splitLine[0].substring(1), currentModule);
        }

        for (String line : input) {
            String[] splitLine = line.split(" -> ");
            String[] destinations = splitLine[1].split(", ");
            if(Arrays.stream(destinations).anyMatch("rx"::contains)){
                nodeBefore_rx = splitLine[0].substring(1);
            }
            for (String destination : destinations) {
                if (moduls.containsKey(destination))
                    moduls.get(destination).inputs.put(splitLine[0].substring(1), Boolean.FALSE);
            }
        }

        LinkedList<Tuple<String, Integer>> nextPulses = new LinkedList<>();
        long count = 1;
        @SuppressWarnings("unchecked")
        Set<String> beforeLast = ((HashMap<String, Integer>) moduls.get(nodeBefore_rx).inputs.clone()).keySet();
        
        

        while (!beforeLast.isEmpty()) {
            nextPulses.add(new Tuple<String, Integer>("roadcaster", 0));

            while (!nextPulses.isEmpty()) {
                if(beforeLast.isEmpty()){
                    break;
                }
                // Getting the quantity of time
                Tuple<String, Integer> aux = nextPulses.remove(0);
                if(beforeLast.contains(aux.getFirst()) && aux.getSecond() == 0){
                    beforeLast.remove(aux.getFirst());
                    // System.out.println(beforeLast);
                    activeBeforeLast.add(count);
                }
                if (!moduls.containsKey(aux.getFirst())) {
                    continue;
                }
                nextPulses.addAll(moduls.get(aux.getFirst()).getNextPulses(aux.getSecond()));
                for (String destination : moduls.get(aux.getFirst()).destinations) {
                    if (moduls.containsKey(destination) && !moduls.get(destination).isFlipFlop) {
                        moduls.get(destination).inputs.put(aux.getFirst(), moduls.get(aux.getFirst()).isActivated);
                    }
                }
            }
            count++;
        }

        // System.out.println(activeBeforeLast);
        System.out.println(lcm_of_array_elements(activeBeforeLast));

    }


    private static long lcm_of_array_elements(List<Long> element_array)
	{
		long lcm_of_array_elements = 1;
		int divisor = 2;
		
		while (true) {
			int counter = 0;
			boolean divisible = false;
			
			for (int i = 0; i < element_array.size(); i++) {

				// lcm_of_array_elements (n1, n2, ... 0) = 0.
				// For negative number we convert into
				// positive and calculate lcm_of_array_elements.

				if (element_array.get(i) == 0) {
					return 0;
				}
				else if (element_array.get(i) < 0) {
					element_array.set(i, element_array.get(i) * (-1));
				}
				if (element_array.get(i) == 1) {
					counter++;
				}

				// Divide element_array by devisor if complete
				// division i.e. without remainder then replace
				// number with quotient; used for find next factor
				if (element_array.get(i) % divisor == 0) {
					divisible = true;
					element_array.set(i, element_array.get(i) / divisor);
				}
			}

			// If divisor able to completely divide any number
			// from array multiply with lcm_of_array_elements
			// and store into lcm_of_array_elements and continue
			// to same divisor for next factor finding.
			// else increment divisor
			if (divisible) {
				lcm_of_array_elements = lcm_of_array_elements * divisor;
			}
			else {
				divisor++;
			}

			// Check if all element_array is 1 indicate 
			// we found all factors and terminate while loop.
			if (counter == element_array.size()) {
				return lcm_of_array_elements;
			}
		}
	}

    public static void main(String[] args) {
        Instant start = Instant.now();
        run();
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }

    // 661244240 too low
    // 778180172 too low
    // 886347020
    // 974238156 too high
}
