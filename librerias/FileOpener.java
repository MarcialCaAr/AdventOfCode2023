package librerias;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileOpener {

    public static List<String> streamOpener(String filename){
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			return stream.collect(Collectors.toList());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
    }

    public static List<String> streamOpener(int day, int part){
        String filename;
        if (part == 1) {
            filename = "day" + day + "/input.txt";
        } else if (part == 0){
            filename = "day" + day + "/input_test.txt";
        } else {
            filename = "day" + day + "/input_test2.txt";
        }

        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			return stream.collect(Collectors.toList());
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
    }

    public static Tuple<Integer, Integer> getDimensions(String fileName){
        Tuple<Integer, Integer> res = new Tuple<Integer,Integer>(null, null);
        try{
            // get the number of lines
            res.setFirst((int)Files.lines(Paths.get(fileName)).count());
            // gets the number of characters on the first line
            res.setSecond(Files.lines(Paths.get(fileName)).findFirst().get().length());
        } catch(Exception e){
            System.out.println(e.toString());
        }

        return res;
    }

    public static Scanner opener(String fileName){
        File fd = new File(fileName);
        Scanner file = null;
        try {
            file = new Scanner(fd);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return file;
    }

    public static Scanner opener(int day, int input) {

        File fd = null;
        if (input == 1) {
            fd = new File("day" + day + "/input.txt");
        } else {
            fd = new File("day" + day + "/input_test.txt");
        }
        Scanner file = null;
        try {
            file = new Scanner(fd);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return file;
    }
}
