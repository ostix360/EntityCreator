package fr.entityCreator.graphics.particles;


import java.util.Objects;

public class ParticleTexture {

    private int texture;
    private int numberOfRows;
    private boolean additive;
    private boolean affectedByLighting;

    public ParticleTexture(int texture, int numberOfRows, boolean additive, boolean affectedByLighting) {
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

    public void setTexture(int texture) {
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

    public int getTextureID() {
        return texture;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }
}
