package fr.entityCreator.entity.component.collision;

import fr.entityCreator.frame.ComponentListPanel;
import fr.entityCreator.frame.ComponentPanel;
import fr.entityCreator.graphics.CollisionObjectRenderer;

import javax.swing.*;

public class CollisionPanel extends ComponentPanel {

    private JPanel settingsPanel;
    private CollisionComponent collisionComponent;

    public CollisionPanel(CollisionComponent component, ComponentListPanel listPanel) {
        super(listPanel,component);
        this.collisionComponent = component;
        this.settingsPanel = settings;
        addPanel();
    }

    public void addPanel() {
        settingsPanel.add(new CollisionObjectPanel(this, this.settingsPanel, this.collisionComponent));

        settingsPanel.validate();
        settingsPanel.repaint();
    }

    @Override
    public void cleanUp() {
        CollisionObjectRenderer.boundingModels.clear();
    }
}
