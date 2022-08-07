package fr.entityCreator.entity;


import fr.entityCreator.core.Timer;
import fr.entityCreator.core.loader.*;
import fr.entityCreator.core.loader.json.*;
import fr.entityCreator.core.resourcesProcessor.*;
import fr.entityCreator.entity.animated.animation.loaders.*;
import fr.entityCreator.entity.component.*;
import fr.entityCreator.entity.component.collision.*;
import fr.entityCreator.entity.component.particle.*;
import fr.entityCreator.frame.*;
import fr.entityCreator.graphics.model.*;
import fr.entityCreator.toolBox.*;
import org.joml.*;

import javax.management.openmbean.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;


public class Entity {

    private Model model;
    protected Vector3f position;
    protected Vector3f rotation;
    protected Vector3f scale;
    private Transform transform;
    protected MovementType movement = MovementType.FORWARD;
    private CollisionComponent collision;
    private String name;
    private int textureIndex = 1;
    protected String type = "Entity";
    private int id = -1;

    private final List<Component> components = new ArrayList<>();

    public Entity(Model model, Vector3f position, Vector3f rotation, float scale) {

        this.model = model;
        this.position = position;
        this.rotation = rotation.add(0, 0, 0);
        this.scale =new Vector3f(scale);
        this.transform = new Transform(position, rotation, scale);
    }

    public Entity(String name) {
        this.name = name;
        this.position = new Vector3f(200, 0, 200);
        this.rotation = new Vector3f(0);
        this.scale = new Vector3f(1);
        this.transform = new Transform(position, rotation, scale.x());
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public CollisionComponent getCollision() {
        return collision;
    }

    public void setCollision(CollisionComponent collision) {
        this.collision = collision;
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

    public float getTextureXOffset() {
        if (model != null && model.getTexture() != null) {
            float column = textureIndex % model.getTexture().getNumbersOfRows();
            return column / model.getTexture().getNumbersOfRows();
        }
        return 1;
    }

    public float getTextureYOffset() {
        if (model != null && model.getTexture() != null) {
            float row = textureIndex / (float) model.getTexture().getNumbersOfRows();
            return row / model.getTexture().getNumbersOfRows();
        }
        return 1;
    }


    public void setTextureIndex(int textureIndex) {
        this.textureIndex = textureIndex;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScales() {
        return new Vector3f(scale);
    }

    public float getScale() {
        return scale.x();
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

    public void exportAllComponents() throws IOException {
        try (FileOutputStream fos = openSave();
             FileChannel fc = fos.getChannel()) {
            for (Component c : components) {
                c.export(fc);
            }
        }
    }

    public List<Component> getComponents() {
        return components;
    }

    private FileOutputStream openSave() throws IOException {
        File file = new File(Config.OUTPUT_FOLDER + "/component/"
                , hashCode() + ".component");
        if (!file.exists()) {
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
                this.model = AnimatedModelLoader.loadEntity(file.getAbsolutePath(), 5, this);
                Config.CURRENT_ANIMATION = LoadAnimation.loadAnimatedModel(file.getAbsolutePath());
            } else if (file.getName().endsWith(".obj")) {
                OBJFileLoader.loadModel(file.getAbsolutePath(), this);
            }
            Config.MODEL_LOCATION = file;
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

    public void setName(String name) {
        this.name = name;
        this.model.setName(name);
    }

    public void export() throws Exception {
        model.export();
        exportAllComponents();
        exportEntity();
    }

    public static Entity load(File file){
        String name = file.getName().replaceAll(".json","");
        Entity e = new Entity(name);
        String content = JsonUtils.loadJson(file.getAbsolutePath());
        String[] values = content.split(";");
        Model.load(values[0],e );
        content = JsonUtils.loadJson(Config.OUTPUT_FOLDER + "/component/"
                + values[2].replaceAll("\n","") + ".component");
        LoadComponents.loadComponents(content,e );
        return e;
    }

    public void exportEntity() throws Exception {
        StringBuilder fileContent = new StringBuilder();
        fileContent.append(model.getName()).append(";").append(name.hashCode()).append(";").append(hashCode());
        File file = new File(Config.OUTPUT_FOLDER,
                "/entities/data/" + name + ".json");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        if (!file.canWrite()) {
            throw new OpenDataException("the file " + file.getAbsolutePath() + " can't to be writed");
        }
        try (FileOutputStream fos = new FileOutputStream(file);
             FileChannel fc = fos.getChannel()) {
            byte[] data = fileContent.toString().getBytes();
            ByteBuffer bytes = ByteBuffer.allocate(data.length);
            bytes.put(data);
            bytes.flip();
            fc.write(bytes);
        }
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "Entity";
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
