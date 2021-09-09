package fr.entityCreator.entity.component.light;

import fr.entityCreator.frame.ComponentListPanel;
import fr.entityCreator.frame.ComponentPanel;

import javax.swing.*;

public class LightPanel extends ComponentPanel {
    public LightPanel(JPanel panel, ComponentListPanel paramComponentListPanel, Light light) {
        super(paramComponentListPanel,light);
        settings.add(panel);
        settings.validate();
        settings.repaint();
    }

    @Override
    public void cleanUp() {

    }
}
