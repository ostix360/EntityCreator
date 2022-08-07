package fr.entityCreator.world;

import fr.entityCreator.core.resources.*;
import fr.entityCreator.entity.*;
import fr.entityCreator.entity.camera.*;
import fr.entityCreator.graphics.*;
import fr.entityCreator.graphics.model.*;
import fr.entityCreator.graphics.textures.*;
import fr.entityCreator.toolBox.*;
import fr.entityCreator.toolBox.maths.*;
import org.joml.*;

import java.util.*;

public class World {

    public static final int MAX_LIGHTS = 2;


    private static final List<Entity> entities = new ArrayList<>();
    private static final List<Entity> aabbs = new ArrayList<>();
    public static Model CUBE;
    private MasterRenderer renderer;
    private boolean isCleanUP = false;

    private Player player;
    Camera cam;

    public World(MasterRenderer renderer) {
        this.renderer = renderer;
    }

    public void init() {
        CUBE = new Model(Config.CUBE, new Texture(Config.WHITE, TextureProperties.DEFAULT()),"cube");
    }

    public void showCollision(Entity entity){
        entities.clear();
        aabbs.clear();
        for (Entity e :  entities){
            renderer.addEntity(e);
        }
        isCleanUP = false;
    }

    public void stopShowingCollision(){
        renderer.clearCollisionEntity();
        this.cleanUp();
    }

//    public static Entity addAABB(Vector3 bodyPosition, Vector3 size) {
//        Entity entity = new Entity(CUBE, Maths.toVector3f(bodyPosition), new Vector3f(), 1);
//        entity.setScale(Maths.toVector3f(size));
//        aabbs.add(entity);
//        return entity;
//    }

    public static void doAABBToRender() {
        entities.addAll(aabbs);
    }


    public static float getTerrainHeight(float worldX, float worldZ) {
        return 0;
    }

    public Player getPlayer() {
        return player;
    }

    public void cleanUp() {
        if (!isCleanUP){
            isCleanUP = true;
        }

    }
}
