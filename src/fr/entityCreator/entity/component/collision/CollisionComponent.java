package fr.entityCreator.entity.component.collision;


import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.core.resources.CollisionShapeResource;
import fr.entityCreator.entity.BoundingModel;
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
    public void export(FileChannel fc) throws IOException {
            fc.write(DataTransformer.casteString(this.getType().toString()));
            for(CollisionShapeResource csr : properties.getCollisionShape()){
                csr.export(fc);
            }
            for (BoundingModel bm : properties.getBoundingModels()){
                bm.export(fc);
            }
    }

    @Override
    public ComponentPanel getComponentPanel(ComponentListPanel listPanel) {
        return new CollisionPanel(this,listPanel);
    }

    public CollisionProperties getProperties() {
        return properties;
    }
}
