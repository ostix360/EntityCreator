package fr.entityCreator.entity.component.light;

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
    public Component loadComponent(String component, Entity entity) {
        String[] lines = component.split("\n");
        Vector3f pos;
        Color color;
        Vector3f att;
        float power;
        String[] values = lines[0].split(";");
        pos = new Vector3f(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));
        values = lines[1].split(";");
        color = new Color(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));
        values = lines[2].split(";");
        att = new Vector3f(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));
        power = Float.parseFloat(lines[3]);
        return new Light(pos, color, power, att);
    }
}
