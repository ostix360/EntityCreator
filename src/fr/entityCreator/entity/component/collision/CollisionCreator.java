package fr.entityCreator.entity.component.collision;

import fr.entityCreator.entity.*;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentCreator;

import java.io.BufferedReader;
import java.util.*;

public class CollisionCreator implements ComponentCreator {
    @Override
    public Component createComponent(Entity entity) {
        return new CollisionComponent(entity);
    }

    @Override
    public Component loadComponent(String component, Entity entity) {
        String[] lines = component.split("\n");
        if (!lines[0].equalsIgnoreCase("Collision Component")) {
            return null;
        }
        return new CollisionComponent(entity);
    }


}
