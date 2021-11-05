package fr.entityCreator.world.interaction;

import com.flowpowered.react.body.*;
import com.flowpowered.react.collision.shape.*;
import com.flowpowered.react.engine.*;
import com.flowpowered.react.math.*;
import com.flowpowered.react.math.Quaternion;
import com.flowpowered.react.math.Transform;
import fr.entityCreator.entity.*;
import fr.entityCreator.toolBox.*;
import fr.entityCreator.world.*;
import gnu.trove.list.*;
import gnu.trove.list.array.*;
import org.joml.*;
import org.joml.Math;

import java.util.*;

public class CollisionSystem {

    private static final Material PHYSICS_MATERIAL = Material.asUnmodifiableMaterial
            (new Material(0.0f, 1.0f));

    private final Vector3 gravity = new Vector3(0, -9.81f, 0);
    private final Map<RigidBody, Entity> motionShape = new HashMap<>();
    private static final Map<CollisionBody, Entity> aabbs = new HashMap<>();
    private final TFloatList meshPositions = new TFloatArrayList();
    private final TIntList meshIndices = new TIntArrayList();
    private DynamicsWorld dynamicsWorld;

    public void init(float timeStep, Entity entities) {
        dynamicsWorld = new DynamicsWorld(gravity, timeStep);
        //addImmobileBody(entities.get(0), new BoxShape(new Vector3(25, 1, 25)), 100, new Vector3(0, 1.8f, 0), Quaternion.identity()).setMaterial(PHYSICS_MATERIAL);
        addAllEntity(entities);
        dynamicsWorld.enableSleeping(false);
        // addTerrain(terrains);
        dynamicsWorld.start();
    }


    private void addAllEntity(Entity e) {
//            if (e.isUseBondingModels()) {
//                int index = 0;
//                for (BoundingModel b : e.getBoundingModels()) {
//                    for (int i = 0; i < b.getModel().getVAO().getPosition().length; i++) {
//                        meshPositions.add(b.getModel().getVAO().getPosition()[i] * b.getScale());
//                    }
//                    meshIndices.addAll(b.getModel().getVAO().getIndices());
//                    TFloatList positions = new TFloatArrayList(meshPositions);
//                    TIntList indices = new TIntArrayList(meshIndices);
//                    MeshGenerator.toWireframe(positions, indices, false);
//                    final ConvexMeshShape meshShape = new ConvexMeshShape(positions.toArray(), positions.size() / 3, 12);
//                    for (int i = 0; i < indices.size(); i += 2) {
//                        meshShape.addEdge(indices.get(i), indices.get(i + 1));
//                    }
//                    meshShape.setIsEdgesInformationUsed(true);
//                    addBody(e, meshShape, 100, index);
//
//                    meshPositions.clear();
//                    meshIndices.clear();
//                    index++;
//                }
//                       } else {

            if (e.getCollision() != null) {
                for (BoundingModel b : e.getCollision().getProperties().getBoundingModels()) {
                    if (b instanceof CollisionShape) {
                        CollisionShape shape = ((CollisionShape) b).clone();
                        shape.setRelativeTransform(new fr.entityCreator.entity.Transform(b.getRelativeTransform()));
                        this.addBody(e, shape);
                    } else {
                        //...
                    }
                }
            }

//            for (int i = 0; i < e.getModel().getMeshModel().getVAO().getPosition().length; i++) {
//                meshPositions.add(e.getModel().getMeshModel().getVAO().getPosition()[i] * e.getScale());
//            }
//            meshIndices.addAll(e.getModel().getMeshModel().getVAO().getIndices());
//            TFloatList positions = new TFloatArrayList(meshPositions);
//            TIntList indices = new TIntArrayList(meshIndices);
//            MeshGenerator.toWireframe(positions, indices, false);
//            final ConvexMeshShape meshShape = new ConvexMeshShape(positions.toArray(), positions.size() / 3, 12);
//            for (int i = 0; i < indices.size(); i += 2) {
//                meshShape.addEdge(indices.get(i), indices.get(i + 1));
//            }
//            meshShape.setIsEdgesInformationUsed(true);
//            addBody(e, meshShape, 100);
//
//            meshPositions.clear();
//            meshIndices.clear();
//        BoxShape box = new BoxShape(20, 20, 20);
//        addBody(box);
        World.doAABBToRender();
    }

    public void update() {

        updatePlayer();
        dynamicsWorld.update();
        for (Map.Entry<RigidBody, Entity> entry : motionShape.entrySet()) {
            final CollisionBody body = entry.getKey();
            Entity shape = null;


            if (entry.getValue() != null) shape = entry.getValue();
            Transform transform = body.getInterpolatedTransform();
            Quaternionf q = new Quaternionf(transform.getOrientation().getX(),
                    transform.getOrientation().getY(),
                    transform.getOrientation().getZ(),
                    transform.getOrientation().getW());
            assert shape != null;
            //   q.getEulerAnglesXYZ(shape.getRotation());
            if (entry.getValue() != null) {
                shape.setPosition(Maths.toVector3f(transform.getPosition()).add(0, -2.4f, 0));
            }

            // if (entry.getValue() != null) shape.getTransform().setQ(q);
            float terrainHeight = World.getTerrainHeight(transform.getPosition().getX(), transform.getPosition().getZ()) + 2.4f;
            if (transform.getPosition().getY() < terrainHeight) {
                if (entry.getValue() != null) {
                    shape.setPosition(new Vector3f(transform.getPosition().getX(), terrainHeight - 2.4f, transform.getPosition().getZ()));
                }
                body.setTransform(new Transform(new Vector3(transform.getPosition().getX(), terrainHeight, transform.getPosition().getZ()), transform.getOrientation()));
            }


        }


    }

    private void updatePlayer() {
        for (Map.Entry<RigidBody, Entity> entry : motionShape.entrySet()) {
            RigidBody body = entry.getKey();
            Entity shape = entry.getValue();
            body.setLinearVelocity(shape.getForceToCenter().multiply(30));
        }
    }

    public void finish() {
        dynamicsWorld.stop();
    }

    private void addBody(CollisionShape shape) {
        RigidBody body = dynamicsWorld.createRigidBody(new Transform(new Vector3(50, 0, 0),
                        Quaternion.identity()),
                (float) 100, shape);

        body.enableMotion(false);
        //body.enableMotion(true);
        body.enableGravity(true);
        body.enableCollision(true);
        body.setMaterial(PHYSICS_MATERIAL);
        addBody(body, null);
    }

    private void addBody(Entity e, CollisionShape shape) {
        fr.entityCreator.entity.Transform trans = shape.getRelativeTransform();

        final Vector3 pos = Maths.toVector3(e.getPosition().add(trans.getPosition(), new Vector3f()).add(shape.applyCorrection()));//.sub(e.getScale().div(6,new Vector3f())));
        shape.scale(e.getScales().mul(1.0f, new Vector3f()));
        AxisAngle4d angles = new AxisAngle4d();
        Quaternionf q = new Quaternionf();
        q.rotateLocalY(Math.toRadians(e.getRotation().y()*2));
        trans.getTransformation().getRotation(angles);
        Quaternionf q2 = new Quaternionf(angles);
        q.add(q2);
        Transform theTransform = new Transform(pos, new Quaternion(q.x(), q.y(), q.z(), q.w()));
        e.getTransform().getTransformation().getUnnormalizedRotation(q);
        RigidBody body = dynamicsWorld.createRigidBody(theTransform,
                (float) 1, shape);


        body.enableMotion(e.getCollision().getProperties().canMove());
        //body.enableMotion(true);
        body.enableGravity(true);
        body.enableCollision(true);
        body.setMaterial(PHYSICS_MATERIAL);
        addBody(body, e);
    }

    private void addBody(CollisionBody body, Entity e) {
        if (body.isMotionEnabled() && e instanceof Player) {
            motionShape.put((RigidBody) body, e);
        }
        AABB aabb = body.getAABB();
        Transform bodyTransform = body.getTransform();
        Vector3 bodyPosition = bodyTransform.getPosition();
        Entity aabbModel = World.addAABB(bodyPosition, Vector3.subtract(aabb.getMax(), aabb.getMin()).divide(6));
        aabbs.put(body, aabbModel);
    }

}
