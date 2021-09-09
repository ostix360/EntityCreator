package fr.entityCreator.entity.component.light;

import fr.entityCreator.core.collision.maths.Vector3;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentCreator;
import fr.entityCreator.toolBox.Color;
import org.joml.Vector3f;

import java.io.BufferedReader;

public class LightCreator implements ComponentCreator {

    @Override
    public Component createComponent(Entity entity) {
        return new Light(new Vector3f(), Color.SUN);
    }

    @Override
    public Component loadComponent(BufferedReader reader, Entity entity) {
        return null;
    }
}
