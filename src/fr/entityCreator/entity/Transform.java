package fr.entityCreator.entity;

import fr.entityCreator.toolBox.Maths;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {
    private Vector3f position;
    private Vector3f rotation;
    private float scale;
    private Matrix3f rotationMatrix;

    public Transform(Vector3f position, Vector3f rotation,float scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }
    public Transform(float x,float y,float z,float rotX,float rotY,float rotZ,float scale) {
        this.position = new Vector3f(x,y,z);
        this.rotation = new Vector3f(rotX,rotY,rotZ);
        this.scale = scale;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setQ(Quaternionf q) {
        rotationMatrix = new Matrix3f().identity();
        rotationMatrix.rotate(q);
    }

    public Matrix4f getTransformation(){
        Matrix4f m = Maths.createTransformationMatrix(this.position,this.rotation,this.scale);
        if (rotationMatrix != null)m.mul(rotationMatrix.get(new Matrix4f()));
        return m;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getX(){
        return position.x();
    }

    public float getY(){
        return position.y();
    }

    public float getZ(){
        return position.z();
    }

    public float getRotX(){
        return rotation.x();
    }

    public float getRotY(){
        return rotation.y();
    }

    public float getRotZ(){
        return rotation.z();
    }

    public float getScale() {
        return scale;
    }
}
