package org.example;

import java.io.FileNotFoundException;
import java.util.List;


public class Main {

    private static final String ANSI_RESET= "\u001B[0m";
    private static final String ANSI_YELLOW= "\u001B[33m";

    public static void main(String[] args) throws FileNotFoundException {
        Simulation simulation = new Simulation();

        List<Item> u1Phase1 = simulation.loadItems(1, "U1");
        List<Rocket> u1Rockets = simulation.load(u1Phase1, "U1");
        int[] u1Phase1Results = simulation.runSimulation(u1Rockets);

        List<Item> u1Phase2 = simulation.loadItems(2, "U1");
        List<Rocket> u1RocketsP2 = simulation.load(u1Phase2,"U1");
        int[] u1Phase2Results = simulation.runSimulation(u1RocketsP2);

        List<Item> u2Phase1 = simulation.loadItems(1, "U2");
        List<Rocket> u2RocketsP1 = simulation.load(u2Phase1,"U2");
        int[] u2Phase1Results = simulation.runSimulation(u2RocketsP1);

        List<Item> u2Phase2 = simulation.loadItems(2, "U2");
        List<Rocket> u2RocketsP2 = simulation.load(u2Phase2,"U2");
        int[] u2Phase2Results = simulation.runSimulation(u2RocketsP2);

        printPhasesOverview(u1Phase1Results, u1Phase2Results, u2Phase1Results, u2Phase2Results);

        printTotalTransported( u1Phase1Results, u1Phase2Results, u2Phase1Results, u2Phase2Results);

        printDetailsOfCrashedRockets();
    }

    private static void printDetailsOfCrashedRockets() {

        printLine(true, "Details of crashed rockets:");
        printLine(true,"_".repeat(87) );
        String list = "|Rocket:  %5s |cost: %2s |cargo weight: %5s |tot weight: %5s |no of rockets: %2s |\n";

        int u1Garbage = 0;
        int u2Garbage = 0;

        if (Simulation.crashedRockets.size() > 0) {
            for (Rocket r : Simulation.crashedRockets.keySet()) {
                System.out.printf(list, r.getClass().getSimpleName() + "_" + r.getId(), r.getRocketCostUSD(), r.currentWeight, (r.getRocketWeightKg() + r.currentWeight), Simulation.crashedRockets.get(r));
                if (r instanceof U1) {
                    u1Garbage += (r.getRocketWeightKg() + r.currentWeight);
                } else if (r instanceof U2) {
                    u2Garbage += (r.getRocketWeightKg() + r.currentWeight);
                } else {
                    printLine(false," what kind of rocket was crashed? ");
                }
            }
        } else {
            printLine(false,"No rocket was crashed ");
        }
        printLine(true,"_".repeat(87) );
        printLine(true, "Total garbage ");
        printLine(false,"U1: "+u1Garbage+"\n"+"U2: "+u2Garbage);
    }

      private static void printLine(boolean colour, String text) {
        if (colour) {
            System.out.println(ANSI_YELLOW + text + ANSI_RESET);
        } else {
            System.out.println(text);
        }
    }


    private static void printTotalTransported( int[] u1Phase1Results, int[] u1Phase2Results, int[] u2Phase1Results, int[] u2Phase2Results) {
        System.out.println(ANSI_YELLOW +"Total weight of equipment transported to the Moon:\n\n"+ ANSI_RESET +
                "Phase 1 "+"U1: "+ u1Phase1Results[3]+" U2:"+ u2Phase1Results[3]+"\n"+
                "Phase 2 "+"U1: "+ u1Phase2Results[3]+" U2:"+ u2Phase2Results[3]+"\n");
        printLine(true,"_".repeat(87) );
    }

    private static void printPhasesOverview(int[] u1Phase1Results, int[] u1Phase2Results, int[] u2Phase1Results, int[] u2Phase2Results) {
        printLine(false,"\n\n");
        printLine(true, "-".repeat(40) + "OVERVIEW" + "-".repeat(39));
        System.out.printf("|Phase  | %37s |%37s |%n", "*********** Rocket U1 *************",  "********** Rocket U2 **********");
        System.out.printf("|       |  %4s | %13s | %13s | %4s | %13s | %13s |%n", "Cost", "Tot launched", "incl crashed", "Cost", "Tot launched", "incl crashed");
        printLine(false,"_".repeat(87) );
        System.out.printf("|Phase 1|  %4d | %13d | %13d | %4d | %13d | %13d |%n", u1Phase1Results[0], u1Phase1Results[1], u1Phase1Results[2], u2Phase1Results[0], u2Phase1Results[1], u2Phase1Results[2]);
        System.out.printf("|Phase 2|  %4d | %13d | %13d | %4d | %13d | %13d |%n", u1Phase2Results[0], u1Phase2Results[1], u1Phase2Results[2], u2Phase2Results[0], u2Phase2Results[1], u2Phase2Results[2]);
        printLine(false,"_".repeat(87) );
        System.out.printf("|Total  |  %4d | %13d | %13d | %4d | %13d | %13d |%n", u1Phase2Results[0]+ u1Phase2Results[0], u1Phase1Results[1]+ u1Phase2Results[1], u1Phase1Results[2]+ u1Phase2Results[2], u2Phase1Results[0]+ u2Phase2Results[0], u2Phase1Results[1]+ u2Phase2Results[1], u2Phase1Results[2]+ u2Phase2Results[2]);
        printLine(true,"_".repeat(87) );
    }
}