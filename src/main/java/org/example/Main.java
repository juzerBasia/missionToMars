package org.example;

import java.io.FileNotFoundException;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_YELLOW = "\u001B[33m";
        System.out.println("Hello world!+\n");
        Simulation simulation = new Simulation();


        ArrayList<Item> u1Phase1 = simulation.loadItems(1, "U1");
        ArrayList<Rocket> u1Rockets = simulation.loadU1(u1Phase1);
        int[] u1Phase1Results = simulation.runSimulation(u1Rockets);

        ArrayList<Item> u1Phase2 = simulation.loadItems(2, "U1");
        ArrayList<Rocket> u1RocketsP2 = simulation.loadU1(u1Phase2);
        int[] u1Phase2Results = simulation.runSimulation(u1RocketsP2);


        ArrayList<Item> u2Phase1 = simulation.loadItems(1, "U2");
        ArrayList<Rocket> u2RocketsP1 = simulation.loadU2(u2Phase1);
        int[] u2Phase1Results = simulation.runSimulation(u2RocketsP1);

        ArrayList<Item> u2Phase2 = simulation.loadItems(2, "U2");
        ArrayList<Rocket> u2RocketsP2 = simulation.loadU2(u2Phase2);
        int[] u2Phase2Results = simulation.runSimulation(u2RocketsP2);

        System.out.println("\n\n");
        System.out.println(ANSI_YELLOW+"-".repeat(40) + "OVERVIEW" + "-".repeat(39)+ANSI_RESET);
        System.out.printf("|Phase  | %37s |%37s |\n", "*********** Rocket U1 *************",  "********** Rocket U2 **********");
        System.out.printf("|       |  %4s | %13s | %13s | %4s | %13s | %13s |\n", "Cost", "Tot launched", "incl crashed", "Cost", "Tot launched", "incl crashed");
        System.out.println("-".repeat(87));
        System.out.printf("|Phase 1|  %4d | %13d | %13d | %4d | %13d | %13d |\n", u1Phase1Results[0], u1Phase1Results[1], u1Phase1Results[2], u2Phase1Results[0], u2Phase1Results[1], u2Phase1Results[2]);
        System.out.printf("|Phase 2|  %4d | %13d | %13d | %4d | %13d | %13d |\n", u1Phase2Results[0], u1Phase2Results[1], u1Phase2Results[2], u2Phase2Results[0], u2Phase2Results[1], u2Phase2Results[2]);
        System.out.println("-".repeat(87));
        System.out.printf("|Total  |  %4d | %13d | %13d | %4d | %13d | %13d |\n", u1Phase2Results[0]+u1Phase2Results[0], u1Phase1Results[1]+u1Phase2Results[1], u1Phase1Results[2]+u1Phase2Results[2],u2Phase1Results[0]+u2Phase2Results[0], u2Phase1Results[1]+ u2Phase2Results[1],u2Phase1Results[2]+ u2Phase2Results[2]);
        System.out.println(ANSI_YELLOW+"-".repeat(87)+ANSI_RESET);

        System.out.println(ANSI_YELLOW+"Total weight of equipment transported to the Moon:\n\n"+ANSI_RESET +
                "Phase 1 "+"U1: "+u1Phase1Results[3]+" U2:"+u2Phase1Results[3]+"\n"+
                "Phase 2 "+"U1: "+u1Phase2Results[3]+" U2:"+u2Phase2Results[3]+"\n");
        System.out.println(ANSI_YELLOW+"-".repeat(87)+ANSI_RESET);

        System.out.println(ANSI_YELLOW + "Details of crashed rockets:" + ANSI_RESET);
        System.out.println("_".repeat(87));
        String list = "|Rocket:  %5s |cost: %2s |cargo weight: %5s |tot weight: %5s |no of rockets: %2s |\n";

        int u1Garbage = 0;
        int u2Garbage = 0;
        for (Rocket r:Simulation.crashedRockets.keySet() ) {
            System.out.printf(list, r.getClass().getSimpleName() + "_" + r.getId(), r.getRocketCost$(), r.currentWeight, (r.getRocketWeightKg() + r.currentWeight), Simulation.crashedRockets.get(r));
            if (r instanceof U1) {
                u1Garbage+= (r.getRocketWeightKg() + r.currentWeight);
            } else if (r instanceof U2) {
                u2Garbage += (r.getRocketWeightKg() + r.currentWeight);
            } else {
                System.out.println(" what kind of rocket was crashed? ");
            }
         }
        System.out.println("-".repeat(87));

        System.out.println(ANSI_YELLOW + "Total garbage " + ANSI_RESET);
        System.out.println("U1: "+u1Garbage+"\n"+"U2: "+u2Garbage);
    }
}