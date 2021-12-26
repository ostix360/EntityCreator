package fr.entityCreator.entity.component.lod;

import fr.entityCreator.entity.*;
import fr.entityCreator.entity.component.*;
import fr.entityCreator.frame.*;

import java.io.*;
import java.nio.channels.*;

public class LODComponent extends Component {

    public LODComponent(Entity e) {
        super(ComponentType.LOD_COMPONENT, e);
    }

    @Override
    public void update() {

    }

    @Override
    public void export(FileChannel fc) throws IOException {

    }

    @Override
    public ComponentPanel getComponentPanel(ComponentListPanel paramComponentListPanel, MainFrame mainFrame) {
        return new LODPanel(paramComponentListPanel,this);
    }
}
