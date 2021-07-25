package fr.entityCreator.entity;

import fr.entityCreator.graphics.model.MeshModel;
import org.joml.Vector3f;

public class BoundingModel {

    private final MeshModel m;
    private final Vector3f pos;
    private final Vector3f rot;
    private final float scale;

    public BoundingModel(MeshModel m, Vector3f pos, Vector3f rot, float scale) {
        this.m = m;
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
    }

    public MeshModel getModel() {
        return m;
    }

    public Vector3f getPos() {
        return pos;
    }

    public Vector3f getRot() {
        return rot;
    }

    public float getScale() {
        return scale;
    }
}
