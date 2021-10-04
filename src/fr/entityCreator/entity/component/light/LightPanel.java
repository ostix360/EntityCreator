package fr.entityCreator.entity.component.light;

import fr.entityCreator.frame.ComponentListPanel;
import fr.entityCreator.frame.ComponentPanel;
import fr.entityCreator.graphics.MasterRenderer;
import fr.entityCreator.main.Main;
import fr.entityCreator.toolBox.Color;

import javax.swing.*;

public class LightPanel extends ComponentPanel {
    public LightPanel(JPanel panel, ComponentListPanel paramComponentListPanel, Light light) {
        super(paramComponentListPanel,light);
        MasterRenderer.addLight(light);
        settings.add(panel);
        settings.validate();
        settings.repaint();
    }

    @Override
    public void cleanUp() {
        MasterRenderer.addLight(Main.light);
    }
}
