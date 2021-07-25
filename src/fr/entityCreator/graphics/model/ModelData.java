package fr.entityCreator.graphics.model;

public class ModelData {
    private final float[] vertices;
    private float[] texcoords;
    private final int[] indices;
    private float[] normals;
    private int[] jointsId;
    private float[] vertexWeights;


    public ModelData(float[] vertices, float[] texcoords, int[] indices, float[] normals, int[] jointsId, float[] vertexWeights) {
        this.vertices = vertices;
        this.texcoords = texcoords;
        this.indices = indices;
        this.normals = normals;
        this.jointsId = jointsId;
        this.vertexWeights = vertexWeights;
    }

    public ModelData(float[] vertices, float[] texcoords, int[] indices, float[] normals) {
        this.vertices = vertices;
        this.texcoords = texcoords;
        this.indices = indices;
        this.normals = normals;
    }

    public float[] getVertices() {
        return vertices;
    }

    public float[] getTexcoords() {
        return texcoords;
    }

    public int[] getIndices() {
        return indices;
    }

    public float[] getNormals() {
        return normals;
    }

    public int[] getJointsId() {
        return jointsId;
    }

    public float[] getVertexWeights() {
        return vertexWeights;
    }
}
