package fr.entityCreator.graphics.particles;


import com.google.gson.annotations.Expose;
import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.core.loader.json.JsonUtils;
import fr.entityCreator.core.resources.TextureProperties;
import fr.entityCreator.graphics.textures.TextureLoader;
import fr.entityCreator.toolBox.Config;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Objects;

public class ParticleTexture {

    private TextureLoader texture;
    @Expose
    private String name;
    @Expose
    private TextureProperties properties;

    public ParticleTexture(TextureLoader texture, int numberOfRows, boolean additive, boolean affectedByLighting) {
        this.texture = texture;
        this.properties = new TextureProperties();
        this.properties.setAdditive(additive);
        this.properties.setAffectedByLighting(affectedByLighting);
        this.properties.setNumbersOfRows(numberOfRows);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticleTexture that = (ParticleTexture) o;
        return texture == that.texture && Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(texture, properties);
    }


    public void export() throws IOException {
        this.name = texture.getFile().getName().replace(".png","");
        String texProp = JsonUtils.gsonInstance(true).toJson(this);
        try(FileOutputStream fos = openJson(); FileChannel fc = fos.getChannel()){
            fc.write(DataTransformer.casteString(texProp));
        }
        File textureFile = openTexture();
        ImageIO.write(texture.getImage(),"png",textureFile);
    }

    private FileOutputStream openJson() throws IOException {
        File file = new File(Config.OUTPUT_FOLDER, "textures/data/" + name + ".json");
        if (!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        return new FileOutputStream(file);
    }

    public String getName() {
        return name;
    }

    private File openTexture() throws IOException {
        File file = new File(Config.OUTPUT_FOLDER, "textures/particle/" + name + ".png");
        if (!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        return file;
    }


    public void setTexture(TextureLoader texture) {
        this.texture = texture;
    }

    public TextureLoader getTextureID() {
        return texture;
    }

    public int getNumberOfRows() {
        return properties.getNumbersOfRows();
    }

    public boolean isAdditive() {
        return properties.isAdditive();
    }

    public boolean isAffectedByLighting() {
        return properties.isAffectedByLighting();
    }

    public TextureProperties getProperties() {
        return properties;
    }

    public void setProperties(TextureProperties properties) {
        this.properties = properties;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfRows(int numberOfRows) {
        properties.setNumbersOfRows(numberOfRows);
    }

    public void setAdditive(boolean additive) {
        properties.setAdditive(additive);
    }

    public void setAffectedByLighting(boolean affectedByLighting) {
        properties.setAffectedByLighting(affectedByLighting);
    }
}
