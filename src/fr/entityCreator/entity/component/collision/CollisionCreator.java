package fr.entityCreator.entity.component.collision;


import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentCreator;

import java.io.BufferedReader;

public class CollisionCreator implements ComponentCreator {
    @Override
    public Component createComponent(Entity entity) {
        return new CollisionComponent(entity,new CollisionProperties(false));
    }

    @Override
    public Component loadComponent(BufferedReader reader, Entity entity) {
        return null;
    }
}
