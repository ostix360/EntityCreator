package fr.entityCreator.entity.component.collision;

import fr.entityCreator.core.exporter.*;
import fr.entityCreator.entity.*;
import fr.entityCreator.entity.component.*;
import fr.entityCreator.frame.*;

import java.io.*;
import java.nio.channels.*;

public class CollisionComponent extends Component {
    private CollisionPanel panel;

    private final CollisionProperty prop;

    public CollisionComponent(Entity e, CollisionProperty prop) {
        super(ComponentType.COLLISION_COMPONENT, e);
        this.prop = prop;
    }

    @Override
    public void update() {

    }

    @Override
    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString(this.getType().toString() + "\n"));
        fc.write(DataTransformer.casteString(this.prop.getControllerType() + "\n"));
    }

    @Override
    public ComponentPanel getComponentPanel(ComponentListPanel listPanel, MainFrame mainFrame) {
        if (panel == null){
            panel = new CollisionPanel(this,listPanel,mainFrame);
        }
        return panel;
    }

    public CollisionProperty getProp() {
        return prop;
    }
}
