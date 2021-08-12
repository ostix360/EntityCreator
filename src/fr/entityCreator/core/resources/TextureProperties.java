package fr.entityCreator.core.resources;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import fr.entityCreator.graphics.textures.TextureLoader;
import fr.entityCreator.toolBox.ToolDirectory;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TextureProperties {

    private TextureLoader normalMapFile;
    private TextureLoader specularMapFile;

    @Expose
    private boolean useAdditive;

    @Expose
    private boolean affectedByLighting;

    @Expose
    @SerializedName("specularMap")
    private String specularMapName;
    @Expose
    @SerializedName("normalMap")
    private String normalMapName;

    @Expose
    private float shineDamper;

    @Expose
    private float reflectivity;

    @Expose
    private int numbersOfRows;

    @Expose
    private boolean isTransparency;
    @Expose
    private boolean useFakeLighting;

    public static final TextureProperties PARTICLE_DEFAULT_PROPERTIES = new TextureProperties(true,false,1);

    public static final TextureProperties DEFAULT = new TextureProperties(null,null,0,0,1,false,false);

    public TextureProperties(TextureLoader normalMapFile, TextureLoader specularMapFile, float shineDamper, float reflectivity, int numbersOfRows, boolean isTransparency, boolean useFakeLighting) {
        this.normalMapFile = normalMapFile;
        this.specularMapFile = specularMapFile;
        this.shineDamper = shineDamper;
        this.reflectivity = reflectivity;
        this.numbersOfRows = numbersOfRows;
        this.isTransparency = isTransparency;
        this.useFakeLighting = useFakeLighting;
    }

    public TextureProperties(boolean useAdditive, boolean affectedByLighting, int numbersOfRows) {
        this.useAdditive = useAdditive;
        this.affectedByLighting = affectedByLighting;
        this.numbersOfRows = numbersOfRows;
    }

    public boolean isUseAdditive() {
        return useAdditive;
    }

    public void setUseAdditive(boolean useAdditive) {
        this.useAdditive = useAdditive;
    }

    public boolean isAffectedByLighting() {
        return affectedByLighting;
    }

    public void setAffectedByLighting(boolean affectedByLighting) {
        this.affectedByLighting = affectedByLighting;
    }

    public void setSpecularMapName(String specularMapName) {
        this.specularMapName = specularMapName;
    }

    public void setNormalMapName(String normalMapName) {
        this.normalMapName = normalMapName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextureProperties that = (TextureProperties) o;
        return normalMapFile == that.normalMapFile && specularMapFile == that.specularMapFile && Float.compare(that.shineDamper, shineDamper) == 0 && Float.compare(that.reflectivity, reflectivity) == 0 && numbersOfRows == that.numbersOfRows && isTransparency == that.isTransparency && useFakeLighting == that.useFakeLighting;
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalMapFile, specularMapFile, shineDamper, reflectivity, numbersOfRows, isTransparency, useFakeLighting);
    }

    public void setNormalMapFile(TextureLoader normalMap) {
        this.normalMapFile = normalMap;
        this.normalMapName = new File(normalMap.getFile()).getName();
    }

    public void setSpecularMapFile(TextureLoader specularMapFile) {
        this.specularMapFile = specularMapFile;
        this.specularMapName = new File(specularMapFile.getFile()).getName();
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
    }

    public String getNormalMapFile() {
        return normalMapFile == null ? null : normalMapFile.getFile();
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

    public void exportSpecular() throws IOException {
            File file = new File(ToolDirectory.OUTPUT_FOLDER + "/textures/entities/specularMap/" +
                    specularMapFile.getFile().replace("\\","/").split("/")
                            [specularMapFile.getFile().replace("\\","/").
                            split("/").length-1]);
            if (!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            ImageIO.write(specularMapFile.getImage(),"png",file);
    }

    public void exportNormal() throws IOException {
        File file = new File(ToolDirectory.OUTPUT_FOLDER + "/textures/entities/normal/" +
                normalMapFile.getFile().replace("\\","/").split("/")
                        [normalMapFile.getFile().replace("\\","/").
                        split("/").length-1]);
        if (!file.exists()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        ImageIO.write(normalMapFile.getImage(),"png",file);
    }

    public boolean hasSpecularMap() {
        return specularMapFile != null;
    }

    public boolean hasNormalMap(){
        return normalMapFile != null;
    }
}
