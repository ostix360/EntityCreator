package fr.entityCreator.graphics.particles;


import com.google.gson.annotations.Expose;
import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.core.loader.json.JsonUtils;
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
    private int numberOfRows;
    @Expose
    private boolean additive;
    @Expose
    private boolean affectedByLighting;

    public ParticleTexture(TextureLoader texture, int numberOfRows, boolean additive, boolean affectedByLighting) {
        this.additive = additive;
        this.texture = texture;
        this.numberOfRows = numberOfRows;
        this.affectedByLighting = affectedByLighting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticleTexture that = (ParticleTexture) o;
        return texture == that.texture && numberOfRows == that.numberOfRows && additive == that.additive && affectedByLighting == that.affectedByLighting;
    }

    @Override
    public int hashCode() {
        return Objects.hash(texture, numberOfRows, additive, affectedByLighting);
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

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public void setAdditive(boolean additive) {
        this.additive = additive;
    }

    public void setAffectedByLighting(boolean affectedByLighting) {
        this.affectedByLighting = affectedByLighting;
    }

    public boolean isAffectedByLighting() {
        return affectedByLighting;
    }

    public boolean isAdditive() {
        return additive;
    }

    public TextureLoader getTextureID() {
        return texture;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

}
