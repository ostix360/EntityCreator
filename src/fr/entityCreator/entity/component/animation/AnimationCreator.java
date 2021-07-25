package fr.entityCreator.entity.component.animation;

import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentCreator;

import java.io.BufferedReader;


public class AnimationCreator implements ComponentCreator {
    @Override
    public Component createComponent(Entity entity) {
        return new AnimationComponent(entity);
    }

    @Override
    public Component loadComponent(BufferedReader reader, Entity entity) {
        return new AnimationComponent(entity);
    }
}
