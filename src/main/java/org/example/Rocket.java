package org.example;


public class Rocket implements Spaceship {

    private final int rocketCostUSD;
    private int rocketWeightKg;
    private final int maxWeightKg;
    private final double chanceLaunchExpl;
    private final double changeLandExpl;
    int currentWeight = rocketWeightKg;

     static int count =1;
    private final int id;

    public Rocket(int rocketCostUSD, int rocketWeightKg, int maxWeightKg, double chanceLaunchExpl, double changeLandExpl, int id) {

        this.rocketCostUSD = rocketCostUSD;
        this.rocketWeightKg = rocketWeightKg;
        this.maxWeightKg = maxWeightKg;
        this.chanceLaunchExpl = chanceLaunchExpl;
        this.changeLandExpl = changeLandExpl;
        this.id = id;
        count++;
    }

    public static int getCount() {
        return count;
    }

    public int getRocketCostUSD() {
        return rocketCostUSD;
    }

    public int getRocketWeightKg() {
        return rocketWeightKg;
    }

    public int getMaxWeightKg() {
        return maxWeightKg;
    }

    public double getChanceLaunchExpl() {
        return chanceLaunchExpl;
    }

    public int getId() {
        return id;
    }

    public double getChangeLandExpl() {
        return changeLandExpl;
    }


    @Override
    public boolean launch() {
        return false;
    }

    @Override
    public boolean land() {
        return false;
    }


    @Override
    public boolean canCarry(Item item) {
        return (getMaxWeightKg() - getRocketWeightKg() - this.currentWeight) >= item.weight;
    }


    @Override
    public void carry(Item item) {
        this.currentWeight += item.weight;

    }
    public double getPartOfCalc() {
        return 1.00*this.getRocketWeightKg() / (this.getMaxWeightKg() - this.getRocketWeightKg());
    }

}
