package fr.entityCreator.graphics.model;


import fr.entityCreator.toolBox.OpenGL.VAO;

public class MeshModel {

    private final VAO vaoID;

    public MeshModel(VAO vaoID) {
        this.vaoID = vaoID;
    }

    public VAO getVAO() {
        return vaoID;
    }

    public int getVertexCount() {
        return vaoID.getVertexCount();
    }
}
