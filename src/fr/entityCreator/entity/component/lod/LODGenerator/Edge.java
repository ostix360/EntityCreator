package fr.entityCreator.entity.component.lod.LODGenerator;

import fr.entityCreator.frame.*;
import org.joml.*;

import java.util.*;

public class Edge {
    private Vertex vertex1;
    private Vertex vertex2;
    private float cost;

    public Edge(Vertex vertex1, Vertex vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    protected void evaluateCost(){
        Matrix4f matrix = new Matrix4f();
        this.vertex1.getMatrix().add(this.vertex1.getMatrix(),matrix);
        Vector3f newPos = this.vertex2.getPoint();
        this.cost = (matrix.m00() * newPos.x * newPos.x
                + 2.0F * matrix.m01() * newPos.x * newPos.y
                + 2.0F * matrix.m02() * newPos.x * newPos.z
                + 2.0F * matrix.m03() * newPos.x + matrix.m11() * newPos.y * newPos.y
                + 2.0F * matrix.m12() * newPos.y * newPos.z
                + 2.0F * matrix.m13() * newPos.y + matrix.m22() * newPos.z * newPos.z
                + 2.0F * matrix.m23() * newPos.z + matrix.m33());
        if (isConnectedToSeam()) {
            this.cost = 1.0e8F;
        }
    }

    protected boolean isAlongSeam() {
        return (this.vertex1.isEdgeVertex()) && (this.vertex2.isEdgeVertex());
    }

    private boolean isConnectedToSeam() {
        return (this.vertex1.isEdgeVertex()) || (this.vertex2.isEdgeVertex());
    }

    protected List<Edge> collapse() {
        this.vertex1.notifyEdgeRemoved(this);
        this.vertex2.notifyEdgeRemoved(this);
        return this.vertex1.remove(this.vertex2);
    }

    protected void replaceVertex(Vertex replaced,Vertex replacement){
        if (this.vertex1 == replaced) {
            this.vertex1 = replacement;
        }else if (this.vertex2 == replaced) {
            this.vertex2 = replacement;
        }else{
            new ErrorPopUp("Error with this model");
        }
    }

    protected void delete(){
        this.vertex1.notifyEdgeRemoved(this);
        this.vertex2.notifyEdgeRemoved(this);
    }

    protected Vertex getRemovedVertex() {
        return this.vertex1;
    }
    private boolean contains(Vertex v) {
        if ((this.vertex1 == v) || (this.vertex2 == v)) {
            return true;
        }
        return false;
    }

    public float getCost() {
        return cost;
    }

    protected static boolean areDuplicates(Edge e1, Edge e2){
        if ((e1.contains(e2.vertex1)) && (e1.contains(e2.vertex2))) {
            return true;
        }
        return false;
    }
}
