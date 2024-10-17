package day8;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import librerias.Tuple;
import librerias.FileOpener;

public class day8_2 {
    public static void run() {
        Scanner file = FileOpener.opener(8, 1); // day x , 0 test / 1 input
        // Scanner file = fileOpener.opener("day8/input_test2.txt");


        HashMap<String, Tuple<String,String>> map = new HashMap<>();
        ArrayList<String> positions = new ArrayList<>();
        char[] moves = file.nextLine().toCharArray();
        file.nextLine();

        while (file.hasNextLine()) {
            String from = file.next();
            file.next();
            String toLeft = file.next().substring(1,4);
            String toRight = file.next().substring(0,3);

            if(from.substring(2,3).equals("A")){
                positions.add(from);
            }

            map.put(from, new Tuple<String,String>(toLeft, toRight));
        }


        int stepCount = 0;
        ArrayList<Integer> lcm = new ArrayList<>();
        while (positions.size() != 0) {
            for(char move : moves){
                for(int i = 0; i < positions.size(); i++){
                    if(move == 'L'){
                        positions.set(i, map.get(positions.get(i)).getFirst());
                    }
                    if(move == 'R'){
                        positions.set(i, map.get(positions.get(i)).getSecond());
                    }
    
                }
                stepCount++;
                for(int i = 0; i < positions.size(); i++){
                    if(positions.get(i).substring(2, 3).equals("Z")){
                        positions.remove(positions.get(i));
                        lcm.add(stepCount);
                    }
                }
                if(positions.size() == 0){
                    break;
                }
            }
        }

        System.out.println(lcm_of_array_elements(lcm));




        // System.out.println(start);
        // System.out.println(map);


    }
	
	private static long lcm_of_array_elements(List<Integer> element_array)
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
	
}

