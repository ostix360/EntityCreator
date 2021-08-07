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
        File output = new File(ToolDirectory.OUTPUT_FOLDER +
                "/models/entities/" + name + "/" + name + (isAnimated ? ".dae":".obj"));
        if (!output.exists()){
            output.getParentFile().mkdirs();
            output.createNewFile();
        }
        try(FileInputStream fis = new FileInputStream(ToolDirectory.MODEL_LOCATION);
            FileChannel fcReader = fis.getChannel();
            FileOutputStream fos = new FileOutputStream(output);
            FileChannel fcWriter = fos.getChannel();
        ){
            ByteBuffer buffer = ByteBuffer.allocate(1024);
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
