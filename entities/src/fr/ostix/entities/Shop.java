package fr.ostix.entities;

import fr.entityCreator.entity.*;

public class Shop extends Entity {
    public Shop(String name) {
        super(name);
        this.type = "Shop";
    }

    @Override
    public String toString() {
        return "Shop";
    }
}
