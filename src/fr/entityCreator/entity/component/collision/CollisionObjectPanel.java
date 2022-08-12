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
        add(label,getGC(0));
        JLabel label2 = new JLabel("the entiy model will be use for the collision");
        add(label2,getGC(1));

        JTextArea textArea = new JTextArea(3,30);
        textArea.setText("Character is used for entities which can move.\n" +
                "And RigidBody is used for entities which can't move.\n");
        textArea.setEditable(false);

        add(textArea,getGC(2));

        JComboBox<String> controllerType = new JComboBox<>();
        controllerType.setModel(new DefaultComboBoxModel<>(new String[]{"RigidBody","Character"}));
        controllerType.addActionListener(e -> collisionComponent.getProp().setControllerType((String) controllerType.getSelectedItem()));
        add(controllerType,getGC(3));
    }




    private GridBagConstraints getGC( int y) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = 4;
        gc.gridx = 0;
        gc.gridy = y;
        gc.weightx = 1;
        gc.weighty = 1;
        return gc;
    }
}
