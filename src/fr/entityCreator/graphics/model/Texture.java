package fr.entityCreator.graphics.model;

import fr.entityCreator.core.resources.TextureProperties;
import fr.entityCreator.graphics.textures.TextureLoader;
import fr.entityCreator.toolBox.ToolDirectory;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Objects;

public class Texture {
    private final int textureID;
    private File newDiffuse;
    private final TextureLoader textureLoader;
    private final int size;
    private final TextureProperties properties;

    public Texture(TextureLoader textureID, TextureProperties properties) {
        this.textureID = textureID.getId();
        this.properties = properties;
        this.size = textureID.getWidth();
        this.textureLoader = textureID;
    }

    public static void unBindTexture() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

    }

    public TextureProperties getProperties() {
        return properties;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Texture texture = (Texture) o;
        return size == texture.size && Objects.equals(properties, texture.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, properties);
    }


    public File getNewDiffuse() {
        return newDiffuse;
    }

    public void setNewDiffuse(File newDiffuse) {
        this.newDiffuse = newDiffuse;
    }

    public TextureLoader getTextureLoader() {
        return textureLoader;
    }

    public int getTextureID() {
        return textureID;
    }

    public void setNormalMapFile(TextureLoader path) {
        this.properties.setNormalMapFile(path);
    }

    public void setSpecularMap(TextureLoader specularMap) {
        this.properties.setSpecularMapFile(specularMap);
    }

    public void setShineDamper(float shineDamper) {
        this.properties.setShineDamper(shineDamper);
    }

    public void setReflectivity(float reflectivity) {
        this.properties.setReflectivity(reflectivity);
    }

    public void setNumbersOfRows(int numbersOfRows) {
        this.properties.setNumbersOfRows(numbersOfRows);
    }

    public void setTransparency(boolean transparency) {
        this.properties.setTransparency(transparency);
    }

    public void setUseFakeLighting(boolean useFakeLighting) {
        this.properties.setUseFakeLighting(useFakeLighting);
    }

    public void setInverseNormal(boolean inverseNormal) {
        properties.setInverseNormal(inverseNormal);
    }


    public boolean useFakeLighting() {
        return properties.useFakeLighting();
    }

    public boolean isTransparency() {
        return properties.isTransparency();
    }

    public String getNormalMapFile() {
        return properties.getNormalMapFile();
    }

    public int getNumbersOfRows() {
        return properties.getNumbersOfRows();
    }

    public String getSpecularMapFile() {
        return properties.getSpecularMapFile();
    }

    public boolean hasSpecularMap() {
        return properties.hasSpecularMap();
    }

    public float getShineDamper() {
        return properties.getShineDamper();
    }

    public float getReflectivity() {
        return properties.getReflectivity();
    }

    public int getID() {
        return textureID;
    }

    public void bindTexture() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
    }

    public int getSize() {
        return size;
    }


    public void exportDiffuse(String name) throws IOException {
        File file = new File(ToolDirectory.OUTPUT_FOLDER + "/textures/entities/"+ name + "/" +
                textureLoader.getFile().replace("\\","/").split("/")
                        [textureLoader.getFile().replace("\\","/").
                        split("/").length-1]);
        if (!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        ImageIO.write(textureLoader.getImage(),"png",file);
    }
}
