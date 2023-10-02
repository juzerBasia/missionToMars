package org.example;

import java.util.Random;

public class U2 extends Rocket{

    public U2(int id) {
        super( 100, 18000, 29000, 0.04, 0.08,id);

      }


    @Override
    public boolean launch() {
        double random = getRandom();
        double rocketCrash = this.getChanceLaunchExpl() * 1.0 * this.getRocketWeightKg() / (this.getMaxWeightKg() - this.getRocketWeightKg());
        return rocketCrash<random;
    }

    @Override
    public boolean land() {
        double random = getRandom();
        double rocketCrash = this.getChangeLandExpl() * 1.0 * this.getRocketWeightKg() / (this.getMaxWeightKg() - this.getRocketWeightKg());
        return rocketCrash<random;
    }


    public double getRandom() {
        Random r = new Random();
        return r.nextDouble() ;
    }


}
