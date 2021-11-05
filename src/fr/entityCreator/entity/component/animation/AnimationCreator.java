package fr.entityCreator.entity.component.animation;

import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.animated.animation.animatedModel.*;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentCreator;

import java.io.BufferedReader;


public class AnimationCreator implements ComponentCreator {
    @Override
    public Component createComponent(Entity entity) {
        return new AnimationComponent(entity);
    }

    @Override
    public Component loadComponent(String component, Entity entity) {
        AnimatedModel model;
        if (entity.getModel() instanceof AnimatedModel) {
            model = (AnimatedModel) entity.getModel();
        } else {
            new Exception("Animation component couldn't be created because your entity's model can't be animated");
            return null;
        }
        return new AnimationComponent(entity);
    }
}
