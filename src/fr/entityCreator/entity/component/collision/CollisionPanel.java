package fr.entityCreator.entity.component.collision;

import fr.entityCreator.frame.*;
import fr.entityCreator.graphics.CollisionObjectRenderer;

import javax.swing.*;

public class CollisionPanel extends ComponentPanel {

    private JPanel settingsPanel;
    private CollisionComponent collisionComponent;

    public CollisionPanel(CollisionComponent component, ComponentListPanel listPanel, MainFrame frame) {
        super(listPanel,component);
        this.collisionComponent = component;
        this.settingsPanel = settings;
        this.addPanel(frame);
    }

    private void addPanel(MainFrame frame) {
        settingsPanel.add(new CollisionObjectPanel(this, this.settingsPanel, this.collisionComponent,frame));

        settingsPanel.validate();
        settingsPanel.repaint();
    }

    @Override
    public void cleanUp() {
        CollisionObjectRenderer.boundingModels.clear();
    }
}
