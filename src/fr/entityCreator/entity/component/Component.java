package fr.entityCreator.entity.component;


import fr.entityCreator.entity.Entity;
import fr.entityCreator.frame.ComponentListPanel;
import fr.entityCreator.frame.ComponentPanel;

import java.io.FileOutputStream;
import java.io.PrintWriter;

public abstract class Component {
    private final ComponentType type;
    protected final Entity e;
    private ComponentPanel panel;

    public Component(ComponentType type, Entity e) {
        this.type = type;
        this.e = e;
    }

    public abstract void update();

    public abstract void export(FileOutputStream fos);

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
