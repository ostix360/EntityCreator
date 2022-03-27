package fr.ostix.entities;

import fr.entityCreator.entity.*;

public class NPC extends Entity {
    public NPC(String name) {
        super(name);
        this.type = "NPC";
    }

    @Override
    public String toString() {
        return "NPC";
    }
}
