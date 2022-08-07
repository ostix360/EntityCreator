package fr.entityCreator.terrain;



import fr.entityCreator.core.Timer;
import fr.entityCreator.core.loader.ModelLoaderRequest;
import fr.entityCreator.core.resourcesProcessor.GLRequestProcessor;
import fr.entityCreator.graphics.model.MeshModel;
import fr.entityCreator.graphics.model.ModelData;
import fr.entityCreator.terrain.texture.TerrainTexture;
import fr.entityCreator.terrain.texture.TerrainTexturePack;
import org.joml.Vector3f;

import java.awt.image.BufferedImage;

public class Terrain {
    private static final int SIZE = 800;
    private static final int MAX_HEIGHT = 40;
    private static final float MAX_PIXEL_COLOR = 256 * 256 * 256;

    private float[][] heights;
    private final float x;
    private final float z;
    private final MeshModel model;
    private final TerrainTexturePack texturePack;
    private final TerrainTexture blendMap;

    public Terrain(float gridX, float gridZ, TerrainTexturePack texturePack, TerrainTexture blendMap) {
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.model = generateTerrain();
        this.texturePack = texturePack;
        this.blendMap = blendMap;
    }

    public float getHeightOfTerrain(float worldX, float worldZ) {
        return 0;
//        float terrainX = worldX - this.x;
//        float terrainZ = worldZ - this.z;
//        float gridSquareSize = SIZE / ((float) heights.length - 1);  // cacul de la grille donc nombre de vertex - 1
//        int gridX = (int) Math.floor(terrainX / gridSquareSize);
//        int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
//        if (gridX < 0 || gridX >= heights.length - 1 || gridZ < 0 || gridZ >= heights.length - 1) {
//            return 0;
//        }
//        float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
//        float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
//        float answer;
//        if (xCoord <= (1 - zCoord)) {
//            answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1,
//                    heights[gridX + 1][gridZ], 0), new Vector3f(0,
//                    heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
//        } else {
//            answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1,
//                    heights[gridX + 1][gridZ + 1], 1), new Vector3f(0,
//                    heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
//        }
//        return answer;
    }

    private MeshModel generateTerrain() {

        int VERTEX_COUNT = 1024;
        //heights = new float[VERTEX_COUNT][VERTEX_COUNT];
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
        int vertexPointer = 0;
        for (int z = 0; z < VERTEX_COUNT; z++) {            // Boucle de generation de monde
            for (int x = 0; x < VERTEX_COUNT; x++) {
                //heights[x][z] = height;
                vertices[vertexPointer * 3] = (float) x / ((float) VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer * 3 + 1] = 0;
                vertices[vertexPointer * 3 + 2] = (float) z / ((float) VERTEX_COUNT - 1) * SIZE;
                Vector3f normal = calculateNormal(x, z);
                normals[vertexPointer * 3] = normal.x;
                normals[vertexPointer * 3 + 1] = normal.y;
                normals[vertexPointer * 3 + 2] = normal.z;
                textureCoords[vertexPointer * 2] = (float) x / ((float) VERTEX_COUNT - 1);
                textureCoords[vertexPointer * 2 + 1] = (float) z / ((float) VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {       //boucle de generation des indices
            for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
                int topLeft = (gz * VERTEX_COUNT) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        ModelLoaderRequest request = new ModelLoaderRequest(new ModelData(vertices,textureCoords,indices,normals));
        GLRequestProcessor.sendRequest(request);
        Timer.waitForRequest(request);
        return request.getModel();
    }

    private Vector3f calculateNormal(int x, int z) {
        Vector3f normal = new Vector3f(0, 3f, 0);
        normal.normalize();
        return normal;
    }

    private float getHeight(int x, int z, BufferedImage image) {
        if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
            return 0;
        }
        float height = image.getRGB(x, z);
        height += MAX_PIXEL_COLOR / 2;
        height /= MAX_PIXEL_COLOR / 2;
        height *= MAX_HEIGHT;
        return height;
    }

    public static int getSIZE() {
        return SIZE;
    }

    public float[][] getHeights() {
        return heights;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public MeshModel getModel() {
        return model;
    }

    public TerrainTexturePack getTexturePack() {
        return texturePack;
    }

    public TerrainTexture getBlendMap() {
        return blendMap;
    }
}
