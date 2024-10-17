package day16;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import librerias.FileOpener;
import librerias.Tuple;

public class Day16_2 {

    private static char[][] input;

    private static void run() {
        input = FileOpener.streamOpener(16, 1).stream().map(String::toCharArray).toArray(char[][]::new);

        LinkedList<Beam> starterBeams = new LinkedList<>();
        
        for(int i = 0; i < input.length; i++){
            starterBeams.add(new Beam(i, -1, 4));
            starterBeams.add(new Beam(i, input[0].length, 2));
        }
        for(int i = 0; i < input[0].length; i++){
            starterBeams.add(new Beam(-1, i, 3));
            starterBeams.add(new Beam(input.length, i, 1));
        }

        AtomicInteger maxEnergized = new AtomicInteger(0);
        starterBeams.parallelStream().forEach(actualBeam -> {
            int energy = energizeCells(actualBeam);
            if(energy > maxEnergized.get()){
                synchronized(maxEnergized){
                    maxEnergized.set(energy);
                }
            }
        });
        System.out.println(maxEnergized.get());
    }

    private static int energizeCells(Beam starterBeam){
        HashSet<String> energized = new HashSet<>();
        HashSet<Beam> visited = new HashSet<>();
        ArrayList<Beam> activBeams = new ArrayList<>();

        activBeams.add(starterBeam);

        while (!activBeams.isEmpty()) {
            Beam actualBeam = activBeams.remove(0);
            if (visited.contains(actualBeam))
                continue;
            visited.add(actualBeam);
            energized.add(actualBeam.iPos() + " " + actualBeam.jPos());
            nextBeam(actualBeam, activBeams);

        }

        return energized.size()-1;
    }

    private static void nextBeam(Beam actualBeam, ArrayList<Beam> activBeams) {
        Tuple<Integer, Integer> nextBeamPos = nextBeamPos(actualBeam);
        if(nextBeamPos == null) return;
        char mirror = input[nextBeamPos.getFirst()][nextBeamPos.getSecond()];
        if (mirror == '.') {
            activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), actualBeam.direction()));
            return;
        }

        if (mirror == '|') {
            if(actualBeam.direction() == 2 || actualBeam.direction() == 4){
                activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), 1));
                activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), 3));
                return;
            }
            activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), actualBeam.direction()));
        }
        if (mirror == '-') {
            if(actualBeam.direction() == 1 || actualBeam.direction() == 3){
                activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), 2));
                activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), 4));
                return;
            }
            activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), actualBeam.direction()));
        }
        if (mirror == '/') {
            if(actualBeam.direction() == 1){
                activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), 4));
                return;
            }
            if(actualBeam.direction() == 2){
                activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), 3));
                return;
            }
            if(actualBeam.direction() == 3){
                activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), 2));
                return;
            }
            if(actualBeam.direction() == 4){
                activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), 1));
                return;
            }
        }
        if (mirror == '\\') {
            if(actualBeam.direction() == 1){
                activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), 2));
                return;
            }
            if(actualBeam.direction() == 2){
                activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), 1));
                return;
            }
            if(actualBeam.direction() == 3){
                activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), 4));
                return;
            }
            if(actualBeam.direction() == 4){
                activBeams.add(new Beam(nextBeamPos.getFirst(), nextBeamPos.getSecond(), 3));
                return;
            }
        }
    }

    private static Tuple<Integer, Integer> nextBeamPos(Beam actualBeam) {
        switch (actualBeam.direction()) {
            case 1:
                if (actualBeam.iPos() == 0)
                    return null;
                return new Tuple<Integer, Integer>(actualBeam.iPos() - 1, actualBeam.jPos());
            case 2:
                if (actualBeam.jPos() == 0)
                    return null;
                return new Tuple<Integer, Integer>(actualBeam.iPos(), actualBeam.jPos() - 1);
            case 3:
                if (actualBeam.iPos() == input.length - 1)
                    return null;
                return new Tuple<Integer, Integer>(actualBeam.iPos() + 1, actualBeam.jPos());
            case 4:
                if (actualBeam.jPos() == input[0].length - 1)
                    return null;
                return new Tuple<Integer, Integer>(actualBeam.iPos(), actualBeam.jPos() + 1);
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        run();
        Instant end = Instant.now();
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }
}