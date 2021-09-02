package fr.entityCreator.entity.component.collision;


import fr.entityCreator.core.resources.CollisionShapeResource;
import fr.entityCreator.entity.BoundingModel;

import java.util.ArrayList;
import java.util.List;

public class CollisionProperties {
    private final boolean canMove;
    private final List<BoundingModel> boundingModels;
    private final List<CollisionShapeResource> collision;

    public CollisionProperties(boolean canMove) {
        this.canMove = canMove;
        this.boundingModels = new ArrayList<>();
        this.collision = new ArrayList<>();
    }

    public boolean canMove() {
        return canMove;
    }


    public List<BoundingModel> getBoundingModels() {
        return boundingModels;
    }

    public List<CollisionShapeResource> getCollisionShape() {
        return collision;
    }
}
