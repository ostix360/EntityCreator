package fr.entityCreator.main;

import fr.entityCreator.core.Timer;
import fr.entityCreator.core.loader.Loader;
import fr.entityCreator.core.loader.TextureLoaderRequest;
import fr.entityCreator.core.resourcesProcessor.GLRequestProcessor;
import fr.entityCreator.creator.Workspace;
import fr.entityCreator.entity.Light;
import fr.entityCreator.frame.MainFrame;
import fr.entityCreator.graphics.MasterRenderer;
import fr.entityCreator.terrain.Terrain;
import fr.entityCreator.terrain.texture.TerrainTexture;
import fr.entityCreator.terrain.texture.TerrainTexturePack;
import fr.entityCreator.toolBox.Color;
import org.joml.Vector3f;

import javax.swing.*;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MasterRenderer renderer = new MasterRenderer();
        Workspace workspace = new Workspace();
        Thread main = Thread.currentThread();
        MainFrame frame = new MainFrame(renderer,workspace);
        Thread renderThread = new Thread(frame.getRenderRunnable(),"Render Thread");
        renderThread.start();
        TextureLoaderRequest backgroundTexture = new TextureLoaderRequest(Main.class.getResource("/res/terrain/grassy2.png").getFile());
        TextureLoaderRequest rTexture = new TextureLoaderRequest(Main.class.getResource("/res/terrain/mud.png").getFile());
        TextureLoaderRequest gTexture = new TextureLoaderRequest(Main.class.getResource("/res/terrain/grassFlowers.png").getFile());
        TextureLoaderRequest bTexture = new TextureLoaderRequest(Main.class.getResource("/res/terrain/path.png").getFile());
        TextureLoaderRequest blendRequest = new TextureLoaderRequest(Main.class.getResource("/res/terrain/blendMap.png").getFile());
        GLRequestProcessor.sendRequest(backgroundTexture,rTexture,gTexture,bTexture,blendRequest);

        Light light = new Light(new Vector3f(100,100000,100), Color.SUN);


        Timer.waitForRequest(blendRequest);

        TerrainTexture backt = new TerrainTexture(backgroundTexture.getTexture().getId());
        TerrainTexture rt = new TerrainTexture(rTexture.getTexture().getId());
        TerrainTexture gt = new TerrainTexture(gTexture.getTexture().getId());
        TerrainTexture bt = new TerrainTexture(bTexture.getTexture().getId());
        TerrainTexture blendt = new TerrainTexture(blendRequest.getTexture().getId());

        TerrainTexturePack tp = new TerrainTexturePack(backt,rt,gt,bt);
        Terrain t = new Terrain(0,0,tp,blendt);


    }

}
