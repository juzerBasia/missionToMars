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
        int[] u1Phase1Cost = simulation.runSimulation(u1Rockets);

        ArrayList<Item> u1Phase2 = simulation.loadItems(2, "U1");
        ArrayList<Rocket> u1RocketsP2 = simulation.loadU1(u1Phase2);
        int[] u1Phase2Cost = simulation.runSimulation(u1RocketsP2);


        ArrayList<Item> u2Phase1 = simulation.loadItems(1, "U2");
        ArrayList<Rocket> u2RocketsP1 = simulation.loadU2(u2Phase1);
        int[] u2Phase1Cost = simulation.runSimulation(u2RocketsP1);

        ArrayList<Item> u2Phase2 = simulation.loadItems(2, "U2");
        ArrayList<Rocket> u2RocketsP2 = simulation.loadU2(u2Phase2);
        int[] u2Phase2Cost = simulation.runSimulation(u2RocketsP2);

        System.out.println("\n\n");
        System.out.println(ANSI_YELLOW+"-".repeat(40) + "OVERVIEW" + "-".repeat(39)+ANSI_RESET);
        System.out.printf("|Phase  | %37s |%37s |\n", "*********** Rocket U1 *************",  "********** Rocket U2 **********");
        System.out.printf("|       |  %4s | %13s | %13s | %4s | %13s | %13s |\n", "Cost", "Tot launched", "incl crashed", "Cost", "Tot launched", "incl crashed");
        System.out.println("-".repeat(87));
        System.out.printf("|Phase 1|  %4d | %13d | %13d | %4d | %13d | %13d |\n", u1Phase1Cost[0], u1Phase1Cost[1], u1Phase1Cost[2], u2Phase1Cost[0], u2Phase1Cost[1], u2Phase1Cost[2]);
        System.out.printf("|Phase 2|  %4d | %13d | %13d | %4d | %13d | %13d |\n", u1Phase2Cost[0], u1Phase2Cost[1], u1Phase2Cost[2], u2Phase2Cost[0], u2Phase2Cost[1], u2Phase2Cost[2]);
        System.out.println("-".repeat(87));
        System.out.printf("|Total  |  %4d | %13d | %13d | %4d | %13d | %13d |\n", u1Phase2Cost[0]+u1Phase2Cost[0], u1Phase1Cost[1]+u1Phase2Cost[1], u1Phase1Cost[2]+u1Phase2Cost[2],u2Phase1Cost[0]+u2Phase2Cost[0], u2Phase1Cost[1]+ u2Phase2Cost[1],u2Phase1Cost[2]+ u2Phase2Cost[2]);
        System.out.println(ANSI_YELLOW+"-".repeat(87)+ANSI_RESET);
        System.out.println("\n\n");
//        System.out.println("Phase 1: " + "Rockets U1 Cost: " + u1Phase1Cost[0] + " Rockets U2 Cost: " + u2Phase1Cost[0]);
//        System.out.println("Phase 2: " + "Rockets U1 Cost: " + u1Phase2Cost[0] + " Rockets U2 Cost: " + u2Phase2Cost[0]);


    }
}