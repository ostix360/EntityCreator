package fr.entityCreator.world;

import com.flowpowered.react.math.*;
import fr.entityCreator.core.resources.*;
import fr.entityCreator.entity.*;
import fr.entityCreator.entity.camera.*;
import fr.entityCreator.graphics.*;
import fr.entityCreator.graphics.model.*;
import fr.entityCreator.graphics.textures.*;
import fr.entityCreator.terrain.*;
import fr.entityCreator.toolBox.*;
import fr.entityCreator.world.interaction.*;
import org.joml.*;
import org.lwjgl.openal.*;

import java.util.Random;
import java.util.*;
import java.lang.Math;

public class World {

    public static final int MAX_LIGHTS = 2;

    private final CollisionSystem collision = new CollisionSystem();

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
        collision.init(1/60f,entity);
        for (Entity e :  entities){
            renderer.addEntity(e);
        }
        isCleanUP = false;
    }

    public void stopShowingCollision(){
        renderer.clearCollisionEntity();
        this.cleanUp();
    }

    public static Entity addAABB(Vector3 bodyPosition, Vector3 size) {
        Entity entity = new Entity(CUBE, Maths.toVector3f(bodyPosition), new Vector3f(), 1);
        entity.setScale(Maths.toVector3f(size));
        aabbs.add(entity);
        return entity;
    }

    public static void doAABBToRender() {
        entities.addAll(aabbs);
    }

    public void update() {
        collision.update();
    }

    public static float getTerrainHeight(float worldX, float worldZ) {
        return 0;
    }

    public Player getPlayer() {
        return player;
    }

    public void cleanUp() {
        if (!isCleanUP){
            collision.finish();
            isCleanUP = true;
        }

    }
}
