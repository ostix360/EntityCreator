package fr.entityCreator.entity.component.collision;


import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentType;
import fr.entityCreator.frame.ComponentListPanel;
import fr.entityCreator.frame.ComponentPanel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

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
    public void export(FileOutputStream fos) {
        try(FileChannel fc = fos.getChannel()){
            fc.write(DataTransformer.casteString(this.getType().toString()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public ComponentPanel getComponentPanel(ComponentListPanel paramComponentListPanel) {
        return null;
    }

    public CollisionProperties getProperties() {
        return properties;
    }
}
