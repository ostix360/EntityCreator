package fr.entityCreator.entity.component;


import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.ai.AICreator;
import fr.entityCreator.entity.component.animation.AnimationCreator;
import fr.entityCreator.entity.component.collision.CollisionCreator;
import fr.entityCreator.entity.component.light.LightCreator;
import fr.entityCreator.entity.component.particle.ParticleCreator;
import fr.entityCreator.frame.ComponentListPanel;

import java.io.BufferedReader;

public enum ComponentType {
    COLLISION_COMPONENT("Collision Component",new CollisionCreator(), 0),
    PARTICLE_COMPONENT("Particle Component", new ParticleCreator(), 1),
    AI_COMPONENT("AI Component", new AICreator(), 2),
    ANIMATED_COMPONENT("Animated Component", new AnimationCreator(), 3),
    LIGHT_COMPONENT("Light Component", new LightCreator(),4);
    private final String name;
    private final ComponentCreator creator;
    private final int id;

    ComponentType(String name, ComponentCreator creator, int id) {
        this.name = name;
        this.creator = creator;
        this.id = id;
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
        currentEntity.addComponent(component);
        panel.addComponent(component);
    }

    public int getId() {
        return id;
    }
}
