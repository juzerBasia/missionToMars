package org.example;

import java.util.Random;

public class U2 extends Rocket{
    Random r = new Random();
    public U2(int id) {
        super( 100, 18000, 29000, 0.04, 0.08,id);
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
        return r.nextDouble() ;
    }

}
