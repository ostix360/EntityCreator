package fr.entityCreator.entity.component.particle;


import fr.entityCreator.core.loader.json.JsonUtils;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentCreator;
import fr.entityCreator.graphics.particles.ParticleSystem;
import fr.entityCreator.graphics.particles.ParticleTarget;
import fr.entityCreator.graphics.particles.ParticleTargetProperties;
import fr.entityCreator.graphics.particles.particleSpawn.*;
import fr.entityCreator.toolBox.Logger;
import org.joml.Vector3f;

import java.io.BufferedReader;

public class ParticleCreator implements ComponentCreator {
    @Override
    public Component createComponent(Entity entity) {
        return new ParticleComponent(ParticleSystem.DEFAULT,entity);
    }

    @Override
    public Component loadComponent(BufferedReader reader, Entity entity) {
        ParticleSystem system = ParticleSystem.DEFAULT;
        String[] values;
        try{
            values = reader.readLine().split(";");
            system = new ParticleSystem(values[0],values[1],values[2],values[3],values[4]);
            values = reader.readLine().split(";");
            setError(system,values);
            values = reader.readLine().split(";");
            Vector3f offset = new Vector3f(Float.parseFloat(values[0]),Float.parseFloat(values[1]),Float.parseFloat(values[2]));
            system.setPositionOffset(offset);
            values = reader.readLine().split(";");
            setDirection(system,values);
            values = reader.readLine().split(";");
            int id = Integer.parseInt(values[0]);
            ParticleSpawn spawn;
            switch (id) {
                case 0:
                    spawn = new Circle();
                    break;
                case 1:
                    spawn = new Line();
                    break;
                case 3:
                    spawn = new Sphere();
                    break;
                default:
                    spawn = new Point();
            }
            spawn.load(values);
            String line;
            if ((line = reader.readLine()) != ""){
                ParticleTargetProperties prop = JsonUtils.gsonInstance(false).fromJson(line, ParticleTargetProperties.class);
                system.setTarget(new ParticleTarget(prop,entity));
            }
        }catch(Exception e){
            Logger.err("Failled to load Particle Component from " + entity.getModel().getName() + " ");
            e.printStackTrace();
        }
        return new ParticleComponent(system,entity);
    }

    private void setError(ParticleSystem system,String[] values){
        system.setRandomRotation(Boolean.parseBoolean(values[0]));
        system.setLifeError(Float.parseFloat(values[1]));
        system.setScaleError(Float.parseFloat(values[2]));
        system.setSpeedError(Float.parseFloat(values[3]));
    }

    private void setDirection(ParticleSystem system, String[] values){
        Vector3f direction = new Vector3f(Float.parseFloat(values[0]),Float.parseFloat(values[1]),Float.parseFloat(values[2]));
        system.setDirection(direction);
        system.setDirectionDeviation(Float.parseFloat(values[3]));
    }
}
