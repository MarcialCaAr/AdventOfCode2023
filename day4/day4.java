package day4;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Scanner;

import librerias.FileOpener;


public class day4 {

    private static final int winningNumbers = 10, myNumbers = 25; // test 5-8, input 10-25
    private static HashSet<Integer> listWinningNumbers;

    public static void run() {
        Scanner file = FileOpener.opener(4, 1); // day x , 0 test / 1 input



        int winnedPoints = 0, cardPoints;
        while (file.hasNextLine()) {
            file.next();
            file.next();

            cardPoints = 0;
            listWinningNumbers = new HashSet<>();
            for(int i = 0; i < winningNumbers; i++){
                listWinningNumbers.add(file.nextInt());
            }
            file.next();

            for(int i = 0; i < myNumbers; i++){
                if(listWinningNumbers.contains(file.nextInt())){
                    if(cardPoints == 0){
                        cardPoints = 1;
                    } else {
                        cardPoints *= 2;
                    }
                }
            }

            winnedPoints += cardPoints;
            // System.out.println(listWinningNumbers);
        }
        System.out.println(winnedPoints);

    }

    public static void main(String[] args) {
        Instant start = Instant.now();
		run();
		Instant end = Instant.now();
		System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
}
