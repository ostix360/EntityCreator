package fr.entityCreator.graphics.model;


import fr.entityCreator.toolBox.OpenGL.VAO;

import java.io.File;

public class MeshModel {

    private final VAO vao;
    private File modelFile;

    public MeshModel(VAO vaoID) {
        this.vao = vaoID;
    }

    public VAO getVAO() {
        return vao;
    }

    public File getModelFile() {
        return modelFile;
    }

    public void setModelFile(File modelFile) {
        this.modelFile = modelFile;
    }

    public int getVertexCount() {
        return vao.getVertexCount();
    }
}
