package fr.entityCreator.graphics.particles;


import java.util.Objects;

public class ParticleTexture {

    private final int texture;
    private final int numberOfRows;
    private final boolean additive;

    public ParticleTexture(int texture, int numberOfRows, boolean additive) {
        this.additive = additive;
        this.texture = texture;
        this.numberOfRows = numberOfRows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticleTexture that = (ParticleTexture) o;
        return numberOfRows == that.numberOfRows && additive == that.additive;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfRows, additive);
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
