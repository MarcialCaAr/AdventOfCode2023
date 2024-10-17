package day7;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import librerias.Triple;
import librerias.FileOpener;

public class day7_2 {

    public static void run() {
        Scanner file = FileOpener.opener(7, 1); // day x , 0 test / 1 input
        // Scanner file = fileOpener.opener("day7/input_test2.txt");

        ArrayList<Triple<Integer, String, Integer>> cardSets = new ArrayList<>();

        while (file.hasNextLine()) {
            String cards = file.next();
            cardSets.add(new Triple<Integer, String, Integer>(setType(cards), cards, file.nextInt()));
        }

        cardSets.sort(new Comparator<Triple<Integer, String, Integer>>() {
            @Override
            public int compare(Triple<Integer, String, Integer> o1, Triple<Integer, String, Integer> o2) {
                if (o1.getFirst().compareTo(o2.getFirst()) > 0) {
                    return 1;
                }
                if (o1.getFirst().compareTo(o2.getFirst()) < 0) {
                    return -1;
                }

                if (typeWinner(o1.getSecond(), o2.getSecond()) > 0) {
                    return 1;
                }

                return -1;
            }
        });

        int sum = 0;
        for (int i = 0; i < cardSets.size(); i++) {
            sum += cardSets.get(i).getThird() * (i + 1);
        }

        System.out.println(sum);
        // System.out.println(cardSets);
    }

    // 247088917 too low

    private static int typeWinner(String o1, String o2) {
        if (o1.length() == 0) {
            return 0;
        }
        List<Character> order = Arrays.asList('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A');
        

        if (order.indexOf(o1.charAt(0)) > order.indexOf(o2.charAt(0))) {
            return 1;
        } else if (order.indexOf(o1.charAt(0)) < order.indexOf(o2.charAt(0))) {
            return -1;
        }

        return typeWinner(o1.substring(1), o2.substring(1));
    }

    // five of a kind = 7
    // four of a kind = 6
    // full house = 5
    // three of a kind = 4
    // two pair = 3
    // one pair = 2
    // high card = 1
    private static int setType(String cardSet) {
        HashMap<Character, Integer> cards = new HashMap<>();
        for (char cha : cardSet.toCharArray()) {
            cards.put(cha, cards.getOrDefault(cha, 0) + 1);
        }

        if (cards.containsKey('J') && cards.size() > 1) {
            int numberOfJ = cards.get('J');
            cards.remove('J');
            Set<Character> claves = cards.keySet();
            Object[] clave = claves.toArray();
            Character claveMax = 0;
            int max = 0;
            for (int i = 0; i < clave.length; i++) {
                if (cards.get(clave[i]) > max) {
                    max = cards.get(clave[i]);
                    claveMax = (Character)clave[i];
                }
            }
            cards.put(claveMax, cards.get(claveMax)+numberOfJ);
        }

        if (cards.size() == 1) {
            return 7;
        }

        if (cards.size() == 2) {
            Set<Character> claves = cards.keySet();
            Object[] clave = claves.toArray();
            for (int i = 0; i < clave.length; i++) {
                if (cards.get(clave[i]) == 4) {
                    return 6;
                }
            }
            return 5;
        }

        if (cards.size() == 3) {
            Set<Character> claves = cards.keySet();
            Object[] clave = claves.toArray();
            for (int i = 0; i < clave.length; i++) {
                if (cards.get(clave[i]) == 3) {
                    return 4;
                }
            }
            return 3;
        }

        if (cards.size() == 4)
            return 2;

        return 1;
    }


    public static void main(String[] args) {
        Instant start = Instant.now();
		run();
		Instant end = Instant.now();
		System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }

}
