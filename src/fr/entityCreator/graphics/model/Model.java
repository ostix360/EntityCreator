package fr.entityCreator.graphics.model;

import fr.entityCreator.core.*;
import fr.entityCreator.core.loader.*;
import fr.entityCreator.core.loader.json.JsonUtils;
import fr.entityCreator.core.resources.*;
import fr.entityCreator.core.resourcesProcessor.*;
import fr.entityCreator.entity.*;
import fr.entityCreator.graphics.textures.Texture;
import fr.entityCreator.toolBox.Config;

import javax.management.openmbean.OpenDataException;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;
public class Model {

    private MeshModel meshModel;
    private Texture texture;
    private String name;
    protected File meshLocation;
    private boolean isAnimated;

    public Model(MeshModel meshModel, Texture texture, String name) {
        this.meshModel = meshModel;
        this.texture = texture;
        this.name = name;
    }

    public Model(String name,boolean isAnimated) {
        this.name = name;
        this.isAnimated = isAnimated;
    }
    public Model(MeshModel meshModel, Texture texture) {
        this.meshModel = meshModel;
        this.texture = texture;
        this.isAnimated = true;
    }

    public static void load(String file, Entity e) {
        String content = JsonUtils.loadJson(Config.OUTPUT_FOLDER+"/models/data/" + file + ".json");

        ModelResources current = JsonUtils.gsonInstance(false).fromJson(content, ModelResources.class);
        if (current == null) {
            throw new NullPointerException("The file cannot " + file + " be read");
        }
        String name = current.getName();
        Model m = new Model(name, current.canAnimated());
        e.setModel(m);
        ModelLoaderRequest request;
        if (current.canAnimated()) {
            request = ResourceLoader.loadTexturedAnimatedModel(current.getPath(), current.getTexture(),e);
            e.setModelFile(new File(Config.OUTPUT_FOLDER + "/models/entities/" + current.getPath() + ".dae"));
            GLRequestProcessor.sendRequest(request);
        } else {
            request = ResourceLoader.loadTexturedModel(current.getPath(), current.getTexture(),e);
            e.setModelFile(new File(Config.OUTPUT_FOLDER + "/models/entities/" + current.getPath() + ".obj"));
            GLRequestProcessor.sendRequest(request);
        }
        loadTexture(file,e);
    }

    private static void loadTexture(String file, Entity e){
        String content = JsonUtils.loadJson(Config.OUTPUT_FOLDER+"/textures/data/" + file + ".json");

        TextureResources current = JsonUtils.gsonInstance(true).fromJson(content, TextureResources.class);
        if (current == null) {
            throw new NullPointerException("The file cannot " + file + " be read");
        }
        String name = current.getName();
        TextureProperties prop = current.getTextureProperties();
        if (prop.getNormalMapName() != null) {
            TextureLoaderRequest tr = new TextureLoaderRequest(Config.OUTPUT_FOLDER+"/textures/entities/normal/" + prop.getNormalMapName() + ".png");
            GLRequestProcessor.sendRequest(tr);
            Timer.waitForRequest(tr);
            prop.setNormalMapFile(tr.getTexture());
        }
        if (prop.getSpecularMapName() != null) {
            TextureLoaderRequest tr = new TextureLoaderRequest(Config.OUTPUT_FOLDER+"/textures/entities/specularMap/" + prop.getSpecularMapName() + ".png");
            GLRequestProcessor.sendRequest(tr);
            Timer.waitForRequest(tr);
            prop.setSpecularMapFile(tr.getTexture());
        }
        TextureLoaderRequest tex = new TextureLoaderRequest(Config.OUTPUT_FOLDER+"/textures/"+current.getPath()+".png", prop,e);
        GLRequestProcessor.sendRequest(tex);
        Timer.waitForRequest(tex);
    }
    public boolean isAnimated() {
        return isAnimated;
    }

    public MeshModel getMeshModel() {
        return meshModel;
    }

    public Texture getTexture() {
        return texture;
    }

    public void export() throws Exception {
        String file = JsonUtils.gsonInstance(true).toJson(new TextureResources(name, "entities/" + name, texture.getProperties()));
        saveFile(file,true);
        exportAllTexture();
        file = JsonUtils.gsonInstance(false).toJson(new ModelResources(name,  name,name,isAnimated));
        saveFile(file,false);
        exportModel();
    }

    private void exportModel() throws IOException {
        File output = new File(Config.OUTPUT_FOLDER +
                "/models/entities/" + name + (isAnimated ? ".dae":".obj"));
        if (!output.exists()){
            output.getParentFile().mkdirs();
            output.createNewFile();
        }
        try(FileInputStream fis = new FileInputStream(Config.MODEL_LOCATION);
            FileChannel fcReader = fis.getChannel();
            FileOutputStream fos = new FileOutputStream(output);
            FileChannel fcWriter = fos.getChannel();
        ){
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            while (fcReader.read(buffer) !=-1) {
                buffer.flip();
                fcWriter.write(buffer);
                buffer.flip();
            }
        }
    }

    private void exportAllTexture() throws IOException {
        this.texture.exportDiffuse(this.name);
        if (this.texture.hasSpecularMap()){
            this.texture.getProperties().exportSpecular();
        }
        if (this.texture.getProperties().hasNormalMap()){
            this.texture.getProperties().exportNormal();
        }
    }


    public void setMeshLocation(File meshLocation) {
        this.meshLocation = meshLocation;
    }

    public void saveFile(String fileContent, boolean isTexture) throws Exception {
        File file = new File(Config.OUTPUT_FOLDER, isTexture ?
                "/textures/data/" + name + ".json" : "models/data/" + name + ".json");
        if (!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        if (!file.canWrite()){
            throw new OpenDataException("the file " + file.getAbsolutePath() + " can't to be writed");
        }
        try (FileOutputStream fos = new FileOutputStream(file);
             FileChannel fc = fos.getChannel()) {
            byte[] data = fileContent.getBytes();
            ByteBuffer bytes = ByteBuffer.allocate(data.length);
            bytes.put(data);
            bytes.flip();
            fc.write(bytes);
        }
    }

    public String getName() {
        return name;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setMeshModel(MeshModel model) {
        this.meshModel = model;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Model model = (Model) o;
        return Objects.equals(meshModel, model.meshModel) && Objects.equals(texture, model.texture) && Objects.equals(name, model.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meshModel.getVertexCount(), texture, name);
    }

    public void setName(String name) {
        this.name = name;
    }
}
