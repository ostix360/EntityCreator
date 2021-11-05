package fr.entityCreator.entity.component.collision;


import com.flowpowered.react.collision.shape.*;
import fr.entityCreator.core.exporter.*;
import fr.entityCreator.entity.*;
import fr.entityCreator.entity.component.*;
import fr.entityCreator.frame.*;
import fr.entityCreator.graphics.*;

import java.io.*;
import java.nio.channels.*;

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
            fc.write(DataTransformer.casteString("false\n"));
    }

    @Override
    public ComponentPanel getComponentPanel(ComponentListPanel listPanel, MainFrame mainFrame) {
        if (panel == null){
            panel = new CollisionPanel(this,listPanel,mainFrame);
        }else{
            CollisionObjectRenderer.boundingModels.addAll(this.getProperties().getBoundingModels());
        }
        return panel;
    }

    public CollisionProperties getProperties() {
        return properties;
    }
}
