package fr.entityCreator.entity.component.collision;

import fr.entityCreator.core.Timer;
import fr.entityCreator.core.loader.*;
import fr.entityCreator.core.resourcesProcessor.*;
import fr.entityCreator.entity.*;
import fr.entityCreator.frame.*;
import fr.entityCreator.graphics.*;
import fr.entityCreator.graphics.model.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;

public class CollisionObjectPanel extends JPanel {
    private CollisionComponent cc;
    private JPanel settings;
    private JButton removeButton;
    private JButton addPersButton;
    private JButton addButton;
    private JPanel actualTransformPanel;
    private JPanel actualShapePanel;

    public CollisionObjectPanel(CollisionPanel collisionPanel, JPanel settingsPanel, CollisionComponent collisionComponent,MainFrame mainFrame) {
        super();
        setLayout(new GridBagLayout());

        this.cc = collisionComponent;
        this.settings = settingsPanel;

        JLabel label = new JLabel("Collision Component added");
        add(label);
        JLabel label2 = new JLabel("the entiy model will be use for the collision");
        add(label2);

    }




    private GridBagConstraints getGC(int x, int y, int width) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = 4;
        gc.gridx = x;
        gc.gridy = y;
        gc.gridwidth = width;
        gc.weightx = 1;
        gc.weighty = 1;
        return gc;
    }
}
