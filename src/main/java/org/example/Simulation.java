package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Simulation {
    int repeatNo = 87;
    static Map<Rocket, Integer> crashedRockets = new HashMap<>();

    String allStars = "*".repeat(repeatNo);
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_PURPLE = "\u001B[35m";
    String purpleStar = ANSI_PURPLE + "*" + ANSI_RESET;
    String done = ANSI_PURPLE + ">>>" + ANSI_RESET;

    ArrayList<Item> loadItems(int phaseNo, String rocketType) throws FileNotFoundException {
        printPhaseInfo(phaseNo, rocketType);
        ArrayList<Item> items = new ArrayList<>();
        String phasePath = phaseNo == 1 ? "src/main/resources/phase-1.txt" : "src/main/resources/phase-2.txt";
        File file = new File(phasePath);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split("=");
                Item item = new Item(line[0], Integer.parseInt(line[1]));
                items.add(item);
            }
        } catch (Exception e) {
            throw new FileNotFoundException();
        }
        return items;

    }

    public List<Rocket> load(List<Item> items, String rocketType) {
        printLoadingInfo(rocketType);
        if (Objects.equals(rocketType, "U1")) {
            U1 rocket = new U1(Rocket.getCount());
            return loadingRockets(rocket, items);
        } else {
            U2 rocket = new U2(Rocket.getCount());
            return loadingRockets(rocket, items);
        }
    }

    List<Rocket> loadingRockets(Rocket rocket, List<Item> items) {
        loadingMessage(rocket);
        Iterator<Item> iterator = items.iterator();
        ArrayList<Rocket> rockets = new ArrayList<>();

        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (rocket.canCarry(item)) {
                loadedItemMessage(item);
                rocket.carry(item);
            } else {
                rockets.add(rocket);
                loadingDoneMessage(rocket);
                if (rocket instanceof U1) {
                    rocket = new U1(Rocket.getCount());
                } else {
                    rocket = new U2(Rocket.getCount());
                }
                loadingMessage(rocket);
                rocket.carry(item);
                loadedItemMessage(item);
            }
        }
        loadingDoneMessage(rocket);
        rockets.add(rocket);

        return rockets;
    }

    public String getRocketDetail(Rocket rocket) {
        return rocket.getClass().getSimpleName() + "_" + rocket.getId();
    }

    public int[] runSimulation(List<Rocket> rockets) {
        System.out.println(ANSI_PURPLE + "simulation run " + ANSI_RESET);
        int totalWeight = 0;
        int counter = 0; //failed trials of launch/land
        int costOfRocket = rockets.get(0).getRocketCostUSD();
        for (Rocket rocket : rockets) {
            int rocketCrashes = launchRocket(rocket, 0);
            counter += rocketCrashes;
            totalWeight += rocket.currentWeight;
        }
        System.out.println(ANSI_PURPLE + "simulation run finished\n" + ANSI_RESET);
        int budget = costOfRocket * (rockets.size() + counter);
        return new int[]{budget, (rockets.size() + counter), counter, totalWeight};
    }

    private int launchRocket(Rocket r, int counter) {
        System.out.print("\t" + r.getClass().getSimpleName() + "_" + r.getId() + " launched " + (counter > 0 ? "again" : ""));

        if (!r.launch()) {
            counter++;
            crashedRockets.put(r, crashedRockets.getOrDefault(r, 0) + 1);
            System.out.print(" ... rocked exploaded at launching ...replaced with new rocket ");
            return launchRocket(r, counter);

        } else if (r.land()) {
            System.out.println("...successfully launched.......successfully landed " + "\n\t\tcrash count for this rocket " + counter);
            return counter;
        }

        crashedRockets.put(r, crashedRockets.getOrDefault(r, 0) + 1);

        System.out.print("...successfully launched......rocked exploaded at landing replaced with new rocket. ");
        counter++;
        return launchRocket(r, counter);
    }

    public void loadingMessage(Rocket rocket) {
        System.out.println(purpleStar + "\t" + getRocketDetail(rocket) + " loading");
    }

    public void printPhaseInfo(int phaseNo, String rocketType) {
        System.out.println(ANSI_YELLOW + allStars + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "P H A S E  N O : " + phaseNo + " for " + rocketType + ANSI_RESET);
        System.out.println(ANSI_YELLOW + allStars + ANSI_RESET);
    }

    public void printLoadingInfo(String rocketType) {
        System.out.println(ANSI_PURPLE + "loading " + rocketType + ANSI_RESET);
    }

    public void loadingDoneMessage(Rocket rocket) {
        System.out.println(done + "\t\t" + getRocketDetail(rocket) + " loaded, total weight " + (rocket.currentWeight + rocket.getRocketWeightKg()));
    }

    public void loadedItemMessage(Item item) {
        System.out.println(purpleStar + "\t\t " + item.name + ": " + item.weight + " kg");
    }
}
