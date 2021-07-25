package fr.entityCreator.entity.component;


import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.ai.AICreator;
import fr.entityCreator.entity.component.animation.AnimationCreator;
import fr.entityCreator.entity.component.collision.CollisionCreator;
import fr.entityCreator.entity.component.particle.ParticleCreator;
import fr.entityCreator.frame.ComponentListPanel;

import java.io.BufferedReader;

public enum ComponentType {
    COLLISION_COMPONENT("Collision Component",new CollisionCreator()),
    PARTICLE_COMPONENT("Particle Component", new ParticleCreator()),
    AI_COMPONENT("AI Component", new AICreator()),
    ANIMATED_COMPONENT("Animated Component", new AnimationCreator());
    private final String name;
    private final ComponentCreator creator;

    ComponentType(String name, ComponentCreator creator) {
        this.name = name;
        this.creator = creator;
    }

    public Component loadComponent(BufferedReader reader ,Entity e){
        return this.creator.loadComponent(reader, e);
    }

    public Component createComponentToEntity(Entity e) {
        return this.creator.createComponent(e);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void createComponent(Entity currentEntity, ComponentListPanel panel) {
        Component component = this.creator.createComponent(currentEntity);
        panel.addComponent(component);
    }
}
