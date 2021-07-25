package fr.entityCreator.entity.component.collision;


import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentType;
import fr.entityCreator.frame.ComponentListPanel;
import fr.entityCreator.frame.ComponentPanel;

import java.io.PrintWriter;

public class CollisionComponent extends Component {
    private final CollisionProperties properties;

    public CollisionComponent(Entity e, CollisionProperties properties) {
        super(ComponentType.COLLISION_COMPONENT, e);
        this.properties = properties;
    }

    @Override
    public void update() {

    }

    @Override
    public void export(PrintWriter writer) {

    }

    @Override
    public ComponentPanel getComponentPanel(ComponentListPanel paramComponentListPanel) {
        return null;
    }

    public CollisionProperties getProperties() {
        return properties;
    }
}
