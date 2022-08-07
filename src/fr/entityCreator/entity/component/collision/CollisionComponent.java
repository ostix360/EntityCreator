package fr.entityCreator.entity.component.collision;

import fr.entityCreator.core.exporter.*;
import fr.entityCreator.entity.*;
import fr.entityCreator.entity.component.*;
import fr.entityCreator.frame.*;
import fr.entityCreator.graphics.*;

import java.io.*;
import java.nio.channels.*;

public class CollisionComponent extends Component {
    private CollisionPanel panel;

    public CollisionComponent(Entity e) {
        super(ComponentType.COLLISION_COMPONENT, e);
    }

    @Override
    public void update() {

    }

    @Override
    public void export(FileChannel fc) throws IOException {
            fc.write(DataTransformer.casteString(this.getType().toString() + "\n"));

    }

    @Override
    public ComponentPanel getComponentPanel(ComponentListPanel listPanel, MainFrame mainFrame) {
        if (panel == null){
            panel = new CollisionPanel(this,listPanel,mainFrame);
        }
        return panel;
    }

}
