package fr.entityCreator.entity.component;


import fr.entityCreator.entity.Entity;
import fr.entityCreator.frame.ComponentListPanel;
import fr.entityCreator.frame.ComponentPanel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

public abstract class Component {
    private final ComponentType type;
    protected final Entity e;
    private ComponentPanel panel;

    public Component(ComponentType type, Entity e) {
        this.type = type;
        this.e = e;
    }

    public abstract void update();

    public abstract void export(FileChannel fc) throws IOException;

    public ComponentType getType() {
        return this.type;
    }

    public void unregisterPanel(ComponentPanel componentPanel) {
        if (this.panel == componentPanel){
            this.panel = null;
        }
    }

    public abstract ComponentPanel getComponentPanel(ComponentListPanel paramComponentListPanel);
}
