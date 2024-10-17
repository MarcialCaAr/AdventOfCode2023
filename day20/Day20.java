package day20;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import librerias.FileOpener;
import librerias.Tuple;

public class Day20 {


    private static final int BUTTON_PULSES = 1000;
    private static HashMap<String, Module> moduls = new HashMap<>();

    private static void run(){
        List<String> input = FileOpener.streamOpener(20, 1);

        for(String line : input){
            String[] splitLine = line.split(" -> ");
            boolean isFlipFlop = splitLine[0].charAt(0) == '%' ? true : false;
            Module currentModule = new Module(isFlipFlop, splitLine[1].split(", "), new HashMap<>());
            moduls.put(splitLine[0].substring(1), currentModule);
        }

        for(String line : input){
            String[] splitLine = line.split(" -> ");
            String[] destinations = splitLine[1].split(", ");
            for(String destination : destinations){
                if(moduls.containsKey(destination) && !moduls.get(destination).isFlipFlop) 
                    moduls.get(destination).inputs.put(splitLine[0].substring(1), Boolean.FALSE);
            }
        }

        LinkedList<Tuple<String, Integer>> nextPulses = new LinkedList<>();
        long lowPulses = 0;
        long highPulses = 0;


        for(int i = 0; i < BUTTON_PULSES; i++){
            nextPulses.add(new Tuple<String,Integer>("roadcaster", 0));
            
            while (!nextPulses.isEmpty()) {
                Tuple<String, Integer> aux = nextPulses.remove(0);
                if(aux.getSecond() == 1){
                    highPulses++;
                } else {
                    lowPulses++;
                }
                if(!moduls.containsKey(aux.getFirst())){
                    continue;
                }
                // System.out.println(nextPulses);
                // System.out.println(aux.getFirst() + " " + aux.getSecond());
                nextPulses.addAll(moduls.get(aux.getFirst()).getNextPulses(aux.getSecond()));
                for(String destination : moduls.get(aux.getFirst()).destinations){
                    if(moduls.containsKey(destination) && !moduls.get(destination).isFlipFlop){
                        moduls.get(destination).inputs.put(aux.getFirst(), moduls.get(aux.getFirst()).isActivated);
                    }
                }
        }
        }
        long aux = lowPulses * highPulses;
        System.out.println(lowPulses + " " + highPulses);
        System.out.println(aux);

        // System.out.println(moduls);
        // for(String aux : moduls.get("roadcaster").destinations()){
            // System.out.print(aux + " ");
            // System.out.println(moduls.get(aux));
        // }
        // System.out.println(moduls.get("pz"));
        // System.out.println(moduls.get("ct"));

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
