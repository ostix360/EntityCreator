package fr.entityCreator.entity.component.collision;

import fr.entityCreator.entity.*;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentCreator;



public class CollisionCreator implements ComponentCreator {
    @Override
    public Component createComponent(Entity entity) {
        return new CollisionComponent(entity, new CollisionProperty());
    }

    @Override
    public Component loadComponent(String component, Entity entity) {
        String[] lines = component.split("\n");
        if (!lines[0].equalsIgnoreCase("Collision Component")) {
            return null;
        }
        CollisionProperty prop = new CollisionProperty();
        try {
            prop.setControllerType(lines[1]);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new CollisionComponent(entity, prop);


    }


}
