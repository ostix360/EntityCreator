package fr.entityCreator.core.resources;

import fr.entityCreator.graphics.textures.TextureLoader;

import java.util.Objects;

public class TextureProperties {

    private TextureLoader normalMapFile;
    private TextureLoader specularMapFile;

    private float shineDamper;
    private float reflectivity;

    private int numbersOfRows;

    private boolean isTransparency;
    private boolean useFakeLighting;
    private boolean isInverseNormal;

    public static final TextureProperties DEFAULT = new TextureProperties(null,null,0,0,1,false,false,false);

    public TextureProperties(TextureLoader normalMapFile, TextureLoader specularMapFile, float shineDamper, float reflectivity, int numbersOfRows, boolean isTransparency, boolean useFakeLighting, boolean isInverseNormal) {
        this.normalMapFile = normalMapFile;
        this.specularMapFile = specularMapFile;
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
        this.numbersOfRows = numbersOfRows;
        this.isTransparency = isTransparency;
        this.useFakeLighting = useFakeLighting;
        this.isInverseNormal = isInverseNormal;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextureProperties that = (TextureProperties) o;
        return normalMapFile == that.normalMapFile && specularMapFile == that.specularMapFile && Float.compare(that.shineDamper, shineDamper) == 0 && Float.compare(that.reflectivity, reflectivity) == 0 && numbersOfRows == that.numbersOfRows && isTransparency == that.isTransparency && useFakeLighting == that.useFakeLighting && isInverseNormal == that.isInverseNormal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalMapFile, specularMapFile, shineDamper, reflectivity, numbersOfRows, isTransparency, useFakeLighting, isInverseNormal);
    }

    public void setNormalMapFile(TextureLoader path) {
        this.normalMapFile = path;
    }

    public void setSpecularMapFile(TextureLoader specularMapFile) {
        this.specularMapFile = specularMapFile;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    public void setNumbersOfRows(int numbersOfRows) {
        this.numbersOfRows = numbersOfRows;
    }

    public void setTransparency(boolean transparency) {
        isTransparency = transparency;
    }

    public void setUseFakeLighting(boolean useFakeLighting) {
        this.useFakeLighting = useFakeLighting;
    }

    public void setInverseNormal(boolean inverseNormal) {
        isInverseNormal = inverseNormal;
    }

    public String getNormalMapFile() {
        return normalMapFile.getFile();
    }

    public String getSpecularMapFile() {
        return specularMapFile == null ? null : specularMapFile.getFile();
    }

    public int getSpecularMap(){
        return specularMapFile != null ? specularMapFile.getId() : 0;
    }

    public int getNormalMap(){
        return normalMapFile != null ? normalMapFile.getId() : 0;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public int getNumbersOfRows() {
        return numbersOfRows;
    }

    public boolean isTransparency() {
        return isTransparency;
    }

    public boolean useFakeLighting() {
        return useFakeLighting;
    }

    public boolean isInverseNormal() {
        return isInverseNormal;
    }

    public boolean hasSpecularMap() {
        return specularMapFile != null;
    }
}
