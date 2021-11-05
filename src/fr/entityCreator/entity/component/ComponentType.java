package fr.entityCreator.entity.component;


import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.ai.AICreator;
import fr.entityCreator.entity.component.animation.AnimationCreator;
import fr.entityCreator.entity.component.collision.*;
import fr.entityCreator.entity.component.light.LightCreator;
import fr.entityCreator.entity.component.particle.ParticleCreator;
import fr.entityCreator.frame.ComponentListPanel;

import java.io.BufferedReader;

public enum ComponentType {
    COLLISION_COMPONENT("Collision Component",new CollisionCreator(), 0,0),
    PARTICLE_COMPONENT("Particle Component", new ParticleCreator(), 1,7),
    AI_COMPONENT("AI Component", new AICreator(), 2,1),
    ANIMATED_COMPONENT("Animated Component", new AnimationCreator(), 3,0),
    LIGHT_COMPONENT("Light Component", new LightCreator(),4,4);
    private final String name;
    private final ComponentCreator creator;
    private final int id;
    private final int nbLine;

    ComponentType(String name, ComponentCreator creator, int id,int nbLine) {
        this.name = name;
        this.creator = creator;
        this.id = id;
        this.nbLine = nbLine;
    }


    public Component loadComponent(Entity e, String component) {
        return this.creator.loadComponent(component, e);
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
        if (component instanceof CollisionComponent){
            currentEntity.setCollision((CollisionComponent) component);
        }
        currentEntity.addComponent(component);
        panel.addComponent(component);
    }

    public int getNbLine() {
        return nbLine;
    }


    public int getId() {
        return id;
    }
}
