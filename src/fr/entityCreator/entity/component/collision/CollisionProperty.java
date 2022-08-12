package fr.entityCreator.entity.component.collision;

public class CollisionProperty {
    private String controllerType = "RigidBody";

    public CollisionProperty() {
    }

    public String getControllerType() {
        return controllerType;
    }

    public void setControllerType(String controllerType) {
        this.controllerType = controllerType;
    }
}
