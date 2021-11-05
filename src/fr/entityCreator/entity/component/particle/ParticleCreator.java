package fr.entityCreator.entity.component.particle;


import fr.entityCreator.core.Timer;
import fr.entityCreator.core.loader.*;
import fr.entityCreator.core.loader.json.JsonUtils;
import fr.entityCreator.core.resources.*;
import fr.entityCreator.core.resourcesProcessor.*;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentCreator;
import fr.entityCreator.graphics.particles.*;
import fr.entityCreator.graphics.particles.particleSpawn.*;
import fr.entityCreator.graphics.textures.*;
import fr.entityCreator.toolBox.*;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.util.*;

public class ParticleCreator implements ComponentCreator {
    @Override
    public Component createComponent(Entity entity) {
        return new ParticleComponent(ParticleSystem.DEFAULT,entity);
    }

    @Override
    public Component loadComponent(String component, Entity entity) {
        ParticleSystem system = null;
        String[] lines = component.split("\n");


        String json = JsonUtils.loadJson(Config.OUTPUT_FOLDER.getAbsolutePath() + "/textures/data/" + lines[lines.length - 1]+ ".json");
        TextureResources texResources = JsonUtils.gsonInstance(false).fromJson(json, TextureResources.class);
        TextureLoaderRequest textureRequest = new TextureLoaderRequest(texResources.getPath());
        GLRequestProcessor.sendRequest(textureRequest);
        String[] values;
        try {
            values = lines[0].split(";");
            system = new ParticleSystem( Float.parseFloat(values[0]), Float.parseFloat(values[1]),
                    Float.parseFloat(values[2]), Float.parseFloat(values[3]),
                    Float.parseFloat(values[4]));
            values = lines[1].split(";");
            setError(system, values);
            values = lines[2].split(";");
            Vector3f offset = new Vector3f(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));
            system.setPositionOffset(offset);
            values = lines[3].split(";");
            setDirection(system, values);
            values = lines[4].split(";");
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
            String line = lines[5];
            if (!Objects.equals(line, "")) {
                ParticleTargetProperties prop = JsonUtils.gsonInstance(false).fromJson(line, ParticleTargetProperties.class);
                system.setTarget(new ParticleTarget(prop, entity));
            }
        } catch (Exception e) {
            Logger.err("Failed to load Particle Component ");
            e.printStackTrace();
        }
        Timer.waitForRequest(textureRequest);
        Texture tex = new Texture(textureRequest.getTexture(),texResources.getTextureProperties());
        ParticleTexture texture = new ParticleTexture(tex.getTextureLoader(), tex.getNumbersOfRows(), tex.isAdditive(),tex.isAffectedByLighting());

        return new ParticleComponent(system, entity);
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
