package fr.entityCreator.entity.component.lod.LODGenerator;

import fr.entityCreator.graphics.model.*;
import org.joml.*;

import java.util.*;

public class LODGenerator {
    private static List<Vertex> vertices = new ArrayList<>();
    private static List<Edge> edges = new ArrayList();

    public static ModelData simplifyModel(ModelData model, float errorMargin) {
        extractVertices(model);
        createMesh(model);
        calculateAllCosts();
        simplifyMesh(errorMargin);
        int[] newIndices = getSimplifiedIndices();
        vertices.clear();
        edges.clear();
        return new ModelData(model.getVertices(), model.getTexcoords(), newIndices,model.getNormals());
    }

    private static void extractVertices(ModelData data) {
        int pointer = 0;
        for (int i = 0; i < data.getVertices().length; i += 3) {
            float x = data.getVertices()[i];
            float y = data.getVertices()[(i + 1)];
            float z = data.getVertices()[(i + 2)];
            vertices.add(new Vertex(new Vector3f(x, y, z), pointer));
            pointer++;
        }
    }

    private static void createMesh(ModelData data) {
        int[] indices = data.getIndices();
        for (int i = 0; i < indices.length; i += 3) {
            int index1 = indices[i];
            int index2 = indices[(i + 1)];
            int index3 = indices[(i + 2)];
            linkMesh(index1, index2, index3);
        }
    }

    private static void linkMesh(int index1, int index2, int index3) {
        Vertex v1 = vertices.get(index1);
        Vertex v2 = vertices.get(index2);
        Vertex v3 = vertices.get(index3);
        Triangle t = new Triangle(v1, v2, v3);
        v1.addTriangleToFan(t);
        v2.addTriangleToFan(t);
        v3.addTriangleToFan(t);
        Edge e1 = new Edge(v1, v2);
        Edge e2 = new Edge(v2, v3);
        Edge e3 = new Edge(v3, v1);
        if (v1.addNewEdge(e1)) {
            v2.addNewEdge(e1);
            edges.add(e1);
        }
        if (v2.addNewEdge(e2)) {
            v3.addNewEdge(e2);
            edges.add(e2);
        }
        if (v3.addNewEdge(e3)) {
            v1.addNewEdge(e3);
            edges.add(e3);
        }
    }

    public static void calculateAllCosts(){
        for (Edge e : edges){
            e.evaluateCost();
        }
    }

    private static void simplifyMesh(float errorMargin) {
        Edge lowest = null;
        while ((lowest = getLowestCostEdge()).getCost() < errorMargin){
            if (vertices.size() < 9) {
                return;
            }
            edges.remove(lowest);
            vertices.remove(lowest.getRemovedVertex());
            List<Edge> dupes = lowest.collapse();
            edges.removeAll(dupes);
        }
    }

    private static Edge getLowestCostEdge() {
        Edge lowest = null;
        for (Edge edge : edges) {
            if (lowest == null) {
                lowest = edge;
            }else if (edge.getCost() <= lowest.getCost()){
                lowest = edge;
            }
        }
        return lowest;
    }

    private static int[] getSimplifiedIndices() {
        Set<Triangle> triangles = new HashSet();
        for (Vertex v : vertices) {
            triangles.addAll(v.getTriangleFan());
        }
        int[] newIndices = new int[triangles.size() * 3];
        int pointer = 0;
        for (Triangle triangle : triangles) {
            newIndices[(pointer++)] = triangle.getVertex1().getIndex();
            newIndices[(pointer++)] = triangle.getVertex2().getIndex();
            newIndices[(pointer++)] = triangle.getVertex3().getIndex();
        }
        return newIndices;
    }
}
