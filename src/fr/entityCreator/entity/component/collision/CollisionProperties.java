package fr.entityCreator.entity.component.collision;


import fr.entityCreator.entity.BoundingModel;

import java.util.ArrayList;
import java.util.List;

public class CollisionProperties {
    private boolean canMove = false;
    private List<BoundingModel> boundingModels;

    public CollisionProperties() {
        this.boundingModels = new ArrayList<>();
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean canMove() {
        return canMove;
    }


    public List<BoundingModel> getBoundingModels() {
        return boundingModels;
    }

    public void setBoundingModels(List<BoundingModel> boundingModels) {
        this.boundingModels = boundingModels;
    }
}
