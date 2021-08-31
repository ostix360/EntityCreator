package fr.entityCreator.main;

import fr.entityCreator.core.Timer;
import fr.entityCreator.core.loader.Loader;
import fr.entityCreator.core.loader.ModelLoaderRequest;
import fr.entityCreator.core.loader.OBJFileLoader;
import fr.entityCreator.core.loader.TextureLoaderRequest;
import fr.entityCreator.core.resourcesProcessor.GLRequest;
import fr.entityCreator.core.resourcesProcessor.GLRequestProcessor;
import fr.entityCreator.creator.Workspace;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.Light;
import fr.entityCreator.entity.Transform;
import fr.entityCreator.entity.camera.Camera;
import fr.entityCreator.frame.MainFrame;
import fr.entityCreator.graphics.MasterRenderer;
import fr.entityCreator.graphics.model.MeshModel;
import fr.entityCreator.graphics.model.ModelData;
import fr.entityCreator.terrain.Terrain;
import fr.entityCreator.terrain.texture.TerrainTexture;
import fr.entityCreator.terrain.texture.TerrainTexturePack;
import fr.entityCreator.toolBox.Color;
import fr.entityCreator.toolBox.Config;
import org.joml.Vector3f;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {

    public static Entity theEntity;
    private static final List<Entity> entities = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        MasterRenderer renderer = new MasterRenderer();
        Transform playerTransform = new Transform(200,0,200,0,0,0,1);
        Camera cam = new Camera(playerTransform);
        Workspace workspace = new Workspace();
        Thread main = Thread.currentThread();
        MainFrame frame = new MainFrame(renderer,workspace,cam);
        Thread renderThread = new Thread(frame.getRenderRunnable(),"Render Thread");
        renderThread.start();
        TextureLoaderRequest backgroundTexture = new TextureLoaderRequest(Main.class.getResource("/res/terrain/grassy2.png").getFile());
        TextureLoaderRequest rTexture = new TextureLoaderRequest(Main.class.getResource("/res/terrain/mud.png").getFile());
        TextureLoaderRequest gTexture = new TextureLoaderRequest(Main.class.getResource("/res/terrain/grassFlowers.png").getFile());
        TextureLoaderRequest bTexture = new TextureLoaderRequest(Main.class.getResource("/res/terrain/path.png").getFile());
        TextureLoaderRequest blendRequest = new TextureLoaderRequest(Main.class.getResource("/res/terrain/blendMap.png").getFile());
        GLRequestProcessor.sendRequest(backgroundTexture,rTexture,gTexture,bTexture,blendRequest);

        Light light = new Light(new Vector3f(100,100000,100), Color.SUN);
        setupMesh();
        Timer.waitForRequest(blendRequest);

        TerrainTexture backt = new TerrainTexture(backgroundTexture.getTexture().getId());
        TerrainTexture rt = new TerrainTexture(rTexture.getTexture().getId());
        TerrainTexture gt = new TerrainTexture(gTexture.getTexture().getId());
        TerrainTexture bt = new TerrainTexture(bTexture.getTexture().getId());
        TerrainTexture blendt = new TerrainTexture(blendRequest.getTexture().getId());

        TerrainTexturePack tp = new TerrainTexturePack(backt,rt,gt,bt);
        Terrain t = new Terrain(0,0,tp,blendt);
        entities.add(theEntity);
        renderer.initToRender(entities,t,light,cam);
    }

    private static void setupMesh() {
//        ModelData data = OBJFileLoader.loadModel(new File(Main.class.getResource("Box.obj").getFile()));
//        ModelLoaderRequest boxRequest = new ModelLoaderRequest(data);
//        GLRequestProcessor.sendRequest(boxRequest);
//        data = OBJFileLoader.loadModel(new File(Main.class.getResource("Capsule.obj").getFile()));
//        ModelLoaderRequest capsuleRequest = new ModelLoaderRequest(data);
//        GLRequestProcessor.sendRequest(capsuleRequest);
//        data = OBJFileLoader.loadModel(new File(Main.class.getResource("Con.obj").getFile()));
//        ModelLoaderRequest conRequest = new ModelLoaderRequest(data);
//        GLRequestProcessor.sendRequest(conRequest);
//        data = OBJFileLoader.loadModel(new File(Main.class.getResource("Cylinder.obj").getFile()));
//        ModelLoaderRequest cylinderRequest = new ModelLoaderRequest(data);
//        GLRequestProcessor.sendRequest(cylinderRequest);
//        data = OBJFileLoader.loadModel(new File(Main.class.getResource("Sphere.obj").getFile()));
//        ModelLoaderRequest sphereRequest = new ModelLoaderRequest(data);
//        GLRequestProcessor.sendRequest(sphereRequest);
//        Timer.waitForRequest(sphereRequest);
//        Config.BOX = boxRequest.getModel();
//        Config.CAPSULE = capsuleRequest.getModel();
//        Config.CONE = conRequest.getModel();
//        Config.CYLINDER = cylinderRequest.getModel();
//        Config.SPHERE = sphereRequest.getModel();
    }

}
