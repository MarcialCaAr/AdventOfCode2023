package day4;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Scanner;

import librerias.FileOpener;



public class day4_2 {

    private static final int winningNumbers = 10, myNumbers = 25; // test 5-8, input 10-25
    private static HashSet<Integer> listWinningNumbers;
    private static int[] cards = new int[200];

    public static void run() {
        Scanner file = FileOpener.opener(4, 1); // day x , 0 test / 1 input



        for(int i = 0; i < cards.length; i++){
            cards[i]++;
        }


        int winnedPoints = 0, cardPoints;
        int cardNumber = 1;
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
                    cardPoints++;
                }
            }

            for(int j = cards[cardNumber]; j > 0; j--){
                for(int i = cardNumber+1; i <= (cardNumber+cardPoints); i++){
                    cards[i]++;
                }
            }

            winnedPoints += cards[cardNumber];
            cardNumber++;
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
