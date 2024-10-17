package day18;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import librerias.FileOpener;

public class Day18_copy {
    private static long getLagoonSize(List<Coordinate> corners) {
        return getSurfaceArea(corners) + getTotalManhattanDistance(corners);
    }

    private static List<Coordinate> digTrench(List<String> input, boolean part1) {
        List<Coordinate> corners = new ArrayList<>();
        Coordinate current = new Coordinate(0, 0);
        corners.add(current);
        for (int i = 0; i < input.size(); i++) {
            String[] parts = input.get(i).split(" ");
            String direction = part1 ? parts[0] : getDirection(parts[2].substring(7, 8));
            long amount = part1 ? Long.valueOf(parts[1]) : Long.decode(parts[2].substring(1, 7));
            current = getNextCoordinate(current, direction, amount);
            if (i != input.size() - 1) {
                corners.add(current);
            }
        }
        return corners;
    }

    private static Coordinate getNextCoordinate(Coordinate current, String direction, long amount) {
        switch (direction) {
            case "R":
                return new Coordinate(current.x() + amount, current.y());
            case "L":
                return new Coordinate(current.x() - amount, current.y());
            case "U":
                return new Coordinate(current.x(), current.y() - amount);
            default:
                return new Coordinate(current.x(), current.y() + amount);
        }
    }

    private static String getDirection(String input) {
        switch (input) {
            case "0":
                return "R";
            case "1":
                return "D";
            case "2":
                return "L";
            default:
                return "U";
        }
    }

    private static long getSurfaceArea(List<Coordinate> corners) {
        // Start with shoelace
        long area = 0;
        for (int i = 0; i < corners.size(); i++) {
            Coordinate c1 = corners.get(i);
            Coordinate c2 = corners.get(i == corners.size() - 1 ? 0 : i + 1);
            long factor = c1.x() * c2.y() - c1.y() * c2.x();
            area += factor;
        }
        area /= 2;
        // Now apply Pick's theorem
        return area - (getTotalManhattanDistance(corners) / 2) + 1;
    }

    private static long getTotalManhattanDistance(List<Coordinate> corners) {
        long total = 0;

        for (int i = 0; i < corners.size(); i++) {
            Coordinate c1 = corners.get(i);
            Coordinate c2 = corners.get(i == corners.size() - 1 ? 0 : i + 1);
            total += Math.abs(c1.x() - c2.x()) + Math.abs(c1.y() - c2.y());
        }

        return total;
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        List<String> input = FileOpener.streamOpener(18, 1);
        System.out.println("Part 1: " + String.valueOf(getLagoonSize(digTrench(input, true))));
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");

        start = Instant.now();
        System.out.println("Part 2: " + String.valueOf(getLagoonSize(digTrench(input, false))));
        end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
}
