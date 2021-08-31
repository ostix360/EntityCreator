package fr.entityCreator.entity.component.collision;


import fr.entityCreator.core.resources.CollisionShapeResource;
import fr.entityCreator.entity.BoundingModel;

import java.util.ArrayList;
import java.util.List;

public class CollisionProperties {
    private final boolean canMove;
    private final boolean useSpecialBoundingBox;
    private final List<BoundingModel> boundingModels;
    private final List<CollisionShapeResource> collision;

    public CollisionProperties(boolean canMove, boolean useSpecialBoundingBox) {
        this.canMove = canMove;
        this.useSpecialBoundingBox = useSpecialBoundingBox;
        this.boundingModels = new ArrayList<>();
        this.collision = new ArrayList<>();
    }

    public boolean canMove() {
        return canMove;
    }

    public boolean useSpecialBoundingBox() {
        return useSpecialBoundingBox;
    }

    public List<BoundingModel> getBoundingModels() {
        return boundingModels;
    }

    public List<CollisionShapeResource> getCollisionShape() {
        return collision;
    }
}
