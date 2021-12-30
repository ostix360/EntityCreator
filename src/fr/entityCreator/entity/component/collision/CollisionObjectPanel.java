package fr.entityCreator.entity.component.collision;

import com.flowpowered.react.collision.shape.*;
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
    private JComboBox<BoundingModel> boundingModelJComboBox;
    private JComboBox<CollisionShape> collisionShapeJComboBox;
    private BoundingModel actualModel;
    private BoundingModel beforeModel;
    private JPanel settings;
    private JButton removeButton;
    private JButton addPersButton;
    private JButton addButton;
    private JPanel actualTransformPanel;
    private JPanel actualShapePanel;

    public CollisionObjectPanel(CollisionPanel collisionPanel, JPanel settingsPanel, CollisionComponent collisionComponent,MainFrame mainFrame) {
        super();
        setLayout(new GridBagLayout());
        CollisionObjectRenderer.boundingModels.clear();
        this.cc = collisionComponent;
        this.settings = settingsPanel;


        removeButton = new JButton("Remove");
        removeButton.setPreferredSize(new Dimension(100, 25));
        removeButton.setVisible(false);
        removeButton.setFont(MainFrame.SMALL_FONT);
        add(removeButton, getGC(0, 2, 1));
        removeButton.addActionListener((e) -> {
            this.actualModel = (BoundingModel) boundingModelJComboBox.getSelectedItem();
            CollisionObjectRenderer.boundingModels.remove(actualModel);
            cc.getProperties().getBoundingModels().remove(actualModel);
            this.boundingModelJComboBox.removeItem(boundingModelJComboBox.getSelectedItem());
            if (boundingModelJComboBox.getItemCount() == 0) {
                this.actualModel = null;
                this.remove(actualTransformPanel);
                this.remove(actualShapePanel);
                actualTransformPanel = null;
                actualShapePanel = null;
                beforeModel = null;
                removeButton.setVisible(false);
                boundingModelJComboBox.repaint();
                this.validate();
                this.repaint();
                settings.validate();
                settings.repaint();
                return;
            }
            this.actualModel = boundingModelJComboBox.getItemAt(0);
            boundingModelJComboBox.repaint();
            this.validate();
            this.repaint();
            settings.validate();
            settings.repaint();
        });

        addPersButton = new JButton("Ajouter votre objet personalisé");
        addPersButton.setPreferredSize(new Dimension(150, 25));
        addPersButton.setFont(MainFrame.SMALL_FONT);
        addPersButton.setForeground(new Color(255, 0, 0));
        setup();

        addPersButton.addActionListener((e) -> {
            if (this.actualModel != null) beforeModel = actualModel;
            new ModelChooseScreen(this);
            if (actualShapePanel != null) {
                this.remove(actualShapePanel);
            }
            boundingModelJComboBox.setSelectedItem(this.actualModel);
            CollisionObjectRenderer.boundingModels.add(actualModel);
            cc.getProperties().getBoundingModels().add(actualModel);
            this.addSettingPanel();
            removeButton.setVisible(true);
        });
        add(addPersButton, getGC(0, 0, 1));

        addButton = new JButton("Ajouter un objet basique");
        addButton.setPreferredSize(new Dimension(150, 25));
        addButton.setFont(MainFrame.SMALL_FONT);
        addButton.setForeground(new Color(255, 0, 0));

        addButton.addActionListener((e) -> {
            if (this.actualModel != null) beforeModel = actualModel;
            this.actualModel = ((CollisionShape) collisionShapeJComboBox.getSelectedItem()).clone();
            this.addSettingPanel();
            this.addSpefSettingPanel();
            CollisionObjectRenderer.boundingModels.add(actualModel);
            cc.getProperties().getBoundingModels().add(actualModel);
            removeButton.setVisible(true);
        });
        add(addButton, getGC(1, 0, 1));

        AtomicBoolean isShowing = new AtomicBoolean(false);
        JButton showCollision = new JButton("Voir la collision");
        showCollision.setPreferredSize(new Dimension(150, 25));
        showCollision.setFont(MainFrame.SMALL_FONT);
        showCollision.addActionListener((e) -> {
            if (isShowing.compareAndSet(true,false)){
                mainFrame.stopShowingCollision();
                showCollision.setText("Voir la collision");
            }else{
                mainFrame.notifyShowCollision();
                isShowing.set(true);
                showCollision.setText("Stop visualisation de la collision");
            }

        });
        add(showCollision, getGC(0, 5, 1));

        for (BoundingModel bm : new ArrayList<>(cc.getProperties().getBoundingModels())){
            if (this.actualModel != null) beforeModel = actualModel;
            this.actualModel = bm;
            this.addSettingPanel();
            this.addSpefSettingPanel();
            CollisionObjectRenderer.boundingModels.add(actualModel);
            removeButton.setVisible(true);
        }
    }

    private void addSpefSettingPanel() {
        if (actualShapePanel != null) {
            this.remove(actualShapePanel);
        }
        CollisionShape shape = (CollisionShape) actualModel;
        actualShapePanel = shape.getPanel();
        this.add(actualShapePanel, getGC(0, 4, 4));
        this.boundingModelJComboBox.addItem(shape);
        this.boundingModelJComboBox.setSelectedItem(shape);
        this.validate();
        this.repaint();
        settings.validate();
        settings.repaint();
    }

    private void setup() {
        boundingModelJComboBox = new JComboBox<>();
        boundingModelJComboBox.setFont(MainFrame.SMALL_FONT);
        boundingModelJComboBox.setModel(new DefaultComboBoxModel<>());
        this.add(boundingModelJComboBox, getGC(0, 1, 1));
        boundingModelJComboBox.addActionListener((e) -> {
            beforeModel = actualModel;
            actualModel = (BoundingModel) boundingModelJComboBox.getSelectedItem();
            if (actualModel != null && !actualModel.equals(beforeModel)) {
                this.addSettingPanel();
                if (actualShapePanel != null) {
                    this.remove(actualShapePanel);
                }
                if (actualModel instanceof CollisionShape) {
                    actualShapePanel = ((CollisionShape) actualModel).getPanel();
                    this.add(actualShapePanel, getGC(0, 4, 4));
                    this.validate();
                    this.repaint();
                    settings.validate();
                    settings.repaint();
                }
            }
        });

        collisionShapeJComboBox = new JComboBox<>();
        collisionShapeJComboBox.setFont(MainFrame.SMALL_FONT);
        CollisionShape[] shapesPosibilities = new CollisionShape[]{
                new BoxShape(1, 1, 1), new CapsuleShape(1, 1), new ConeShape(1, 1),
                new CylinderShape(1, 1), new SphereShape(1)
        };
        collisionShapeJComboBox.setModel(new DefaultComboBoxModel<>(shapesPosibilities));
        this.add(collisionShapeJComboBox, getGC(1, 1, 1));
    }

    public void addSettingPanel() {
        if (actualTransformPanel != null) {
            this.remove(actualTransformPanel);
        }
        actualTransformPanel = actualModel.getRelativeTransform().getPanel();
        this.add(actualTransformPanel, getGC(0, 3, 4));
        this.validate();
        this.repaint();
        settings.validate();
        settings.repaint();
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

    public void setObjectFile(File object) {
        ModelLoaderRequest request;
        if (object == null) {
            return;
        }
        if (object.getName().endsWith(".obj")) {
            try (FileInputStream fis = new FileInputStream(object)) {
                ModelData data = OBJFileLoader.loadModel(fis);
                request = new ModelLoaderRequest(data);
                GLRequestProcessor.sendRequest(request);
                Timer.waitForRequest(request);
                actualModel = new BoundingModel(request.getModel());
                actualModel.getModel().setModelFile(object);
                boundingModelJComboBox.addItem(actualModel);

            } catch (IOException e) {
                e.printStackTrace();
            }

            boundingModelJComboBox.repaint();
            this.validate();
            this.repaint();
            settings.validate();
            settings.repaint();
        } else {
            new ErrorPopUp("Votre fichier doit etre un obj");
        }
    }
}
