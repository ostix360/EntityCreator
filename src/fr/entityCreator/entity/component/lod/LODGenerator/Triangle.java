package fr.entityCreator.entity.component.lod.LODGenerator;

import fr.entityCreator.frame.*;
import org.joml.*;

import java.util.*;

public class Triangle {
    private Vertex vertex1;
    private Vertex vertex2;
    private Vertex vertex3;
    private Matrix4f matrix;

    public Triangle(Vertex vertex1, Vertex vertex2, Vertex vertex3) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
        calculateMatrix();
    }

    protected Vertex getAnotherVertex(Vertex original){
        if (this.vertex1 != original) {
            return this.vertex1;
        }
        return this.vertex2;
    }

    public List<Vertex> getOtherVertices(Vertex original) {
        List<Vertex> vertices = new ArrayList();
        if (this.vertex1 != original) {
            vertices.add(this.vertex1);
        }
        if (this.vertex2 != original) {
            vertices.add(this.vertex2);
        }
        if (this.vertex3 != original) {
            vertices.add(this.vertex3);
        }
        return vertices;
    }

    protected boolean contains(Vertex vertex) {
        if ((this.vertex1 == vertex) || (this.vertex2 == vertex) || (this.vertex3 == vertex)) {
            return true;
        }
        return false;
    }

    protected boolean replace(Vertex replaced,Vertex replacement){
        if (contains(replaced)) {
            return true;
        }
        if (this.vertex1 == replaced){
            this.vertex1 = replacement;
        }else if (this.vertex2 == replaced){
            this.vertex2 = replacement;
        }else if (this.vertex3 == replaced){
            this.vertex3 = replacement;
        }else{
            new ErrorPopUp("Error with this model");
        }
        return false;
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public Vertex getVertex3() {
        return vertex3;
    }

    public Matrix4f getMatrix() {
        return matrix;
    }

    private void calculateMatrix() {
        Vector3f normal = getNormal();
        Vector3f vertex = this.vertex1.getPoint();
        float d = -(normal.x * vertex.x + normal.y * vertex.y + normal.z * vertex.z);
        float a = normal.x;
        float b = normal.y;
        float c = normal.z;
        this.matrix = new Matrix4f();
        this.matrix = new Matrix4f();
        this.matrix.m00(a * a);
        this.matrix.m10(a * b);
        this.matrix.m20(a * c);
        this.matrix.m30(a * d);

        this.matrix.m01(a * b);
        this.matrix.m11(b * b);
        this.matrix.m21(b * c);
        this.matrix.m31(b * d);

        this.matrix.m02(a * c);
        this.matrix.m12(b * c);
        this.matrix.m22(c * c);
        this.matrix.m32(c * d);

        this.matrix.m03(d * a);
        this.matrix.m13(d * b);
        this.matrix.m23(d * c);
        this.matrix.m33(d * d);
    }

    private Vector3f getNormal() {
        Vector3f first = this.vertex2.getPoint().sub(this.vertex1.getPoint(), null);
        Vector3f second = this.vertex3.getPoint().sub(this.vertex1.getPoint(), null);
        Vector3f cross = first.cross(second, null);
        return cross.normalize(null);
    }

    public void remove() {
        this.vertex1.notifyTriangleRemoved(this);
        this.vertex2.notifyTriangleRemoved(this);
        this.vertex3.notifyTriangleRemoved(this);
    }


}
