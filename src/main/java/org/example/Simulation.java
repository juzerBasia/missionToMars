package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;



public class Simulation {
    int repeatNo = 87;
    String loading = "Loading X Rockets";
    String allStars = "*".repeat(repeatNo);
    String simulation = "Simulation run";
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_YELLOW = "\u001B[33m";

    ArrayList<Item> loadItems(int phaseNo, String rocketType) throws FileNotFoundException {
        System.out.println(ANSI_YELLOW+allStars+ANSI_RESET);
        System.out.println(ANSI_YELLOW+"P H A S E  N O : " + phaseNo + " for " + rocketType+ANSI_RESET);
        System.out.println(ANSI_YELLOW+allStars+ANSI_RESET);
        ArrayList<Item> items = new ArrayList<>();
        String phasePath = phaseNo == 1 ? "src/main/resources/phase-1.txt" : "src/main/resources/phase-2.txt";

        File file = new File(phasePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("=");
            Item item = new Item(line[0], Integer.parseInt(line[1]));
            items.add(item);
        }
        return items;
    }

    ArrayList<Rocket> loadU1(ArrayList<Item> items) {

        System.out.println(loading.replace("X", "U1"));
        ArrayList<Rocket> u1Rockets = new ArrayList<>();
        Iterator<Item> iterator = items.iterator();
        U1 u1 = new U1(Rocket.getCount());
        System.out.println("\tU1_id=" + u1.getId() + " loading");

        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (u1.canCarry(item)) {
                System.out.println("\t\t " + item.weight+" kg");
                u1.carry(item);
            } else {
                u1Rockets.add(u1);
                System.out.println("\t\tU1_id=" + u1.getId() + " loaded " + " total weight " + (u1.currentWeight + u1.getRocketWeightKg()));
                u1 = new U1(Rocket.getCount());
                System.out.println("\tU1_id=" + u1.getId() + " loading");
            }
        }
        System.out.println("\t\tU1_id=" + u1.getId() + " loaded " + " total weight " + (u1.currentWeight + u1.getRocketWeightKg()));
        u1Rockets.add(u1);
        System.out.println(loading.replace("X", "U1") + " finished\n");
        return u1Rockets;
    }

    ArrayList<Rocket> loadU2(ArrayList<Item> items){
        System.out.println(loading.replace("X", "U2"));

        ArrayList<Rocket> u2Rockets = new ArrayList<>();
        Iterator<Item> iterator = items.iterator();
        U2 u2 = new U2(Rocket.getCount());
        System.out.println("\tU2_id=" + u2.getId() + " loading");

        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (u2.canCarry(item)) {
                System.out.println("\t\t " + item.weight);
                u2.carry(item);
            } else {
                u2Rockets.add(u2);
                System.out.println("\t\tU2_id=" + u2.getId() + " loaded " + " total weight " + (u2.currentWeight + u2.getRocketWeightKg()));
                u2 = new U2(Rocket.getCount());
                System.out.println("\tU2_id=" + u2.getId() + " loading");
            }
        }
        System.out.println("\t\tU2_id=" + u2.getId() + " loaded " + " total weight " + (u2.currentWeight + u2.getRocketWeightKg()));
        u2Rockets.add(u2);
        System.out.println(loading.replace("X", "U2") + " finished\n");
        return u2Rockets;
    }

    public int[] runSimulation(ArrayList<Rocket> rockets) {
        System.out.println(simulation);
        
        int counter = 0; //failed trials of launch/land
        int costOfRocket = rockets.get(0).getRocketCost$();
        for (Rocket r : rockets) {
            int rocketCrashes= launchRocket(r, 0);
            counter += rocketCrashes;
        }
        System.out.println(simulation + " " + "finished\n");
             int budget = costOfRocket * (rockets.size() + counter);
        return new int[]{budget, (rockets.size() + counter), counter};
    }

    private int launchRocket(Rocket r, int counter) {

        System.out.print("\t" + r.getClass().getSimpleName() + "_" + r.getId() + " launched " + (counter > 0 ? "again" : ""));
        if (!r.launch()) {
            counter++;
            System.out.print(" ... rocked exploaded at launching ...replaced with new rocket ");
            return launchRocket(r, counter);
        } else if (r.land()) {
            System.out.print("...successfully launched...");
            System.out.println("....successfully landed "+"\n\t\tcrash count for this rocket "+counter);
            return counter;
        }
        System.out.print("...successfully launched...");
        counter++;
        System.out.print("...rocked exploaded at landing replaced with new rocket. ");
        return launchRocket(r,counter);
    }
}
