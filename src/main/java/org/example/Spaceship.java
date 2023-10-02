package org.example;

public interface Spaceship {
    boolean launch();
    boolean land();

    boolean canCarry(Item item);

    void carry(Item item);

}
