package fr.entityCreator.graphics;

import fr.entityCreator.core.loader.Loader;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.Light;
import fr.entityCreator.entity.camera.Camera;
import fr.entityCreator.graphics.model.Model;
import fr.entityCreator.graphics.shader.ClassicShader;
import fr.entityCreator.graphics.shader.TerrainShader;
import fr.entityCreator.terrain.Terrain;
import fr.entityCreator.toolBox.Color;
import fr.entityCreator.toolBox.OpenGL.DisplayManager;
import fr.entityCreator.toolBox.OpenGL.OpenGlUtils;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class MasterRenderer {

    public static final float FOV = (float) Math.toRadians(70f);
    public static final float NEAR = 0.1f;
    public static final float FAR = 500f;

    public static Color skyColor = new Color(0.5444f, 0.62f, 0.69f);

    private EntityRenderer entityRenderer;
    private ClassicShader shader;

    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader;

    private Terrain terrains;
    private static Matrix4f projectionMatrix;

    private Entity theEntity;
    private Camera cam;
    private Light light;

    private final List<Entity> entities = new ArrayList<>();

    public MasterRenderer() {
    }

    public void init(){

        OpenGlUtils.cullBackFaces(true);
        projectionMatrix = createProjectionMatrix();
        System.out.println("init");
        this.shader = new ClassicShader();
        this.terrainShader = new TerrainShader();
        this.entityRenderer = new EntityRenderer(shader, projectionMatrix);
        this.terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }


    public void initFrame() {
        glEnable(GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(skyColor.getRed(), skyColor.getGreen(), skyColor.getBlue(), skyColor.getAlpha());
        createProjectionMatrix();
    }


    private void processEntity(Entity e) {
            entities.add(e);
    }

    public void initToRender(List<Entity> entities, Terrain terrains, Light light, Camera camera) {
        for (Entity entity : entities) {
            if (entity == null){
                continue;
            }
            processEntity(entity);
        }
        this.terrains = terrains;
        this.light = light;
        this.cam = camera;
    }

    public void renderScene() {

        render(light, cam);

    }

    public void setTheEntity(Entity e){
        this.theEntity = e;
        processEntity(e);
    }

    private void render(Light light, Camera cam) {
        updateEntity();
        this.initFrame();
        shader.bind();
        if (light != null)shader.loadLight(light);
        shader.loadSkyColor(skyColor);
        shader.loadViewMatrix(cam);
        entityRenderer.render(entities);
        shader.unBind();

        terrainShader.bind();
        terrainShader.loadSkyColour(skyColor);
        if (light != null)terrainShader.loadLight(light);
        terrainShader.loadViewMatrix(cam);
        terrainRenderer.render(terrains);
        terrainShader.unBind();

    }

    private void updateEntity() {
        for (Entity entity : entities) {
            entity.update();
        }
    }

    public void clearEntity(){
        entities.clear();
    }

    public void cleanUp() {
        clearEntity();
        this.terrainShader.cleanUp();
        this.shader.cleanUp();
        glDisable(GL_BLEND);
    }

    private Matrix4f createProjectionMatrix() {
        projectionMatrix = new Matrix4f();
        projectionMatrix.identity();
        float aspectRatio = (float) DisplayManager.getWidth() / (float) DisplayManager.getHeight();
        projectionMatrix.perspective(FOV, aspectRatio, NEAR, FAR);
        return projectionMatrix;
//        projectionMatrix = new Matrix4f();
//        float aspectRatio = (float) DisplayManager.getWidth() / (float) DisplayManager.getHeight();
//        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
//        float x_scale = y_scale / aspectRatio;
//        float frustum_length = far - near;
//
//        projectionMatrix.set(0,0,x_scale);
//        projectionMatrix.set(1,1,y_scale);
//        projectionMatrix.set(2,2,-((far + near) / frustum_length));
//        projectionMatrix.set(2,3, -1);
//        projectionMatrix.set(3,2, -((2 * near * far) / frustum_length));
//        projectionMatrix.set(3,3, 0);
    }

    public static Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public void setCam(Camera cam) {
        this.cam = cam;
    }
}
