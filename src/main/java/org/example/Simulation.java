package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Simulation {
    int repeatNo = 87;
    public static Map<Rocket, Integer> crashedRockets = new HashMap<>();
    String loading = "Loading X Rockets";
    String allStars = "*".repeat(repeatNo);
    String simulation = "Simulation run";
    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_PURPLE = "\u001B[35m";
    String purpleStar = ANSI_PURPLE + "*" + ANSI_RESET;
    String done = ANSI_PURPLE + ">>>" + ANSI_RESET;


    ArrayList<Item> loadItems(int phaseNo, String rocketType) throws FileNotFoundException {
        System.out.println(ANSI_YELLOW + allStars + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "P H A S E  N O : " + phaseNo + " for " + rocketType + ANSI_RESET);
        System.out.println(ANSI_YELLOW + allStars + ANSI_RESET);
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
        System.out.println(ANSI_PURPLE + loading.replace("X", "U1") + ANSI_RESET);

        U1 u1 = new U1(Rocket.getCount());
        System.out.println(purpleStar+"\t"+u1.getClass().getSimpleName()+"_" + u1.getId() + " loading");

        return loadingRockets(u1, items);
    }

    ArrayList<Rocket> loadU2(ArrayList<Item> items) {
        System.out.println(ANSI_PURPLE + loading.replace("X", "U2") + ANSI_RESET);
        U2 u2 = new U2(Rocket.getCount());
        System.out.println(purpleStar+"\tU2_id=" + u2.getId() + " loading");

        return  loadingRockets(u2, items);
    }

    ArrayList<Rocket> loadingRockets(Rocket rocket, ArrayList<Item> items) {
        Iterator<Item> iterator = items.iterator();
        ArrayList<Rocket> rockets = new ArrayList<>();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (rocket.canCarry(item)) {
                System.out.println(purpleStar+"\t\t " +item.name+": "+ item.weight + " kg");
                rocket.carry(item);
            } else {
                rockets.add(rocket);
                System.out.println(done+"\t\t"+rocket.getClass().getSimpleName()+"_" + rocket.getId() + " loaded " + " total weight " + (rocket.currentWeight + rocket.getRocketWeightKg()));
                if (rocket instanceof U1) {
                    rocket = new U1(Rocket.getCount());
                } else {
                    rocket = new U2(Rocket.getCount());
                }
                System.out.println(purpleStar+"\t"+rocket.getClass().getSimpleName()+"_" + rocket.getId() + " loading");
                rocket.carry(item);
                System.out.println(purpleStar+"\t\t " +item.name+": "+ item.weight + " kg");
            }
        }
        System.out.println(done+"\t\t"+rocket.getClass().getSimpleName()+"_" + rocket.getId() + " loaded " + " total weight " + (rocket.currentWeight + rocket.getRocketWeightKg()));
        rockets.add(rocket);
        System.out.println(ANSI_PURPLE + loading.replace("X", rocket.getClass().getSimpleName()) + " finished\n" + ANSI_RESET);

        return rockets;
    }

    public int[] runSimulation(ArrayList<Rocket> rockets) {
        System.out.println(ANSI_PURPLE + simulation + ANSI_RESET);
        int totalWeight = 0;
        int counter = 0; //failed trials of launch/land
        int costOfRocket = rockets.get(0).getRocketCost$();
        for (Rocket rocket : rockets) {
            int rocketCrashes = launchRocket(rocket, 0);
            counter += rocketCrashes;
            totalWeight += rocket.currentWeight;
        }
        System.out.println(ANSI_PURPLE + simulation + " " + "finished\n" + ANSI_RESET);
        int budget = costOfRocket * (rockets.size() + counter);
        return new int[]{budget, (rockets.size() + counter), counter, totalWeight};
    }
    private int launchRocket(Rocket r, int counter) {

        System.out.print("\t" + r.getClass().getSimpleName() + "_" + r.getId() + " launched " + (counter > 0 ? "again" : ""));
        if (!r.launch()) {
            counter++;

            crashedRockets.put(r, crashedRockets.getOrDefault(r,0)+1);

            System.out.print(" ... rocked exploaded at launching ...replaced with new rocket ");
            return launchRocket(r, counter);
        } else if (r.land()) {
            System.out.print("...successfully launched...");
            System.out.println("....successfully landed " + "\n\t\tcrash count for this rocket " + counter);
            return counter;
        }

        crashedRockets.put(r, crashedRockets.getOrDefault(r,0)+1);

        System.out.print("...successfully launched...");
        counter++;
        System.out.print("...rocked exploaded at landing replaced with new rocket. ");
        return launchRocket(r, counter);
    }
}
