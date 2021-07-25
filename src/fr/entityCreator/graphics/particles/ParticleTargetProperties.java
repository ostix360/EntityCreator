package fr.entityCreator.graphics.particles;

import java.util.Objects;

public class ParticleTargetProperties {
    private float xOffset;
    private float yOffset;
    private float zOffset;
    private float pullFactor;
    private float cutOff;

    public ParticleTargetProperties(float xOffset, float yOffset, float zOffset, float pullFactor, float cutOff) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.pullFactor = pullFactor;
        this.cutOff = cutOff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticleTargetProperties that = (ParticleTargetProperties) o;
        return Float.compare(that.xOffset, xOffset) == 0 && Float.compare(that.yOffset, yOffset) == 0 && Float.compare(that.zOffset, zOffset) == 0 && Float.compare(that.pullFactor, pullFactor) == 0 && Float.compare(that.cutOff, cutOff) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xOffset, yOffset, zOffset, pullFactor, cutOff);
    }

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    public float getzOffset() {
        return zOffset;
    }

    public void setzOffset(float zOffset) {
        this.zOffset = zOffset;
    }

    public float getPullFactor() {
        return pullFactor;
    }

    public void setPullFactor(float pullFactor) {
        this.pullFactor = pullFactor;
    }

    public float getCutOff() {
        return cutOff;
    }

    public void setCutOff(float cutOff) {
        this.cutOff = cutOff;
    }
}
