package fr.ostix.entities;

import fr.entityCreator.entity.*;

import java.util.*;

public class Entities {

    public static List<Class<? extends Entity>> getEntities() {
        List<Class<? extends Entity>> entities = new ArrayList<>();
        entities.add(Entity.class);
        entities.add(Shop.class);
        entities.add(NPC.class);
        return entities;
    }

    public static Class<? extends Entity> getClass(String name) {
        switch (name) {
            case "Shop":
                return Shop.class;
            case "NPC":
                return NPC.class;
            default:
                return Entity.class;
        }
    }
}
