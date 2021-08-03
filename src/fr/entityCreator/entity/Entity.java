package fr.entityCreator.entity;


import fr.entityCreator.core.Timer;
import fr.entityCreator.core.loader.Loader;
import fr.entityCreator.core.loader.OBJFileLoader;
import fr.entityCreator.core.loader.TextureLoaderRequest;
import fr.entityCreator.core.resources.collision.maths.Vector3;
import fr.entityCreator.core.resourcesProcessor.GLRequest;
import fr.entityCreator.core.resourcesProcessor.GLRequestProcessor;
import fr.entityCreator.entity.animated.animation.animatedModel.AnimatedModel;
import fr.entityCreator.entity.animated.animation.loaders.AnimatedModelLoader;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.collision.CollisionComponent;
import fr.entityCreator.entity.component.particle.ParticleComponent;
import fr.entityCreator.frame.ErrorPopUp;
import fr.entityCreator.graphics.model.MeshModel;
import fr.entityCreator.graphics.model.Model;
import fr.entityCreator.graphics.model.Texture;
import fr.entityCreator.graphics.textures.TextureLoader;
import fr.entityCreator.toolBox.ToolDirectory;
import org.joml.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Entity {

    protected final Vector3 forceToCenter = new Vector3();
    protected final Vector3 torque = new Vector3();
    private Model model;
    protected Vector3f position;
    protected Vector3f rotation;
    protected float scale;
    private Transform transform;
    protected MovementType movement;
    private CollisionComponent collision;
    private String name;

    private final List<Component> components = new ArrayList<>();

    public Entity(Model model, Vector3f position, Vector3f rotation, float scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation.add(0, 0, 0);
        this.scale = scale;
        this.transform = new Transform(position, rotation, scale);
    }

    public Entity(String name) {
        this.name = name;
        this.position = new Vector3f(200, 0, 200);
        this.rotation = new Vector3f(0);
        this.scale = 1;
        this.transform = new Transform(position, rotation, scale);
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public CollisionComponent getCollision() {
        return collision;
    }

    public void setCollision(CollisionComponent collision) {
        this.collision = collision;
        this.components.add(collision);
    }

    public void addComponent(Component c) {
        components.add(c);
    }

    public void update() {
        for (Component c : components) {
            if (c instanceof ParticleComponent) {
                ((ParticleComponent) c).setOffset(new Vector3f(0, 8.5f, 0));
            }
            c.update();
        }
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void increasePosition(Vector3f value) {
        position.add(value);
    }

    public void increaseRotation(Vector3f value) {
        rotation.add(value);
        //transform.setRotation(rotation);
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public Transform getTransform() {
        transform.setRotation(rotation);
        transform.setPosition(position);
        transform.setScale(scale);
        return transform;
    }

    public MovementType getMovement() {
        return movement;
    }

    public void setMovement(MovementType movement) {
        this.movement = movement;
    }

    public Vector3 getForceToCenter() {
        return forceToCenter;
    }


    public Vector3 getTorque() {
        return torque.multiply(100);
    }

    public void exportAllComponents() throws IOException {
        try(FileOutputStream fos = openSave()) {
            for (Component c : components) {
                c.export(fos);
            }
        }
    }

    public List<Component> getComponents() {
        return components;
    }

    private FileOutputStream openSave() throws IOException {
        File file = new File(ToolDirectory.OUTPUT_FOLDER + "/component/"
                ,hashCode() + ".component");
        if (!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        return new FileOutputStream(file);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(model, entity.model) && Objects.equals(collision, entity.collision) && Objects.equals(components, entity.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collision, components);
    }

    public boolean hasModel() {
        return this.model != null;
    }

    public void setModelFile(File file) {
        if (file.exists() && file.canRead()) {
            if (file.getName().endsWith(".dae")) {
                AnimatedModelLoader.loadEntity(file.getAbsolutePath(), 5, this);
            } else if (file.getName().endsWith(".obj")) {
                OBJFileLoader.loadModel(file.getAbsolutePath(), this);
            }
        } else {
            new ErrorPopUp("impossible de charger le model");
        }
    }

    public void setTexturedFile(File file) {
        if (file.exists() && file.canRead()) {
            GLRequest request = new TextureLoaderRequest(file.getAbsolutePath(), this);
            GLRequestProcessor.sendRequest(request);
            Timer.waitForRequest(request);
        }
    }

    public String getName() {
        return name;
    }

    public enum MovementType {
        FORWARD("run"),
        BACK("back"),
        JUMP("jump"),
        STATIC("staying");
        String id;

        MovementType(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }


}
