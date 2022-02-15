package fr.entityCreator.frame;


import fr.entityCreator.creator.Workspace;
import fr.entityCreator.entity.*;
import fr.entityCreator.entity.camera.Camera;
import javafx.scene.control.Slider;
import org.joml.Vector3f;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StandardPreviewSettings extends JPanel {
    private JCheckBox showPlayerBox;
    private JCheckBox rotateCameraBox;
    private JButton rotateXAxis;
    private JCheckBox rotateTexturesBox;
    private Camera camera;
    private Workspace workspace;

    public StandardPreviewSettings(int width, int height, Camera camera, Workspace workspace) {
        this.camera = camera;
        this.workspace = workspace;
        setPreferredSize(new Dimension(width, height));
        super.setLayout(new GridBagLayout());
        //addShowPlayerBox();
        addRotateCameraBox();
        addRotateXAxis();
        addScaleSlider();
       // addRotateTexturesBox();
       // addShowTerrainBox();
       // addTimeSlider();
    }



//    private void addShowPlayerBox() {
//        GridBagConstraints gc = getGC(0, 0, 1);
//        this.showPlayerBox = new JCheckBox("Show Player");
//        this.showPlayerBox.setFont(MainFrame.SMALL_FONT);
//        this.showPlayerBox.setSelected(true);
//        this.showPlayerBox.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                MainApp.player.showEntity(StandardPreviewSettings.this.showPlayerBox.isSelected());
//            }
//        });
//        add(this.showPlayerBox, gc);
//    }

    private void addScaleSlider(){
        GridBagConstraints gc = getGC(10, 0, 2);
        add(this.createSlider("Scale :",5,1),gc);
    }


    private void addRotateCameraBox() {
        GridBagConstraints gc = getGC(0, 1, 2);
        this.rotateCameraBox = new JCheckBox("Rotation de la Camera");
        this.rotateCameraBox.setFont(MainFrame.SMALL_FONT);
        this.rotateCameraBox.setSelected(true);
        this.rotateCameraBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                camera.setRotate(StandardPreviewSettings.this.rotateCameraBox.isSelected());
            }
        });
        add(this.rotateCameraBox, gc);
    }

    private void addRotateXAxis() {
        GridBagConstraints gc = getGC(0, 2, 2);
        this.rotateXAxis = new JButton("Rotation de l' objet");
        this.rotateXAxis.setFont(MainFrame.SMALL_FONT);
        this.rotateXAxis.setSelected(true);
        this.rotateXAxis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (workspace.getCurrentEntity() != null){
                    workspace.getCurrentEntity().increaseRotation(new Vector3f(45.0F,0,0));
                }
            }
        });
        add(this.rotateXAxis, gc);
    }


    private JPanel createSlider(String name, float maximum, float start) {
        JPanel panelSlider = new JPanel();
        panelSlider.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = 1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(MainFrame.SMALL_FONT);
        nameLabel.setPreferredSize(new Dimension(50, 20));
        panelSlider.add(nameLabel, gc);
        gc.weightx = 4.0D;
        gc.gridx = 1;
        JLabel valueReading = new JLabel();
        valueReading.setPreferredSize(new Dimension(40, 20));
        valueReading.setFont(MainFrame.SMALL_FONT);
        JFloatSlider slider = new JFloatSlider(0, 0.0F, maximum, start);
        valueReading.setText(limitChars(Float.toString(slider.getActualValue()), 4));
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                valueReading.setText(limitChars(String.valueOf(slider.getActualValue()), 5));
                if (workspace.getCurrentEntity() != null) {
                    workspace.getCurrentEntity().setScale(new Vector3f(slider.getActualValue()));
                }
            }
        });
        panelSlider.add(slider, gc);
        slider.setPreferredSize(new Dimension(120, 20));
        gc.gridx = 2;
        gc.weightx = 1.0D;
        panelSlider.add(valueReading, gc);
        return panelSlider;
    }

    private String limitChars(String original, int limit) {
        if (original.length() <= limit) {
            return original;
        }
        return original.substring(0, 5);
    }


//    private void addTimeSlider() {
//        final Slider slider = new Slider("Time", MainApp.time, 0.0F, 24000.0F, true, 220, 40);
//        slider.addSliderListener(new ChangeListener() {
//            public void stateChanged(ChangeEvent arg0) {
//                MainApp.time = slider.getSliderReading();
//
//            }
//        });
//        add(slider, getGC(0, 2, 2));
//
//    }

//    private void addShowTerrainBox() {
//
//        GridBagConstraints gc = getGC(1, 1, 1);
//
//        JCheckBox showTerrain = new JCheckBox("Show Terrain");
//
//        showTerrain.setFont(MainFrame.SMALL_FONT);
//
//        showTerrain.setSelected(MainApp.showTerrain);
//
//        showTerrain.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//
//                MainApp.showTerrain = showTerrain.isSelected();
//            }
//        });
//        add(showTerrain, gc);
//
//    }

    private GridBagConstraints getGC(int x, int y, int width) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = x;
        gc.gridy = y;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        gc.gridwidth = width;
        return gc;
    }
}