package fr.entityCreator.main;

import fr.entityCreator.core.Timer;
import fr.entityCreator.core.loader.ModelLoaderRequest;
import fr.entityCreator.core.loader.OBJFileLoader;
import fr.entityCreator.core.loader.TextureLoaderRequest;
import fr.entityCreator.core.resourcesProcessor.GLRequestProcessor;
import fr.entityCreator.creator.Workspace;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.light.Light;
import fr.entityCreator.entity.Transform;
import fr.entityCreator.entity.camera.Camera;
import fr.entityCreator.frame.MainFrame;
import fr.entityCreator.frame.PopUp;
import fr.entityCreator.graphics.MasterRenderer;
import fr.entityCreator.graphics.model.ModelData;
import fr.entityCreator.terrain.Terrain;
import fr.entityCreator.terrain.texture.TerrainTexture;
import fr.entityCreator.terrain.texture.TerrainTexturePack;
import fr.entityCreator.toolBox.Color;
import fr.entityCreator.toolBox.Config;
import org.joml.Vector3f;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static Entity theEntity;
    private static final List<Entity> entities = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException, URISyntaxException {
        readConfig();
        MasterRenderer renderer = new MasterRenderer();
        Transform playerTransform = new Transform(200,0,200,0,0,0,1,1,1);
        Camera cam = new Camera(playerTransform);
        Workspace workspace = new Workspace();
        Thread main = Thread.currentThread();
        MainFrame frame = new MainFrame(renderer,workspace,cam);
        Thread renderThread = new Thread(frame.getRenderRunnable(),"Render Thread");
        renderThread.start();
        TextureLoaderRequest backgroundTexture = new TextureLoaderRequest(Main.class.getResourceAsStream("/res/terrain/grassy2.png"));
        TextureLoaderRequest rTexture = new TextureLoaderRequest(Main.class.getResourceAsStream("/res/terrain/mud.png"));
        TextureLoaderRequest gTexture = new TextureLoaderRequest(Main.class.getResourceAsStream("/res/terrain/grassFlowers.png"));
        TextureLoaderRequest bTexture = new TextureLoaderRequest(Main.class.getResourceAsStream("/res/terrain/path.png"));
        TextureLoaderRequest blendRequest = new TextureLoaderRequest(Main.class.getResourceAsStream("/res/terrain/blendMap.png"));
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
        renderer.initToRender(entities,t,light,cam);
    }

    private static void readConfig() {
        if(!Config.optionFile.exists()|| !Config.optionFile.canRead()){
            new PopUp("Bonjour et bienvenue sur ce logiciel.\n",
                    "Avant toutes choses vous devez configurer vos options.\n",
                    "Allez dans Autre puis dans option et séléctionnez l'emplacement de chaque dossier.\n" ,
                    "Créé une nouvelle entité et amusez vous à créer se que vous voulez. :-)");
            System.err.println("File not found");
        }else{
            try (FileReader fr = new FileReader(Config.optionFile);
            BufferedReader reader = new BufferedReader(fr)){
                String[] config = reader.readLine().split(";");
                if (config.length != 3){
                    System.err.println("Error in this file");
                    Config.optionFile.delete();
                    return;
                }
                Config.OUTPUT_FOLDER = new File(config[0]);
                Config.MODELS_FOLDER = new File(config[1]);
                Config.TEXTURES_FOLDER = new File(config[2]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setupMesh() throws URISyntaxException {
        TextureLoaderRequest textureRequest = new TextureLoaderRequest(Main.class.getResourceAsStream("/white.png"));
        GLRequestProcessor.sendRequest(textureRequest);
        ModelData data = OBJFileLoader.loadModel(Main.class.getResourceAsStream("/model/cube.obj"));
        ModelLoaderRequest boxRequest = new ModelLoaderRequest(data);
        GLRequestProcessor.sendRequest(boxRequest);
        data = OBJFileLoader.loadModel(Main.class.getResourceAsStream("/model/cone.obj"));
        ModelLoaderRequest conRequest = new ModelLoaderRequest(data);
        GLRequestProcessor.sendRequest(conRequest);
        data = OBJFileLoader.loadModel(Main.class.getResourceAsStream("/model/cylinder.obj"));
        ModelLoaderRequest cylinderRequest = new ModelLoaderRequest(data);
        GLRequestProcessor.sendRequest(cylinderRequest);
        data = OBJFileLoader.loadModel(Main.class.getResourceAsStream("/model/sphere.obj"));
        ModelLoaderRequest sphereRequest = new ModelLoaderRequest(data);
        GLRequestProcessor.sendRequest(sphereRequest);

        Timer.waitForRequest(sphereRequest);
        Config.BOX = boxRequest.getModel();
        Config.CONE = conRequest.getModel();
        Config.CYLINDER = cylinderRequest.getModel();
        Config.SPHERE = sphereRequest.getModel();
        Config.WHITE = textureRequest.getTexture();
    }

}
