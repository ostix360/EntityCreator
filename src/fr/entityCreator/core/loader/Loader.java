package fr.entityCreator.core.loader;


import fr.entityCreator.core.resourcesProcessor.GLRequest;
import fr.entityCreator.graphics.model.MeshModel;
import fr.entityCreator.graphics.textures.TextureData;
import fr.entityCreator.graphics.textures.TextureLoader;
import fr.entityCreator.graphics.textures.TextureUtils;
import fr.entityCreator.toolBox.Logger;
import fr.entityCreator.toolBox.OpenGL.VAO;
import fr.entityCreator.toolBox.OpenGL.VBO;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static fr.entityCreator.toolBox.ToolDirectory.RES_FOLDER;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Loader {

    public static final Loader INSTANCE = new Loader();

    private final List<VAO> VAOs = new ArrayList<>();
    private final List<TextureLoader> textureLoaders = new ArrayList<>();

    public MeshModel loadToVAO(float[] position, float[] texturesCoords,
                               float[] normals, int[] indices) {
        VAO vao = VAO.createVAO();
        VAOs.add(vao);
        vao.bind();
        vao.storeIndicesInVAO(indices);
        vao.storePositionInAttributeList(0, 3, position);
        vao.storeDataInAttributeList(1, 2, texturesCoords);
        vao.storeDataInAttributeList(2, 3, normals);
        VAO.unbind();
        return new MeshModel(vao);
    }

    public MeshModel loadToVAO(int[] indices, float[] position, float[] texturesCoords, float[] normals, int[] jointIDs, float[] vertexWeights) {
        VAO vao = VAO.createVAO();
        VAOs.add(vao);
        vao.bind();
        vao.storeIndicesInVAO(indices);
        vao.storePositionInAttributeList(0, 3, position);
        vao.storeDataInAttributeList(1, 2, texturesCoords);
        vao.storeDataInAttributeList(2, 3, normals);
        vao.storeIntDataInAttributeList(3, 3, jointIDs);
        vao.storeDataInAttributeList(4, 3, vertexWeights);
        VAO.unbind();
        return new MeshModel(vao);
    }

    public MeshModel loadFontToVAO(float[] pos, float[] texturesCoords) {
        VAO vao = VAO.createVAO();
        VAOs.add(vao);
        vao.bind();
        vao.storeDataInAttributeList(0, 3, pos);
        vao.storeDataInAttributeList(1, 2, texturesCoords);
        VAO.unbind();
        return new MeshModel(vao);
    }


    public void addInstance(VAO vao, VBO vbo, int attrib, int dataSize, int instanceDataLength, int offset) {
        vao.addInstance(vbo, attrib, dataSize, instanceDataLength, offset);
    }

    public void updateVBO(VBO vbo, float[] data, FloatBuffer buffer) {
        buffer.clear();
        buffer.put(data);
        buffer.flip();
        vbo.updateVBO(buffer);
    }


    public MeshModel loadToVAO(float[] vertices) {
        VAO vao = VAO.createVAO();
        VAOs.add(vao);
        vao.bind();
        vao.storeDataInAttributeList(0, 2, vertices);
        vao.setVertexCount(vertices.length / 2);
        VAO.unbind();
        return new MeshModel(vao);
    }

    //TEXTURES

    public TextureLoader loadTexture(String fileName) {
        TextureLoader textureLoader = TextureLoader.loadTexture(fileName, TextureUtils.MIPMAP_ANISOTROPIC_MODE, false);
        textureLoaders.add(textureLoader);
        return textureLoader;
    }

    public TextureLoader loadTextureFont(String textureFontName) {
        TextureLoader textureLoader = TextureLoader.loadTexture("font/" + textureFontName, 0, false);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0);
        textureLoaders.add(textureLoader);
        return textureLoader;
    }

    public void cleanUp() {
        for (VAO vao : VAOs) {
            vao.cleanUP();
        }
        VAOs.clear();
        for (TextureLoader t : textureLoaders) {
            GL11.glDeleteTextures(t.getId());
        }
        textureLoaders.clear();
    }

}
