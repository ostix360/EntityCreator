package fr.entityCreator.entity.component.collision;


import fr.entityCreator.core.collision.shape.CollisionShape;
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
    private CollisionPanel panel;

    public CollisionComponent(Entity e, CollisionProperties properties) {
        super(ComponentType.COLLISION_COMPONENT, e);
        this.properties = properties;
    }

    @Override
    public void update() {

    }

    @Override
    public void export(FileChannel fc) throws IOException {
            fc.write(DataTransformer.casteString(this.getType().toString() + "\n"));
            for (BoundingModel bm : properties.getBoundingModels()){
                if (bm instanceof CollisionShape){
                    CollisionShape shape = (CollisionShape) bm;
                    fc.write(DataTransformer.casteString(shape.getType().toString() +"\n"));
                    shape.export(fc);
                    shape.getRelativeTransform().export(fc);
                }else{
                    bm.export(fc);
                }
            }
            fc.write(DataTransformer.casteString("\n"));
    }

    @Override
    public ComponentPanel getComponentPanel(ComponentListPanel listPanel) {
        if (panel == null){
            panel = new CollisionPanel(this,listPanel);
        }
        return panel;
    }

    public CollisionProperties getProperties() {
        return properties;
    }
}
