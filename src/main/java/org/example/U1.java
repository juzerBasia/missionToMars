package org.example;

import java.util.Random;

public class U1 extends Rocket {
    Random r = new Random();

    public U1(int id) {
        super(120, 10000, 18000, 0.05, 0.01, id);
    }

    @Override
    public boolean launch() {
        double random = getRandom();
        return this.getChanceLaunchExpl() * getPartOfCalc() < random;
    }

    @Override
    public boolean land() {
        double random = getRandom();
        return this.getChangeLandExpl() * getPartOfCalc() < random;
    }

    public double getRandom() {
        return r.nextDouble();
    }


}
