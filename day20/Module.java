package day20;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import librerias.Tuple;

public class Module {

    public boolean isFlipFlop;
    public String[] destinations;
    public HashMap<String, Boolean> inputs;
    public boolean isActivated = false;

    public Module(boolean isFlipFlop, String[] destinations, HashMap<String, Boolean> inputs) {
        this.isFlipFlop = isFlipFlop;
        this.destinations = destinations;
        this.inputs = inputs;
    }

    public String toString(){

        return "Module{" + isFlipFlop + ", " + destinations + ", " + inputs + ", " + isActivated + "}";
    }

    public List<Tuple<String, Integer>> getNextPulses(int pulse) {
        LinkedList<Tuple<String, Integer>> res = new LinkedList<>();

        if (isFlipFlop) {
            if (pulse == 1)
                return res;
            // System.out.println(this.isActivated);
            this.isActivated = !this.isActivated;
            int sendingPulse = this.isActivated ? 1 : 0;

            for (String destination : destinations) {
                res.add(new Tuple<String, Integer>(destination, sendingPulse));
                // System.out.println(sendingPulse + " -> " + destination);
            }
            return res;
        }

        // if (inputs.isEmpty()) {
        //     for (String destination : destinations) {
        //         res.add(new Tuple<String, Integer>(destination, pulse));
        //     }
        // }

        int sendingPulse = 1;
        isActivated = true;
        // System.out.println(inputs);
        if (inputs.values().stream().allMatch(value -> value == Boolean.TRUE)) {
            sendingPulse = 0;
            isActivated = false;
        }

        for (String destination : destinations) {
            res.add(new Tuple<String, Integer>(destination, sendingPulse));
            // System.out.println(sendingPulse + " -> " + destination);
        }

        return res;
    }
}