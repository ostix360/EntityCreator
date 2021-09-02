package fr.entityCreator.core.resources;


import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.core.collision.maths.Vector3;
import fr.entityCreator.core.collision.shape.*;
import fr.entityCreator.entity.Transform;
import org.joml.Vector3f;

import java.io.IOException;
import java.nio.channels.FileChannel;

public class CollisionShapeResource {
    private final String type;
    private final Vector3 extent;
    private final float radius;
    private final float height;
    private CollisionShape shape;
    private Transform relativeTransform;

    public CollisionShapeResource(String type, Vector3 extent, float radius, float height) {
        this.type = type;
        this.extent = extent;
        this.radius = radius;
        this.height = height;
        this.relativeTransform = new Transform(new Vector3f(),new Vector3f(),1);
    }

    public Transform getRelativeTransform() {
        return relativeTransform;
    }

    public String getType() {
        return type;
    }

    public BoxShape getBoxShape() {
        if (type.equals("box")) {
            if (extent == null) {
                new NullPointerException("for a box we need to have the parameter extent!");
                return null;
            }
            return (BoxShape) (shape = new BoxShape(extent));
        }
        new IllegalStateException("this shape is not a box!");
        return null;
    }


    public CapsuleShape getCapsuleShape() {
        if (type.equals("capsule")) {
            if (radius <= -1) {
                new NullPointerException("for a capsule we need to have the parameter radius more than 0!");
                return null;
            }
            if (height <= 0) {
                new NullPointerException("for a capsule we need to have the parameter radius more than 0!");
                return null;
            }
            return (CapsuleShape) (shape = new CapsuleShape(radius, height));
        }
        new IllegalStateException("this shape is not a capsule!");
        return null;
    }

    public ConeShape getConeShape() {
        if (type.equals("cone")) {
            if (radius <= 0) {
                new NullPointerException("for a cone we need to have the parameter radius more than 0!");
                return null;
            }
            if (height <= 0) {
                new NullPointerException("for a cone we need to have the parameter radius more than 0!");
                return null;
            }
            return (ConeShape) (shape = new ConeShape(radius, height));
        }
        new IllegalStateException("this shape is not a cone!");
        return null;
    }

    public CylinderShape getCylinderShape() {
        if (type.equals("capsule")) {
            if (radius <= 0) {
                new NullPointerException("for a cylinder we need to have the parameter radius more than 0!");
                return null;
            }
            if (height <= 0) {
                new NullPointerException("for a cylinder we need to have the parameter radius more than 0!");
                return null;
            }
            return (CylinderShape) (shape = new CylinderShape(radius, height));
        }
        new IllegalStateException("this shape is not a cylinder!");
        return null;
    }

    public SphereShape getSphereShape() {
        if (type.equals("capsule")) {
            if (radius <= 0) {
                new NullPointerException("for a sphere we need to have the parameter radius more than 0!");
                return null;
            }
            return (SphereShape) (shape = new SphereShape(radius));
        }
        new IllegalStateException("this shape is not a sphere!");
        return null;
    }

    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString(type + ";"));
        shape.export(fc);
        relativeTransform.export(fc);
    }
}
