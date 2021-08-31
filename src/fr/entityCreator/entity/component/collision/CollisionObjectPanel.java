package fr.entityCreator.entity.component.collision;

import fr.entityCreator.core.Timer;
import fr.entityCreator.core.loader.ModelLoaderRequest;
import fr.entityCreator.core.loader.OBJFileLoader;
import fr.entityCreator.core.resources.collision.shape.*;
import fr.entityCreator.core.resourcesProcessor.GLRequest;
import fr.entityCreator.core.resourcesProcessor.GLRequestProcessor;
import fr.entityCreator.entity.BoundingModel;
import fr.entityCreator.frame.ErrorPopUp;
import fr.entityCreator.frame.MainFrame;
import fr.entityCreator.frame.ModelChooseScreen;
import fr.entityCreator.graphics.model.MeshModel;
import fr.entityCreator.graphics.model.ModelData;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CollisionObjectPanel extends JPanel{
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

    public CollisionObjectPanel(CollisionPanel collisionPanel, JPanel settingsPanel, CollisionComponent collisionComponent) {
        super();
        setLayout(new GridBagLayout());
        this.cc = collisionComponent;
        this.settings = settingsPanel;

        removeButton = new JButton("Remove");
        removeButton.setPreferredSize(new Dimension(100, 25));
        removeButton.setVisible(false);
        removeButton.setFont(MainFrame.SMALL_FONT);
        add(removeButton, getGC(0, 2, 1));
        removeButton.addActionListener((e) ->{
            this.boundingModelJComboBox.removeItem(boundingModelJComboBox.getSelectedIndex());
        });

        addPersButton = new JButton("Ajouter votre objet personalisé");
        addPersButton.setPreferredSize(new Dimension(150, 25));
        addPersButton.setFont(MainFrame.SMALL_FONT);
        addPersButton.setForeground(new Color(255, 0, 0));
        setup();

        addPersButton.addActionListener((e) ->{
            if (this.actualModel != null) beforeModel = actualModel;
            new ModelChooseScreen(this);
            if (actualShapePanel != null){
                this.remove(actualShapePanel);
            }
            boundingModelJComboBox.setSelectedItem(this.actualModel);
            this.addSettingPanel();
            removeButton.setVisible(true);
        });
        add(addPersButton, getGC(0, 0, 1));

        addButton = new JButton("Ajouter un objet basique");
        addButton.setPreferredSize(new Dimension(150, 25));
        addButton.setFont(MainFrame.SMALL_FONT);
        addButton.setForeground(new Color(255, 0, 0));

        addButton.addActionListener((e) ->{
            if (this.actualModel != null) beforeModel = actualModel;
            this.actualModel = (BoundingModel) collisionShapeJComboBox.getSelectedItem();
            this.addSettingPanel();
            this.addSpefSettingPanel();
            removeButton.setVisible(true);
        });
        add(addButton, getGC(1, 0, 1));
    }

    private void addSpefSettingPanel() {
        if (actualShapePanel != null){
            this.remove(actualShapePanel);
        }
        CollisionShape shape = (CollisionShape) collisionShapeJComboBox.getSelectedItem();
        actualShapePanel = shape.getPanel();
        this.add(actualShapePanel,getGC(0,4,4));
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
        this.add(boundingModelJComboBox,getGC(0,1,1));
        boundingModelJComboBox.addActionListener((e) ->{
            beforeModel = actualModel;
            actualModel = (BoundingModel) boundingModelJComboBox.getSelectedItem();
            if (!actualModel.equals(beforeModel)){
                this.addSettingPanel();
                if (actualShapePanel != null){
                    this.remove(actualShapePanel);
                }
                if (actualModel instanceof CollisionShape){
                    actualShapePanel =  ((CollisionShape) actualModel).getPanel();
                    this.add(actualShapePanel,getGC(0,4,4));
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
                new BoxShape(1,1,1),new CapsuleShape(1,1),new ConeShape(1,1),
                new CylinderShape(1,1),new SphereShape(1)
        };
        collisionShapeJComboBox.setModel(new DefaultComboBoxModel<>(shapesPosibilities));
        this.add(collisionShapeJComboBox,getGC(1,1,1));
    }

    public void addSettingPanel(){
        if (actualTransformPanel != null){
            this.remove(actualTransformPanel);
        }
        actualTransformPanel = actualModel.getRelativeTransform().getPanel();
        this.add(actualTransformPanel,getGC(0,3,4));
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
        if (object == null){
            return;
        }
        if (object.getName().endsWith(".obj")) {
            ModelData data = OBJFileLoader.loadModel(object);
            request = new ModelLoaderRequest(data);
            GLRequestProcessor.sendRequest(request);
            Timer.waitForRequest(request);
            actualModel = new BoundingModel(request.getModel());
            actualModel.getModel().setModelFile(object);
            boundingModelJComboBox.addItem(actualModel);
            boundingModelJComboBox.repaint();
            this.validate();
            this.repaint();
            settings.validate();
            settings.repaint();
        }else{
            new ErrorPopUp("Votre fichier doit etre un obj");
        }
    }
}
