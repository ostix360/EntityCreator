package fr.entityCreator.graphics.particles;


import fr.entityCreator.entity.Entity;
import fr.entityCreator.toolBox.maths.Maths;
import org.joml.Vector3f;

import java.util.Objects;

public class ParticleTarget {

    private final Entity entity;
    private final ParticleTargetProperties properties;
    private Vector3f position;

    public ParticleTarget(ParticleTargetProperties properties, Entity entity) {
        this.properties = properties;
        this.entity = entity;
        float scale = entity.getScale();
        Vector3f offset = Maths.rotateVector(new Vector3f(properties.getxOffset() * scale,
                        properties.getyOffset() * scale, properties.getzOffset() * scale),
                entity.getRotation().x(), entity.getRotation().y(), entity.getRotation().z());

        this.position = new Vector3f(entity.getPosition()).add(offset);
    }

    public void updatePosition() {
        float scale = entity.getScale();
        Vector3f offset = Maths.rotateVector(new Vector3f(properties.getxOffset() * scale,
                        properties.getyOffset() * scale, properties.getzOffset() * scale),
                entity.getRotation().x(), entity.getRotation().y(), entity.getRotation().z());

        this.position = new Vector3f(entity.getPosition()).add(offset);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticleTarget that = (ParticleTarget) o;
        return Objects.equals(entity, that.entity) && Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }

    protected Vector3f getForce(Vector3f particlePos) {
        Vector3f toTarget = new Vector3f(this.position).sub(particlePos);
        float distance = toTarget.length();

        if (distance <= this.properties.getCutOff()) {
            return null;
        }

        float distanceSquared = distance * distance;
        toTarget.normalize();
        toTarget.mul(this.properties.getPullFactor() / distanceSquared);
        return toTarget;
    }

    public ParticleTargetProperties getProperties() {
        return properties;
    }
}
