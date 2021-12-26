package fr.entityCreator.entity.component.lod.LODGenerator;

import org.joml.*;

import java.util.*;

public class Vertex {
    private Vector3f vertex;
    private int index;
    private Set<Triangle> triangleFan = new HashSet();
    private List<Edge> edges = new ArrayList<>();
    private Matrix4f matrix;

    public Vertex(Vector3f point, int index) {
        this.vertex = point;
        this.index = index;
    }

    protected void addTriangleToFan(Triangle t) {
        this.triangleFan.add(t);
        if (matrix == null) {
            this.matrix = new Matrix4f(t.getMatrix());
        } else {
            this.matrix.add(t.getMatrix());
        }
    }

    public void notifyTriangleRemoved(Triangle triangle) {
        this.triangleFan.remove(triangle);
    }

    protected List<Edge> remove(Vertex replacement){
        List<Triangle> toRemove = new ArrayList();
        for (Triangle triangle : this.triangleFan) {
            boolean collapsed = triangle.replace(this, replacement);
            if (collapsed) {
                toRemove.add(triangle);
            }
        }
        for (Triangle t : toRemove){
            t.remove();
        }
        for (Edge edge: this.edges){
            edge.replaceVertex(this, replacement);
        }
        return replacement.takeOverFrom(this);
    }

    private List<Edge> takeOverFrom(Vertex removed) {
        this.matrix.add(this.matrix);
        this.triangleFan.addAll(removed.triangleFan);
        List<Edge> dupes = new ArrayList();
        for (Edge newEdge : removed.edges) {
            if (!addNewEdge(newEdge)) {
                dupes.add(newEdge);
            }
        }
        for (Edge edge : this.edges) {
            edge.evaluateCost();
        }
        for (Edge dupe : dupes) {
            dupe.delete();
        }
        return dupes;
    }

    protected boolean addNewEdge(Edge edge) {
        for (Edge currentEdge : this.edges) {
            if (Edge.areDuplicates(edge, currentEdge)) {
                return false;
            }
        }
        this.edges.add(edge);
        return true;
    }

    public Vector3f getPoint() {
        return vertex;
    }

    public int getIndex() {
        return index;
    }

    public Matrix4f getMatrix() {
        return matrix;
    }

    public Set<Triangle> getTriangleFan() {
        return triangleFan;
    }

    public boolean isEdgeVertex() {
        List<Vertex> nonMatchedVertices = new ArrayList();
        for (Triangle triangle : this.triangleFan) {
            List<Vertex> otherVerts = triangle.getOtherVertices(this);
            for (Vertex otherVert : otherVerts) {
                if (!nonMatchedVertices.contains(otherVert)) {
                    nonMatchedVertices.add(otherVert);
                }else{
                    nonMatchedVertices.remove(otherVert);
                }
            }
        }
        return !nonMatchedVertices.isEmpty();
    }

    public void notifyEdgeRemoved(Edge edge) {
        this.edges.remove(edge);
    }
}
