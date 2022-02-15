package fr.ostix.entities;

import fr.entityCreator.entity.*;

import java.util.*;

public class Entities {

    public static List<Class<? extends Entity>> getEntities(){
        List<Class<? extends Entity>> entities = new ArrayList<>();
        entities.add(Entity.class);
        entities.add(Shop.class);
        return entities;
    }
}
