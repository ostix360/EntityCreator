package fr.entityCreator.graphics.model;

import fr.entityCreator.core.loader.json.JsonUtils;
import fr.entityCreator.core.resources.ModelResources;
import fr.entityCreator.core.resources.TextureResources;
import fr.entityCreator.entity.animated.animation.animatedModel.AnimatedModel;
import fr.entityCreator.toolBox.ToolDirectory;

import javax.management.openmbean.OpenDataException;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;
public class Model {

    private MeshModel meshModel;
    private Texture texture;
    private final String name;

    public Model(MeshModel meshModel, Texture texture, String name) {
        this.meshModel = meshModel;
        this.texture = texture;
        this.name = name;
    }

    public Model(String name) {
        this.name = name;
    }

    public MeshModel getMeshModel() {
        return meshModel;
    }

    public Texture getTexture() {
        return texture;
    }

    public void export() throws Exception {
        String file = JsonUtils.gsonInstance().toJson(new TextureResources(name, "entities/" + name, texture.getProperties()));
        saveFile(file,true);
        file = JsonUtils.gsonInstance().toJson(new ModelResources(name, "entities/" + name,name,this instanceof AnimatedModel));
        saveFile(file,false);
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

    public void saveFile(String fileContent, boolean isTexture) throws Exception {
        File file = new File(ToolDirectory.OUTPUT_FOLDER, isTexture ?
                "/textures/entities/data/" + name + ".json" : "models/entities/data/" + name + ".json");
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
}
