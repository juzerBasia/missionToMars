package org.example;

import java.util.Random;

public class U1 extends Rocket {

    public U1(int id) {
        super(120, 10000, 18000, 0.05, 0.01, id);
    }

    @Override
    public boolean launch() {
        double random = getRandom();
        double rocketCrash = this.getChanceLaunchExpl() * 1.0 * this.getRocketWeightKg() / (this.getMaxWeightKg() - this.getRocketWeightKg());
        return rocketCrash < random;
    }

    @Override
    public boolean land() {
        double random = getRandom();
        double rocketCrash = this.getChangeLandExpl() * 1.0 * this.getRocketWeightKg() / (this.getMaxWeightKg() - this.getRocketWeightKg());
        return rocketCrash < random;
    }

    public double getRandom() {
        Random r = new Random();
        return r.nextDouble();
    }

}
