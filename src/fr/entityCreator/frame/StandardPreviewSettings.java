package fr.entityCreator.frame;


import fr.entityCreator.entity.camera.Camera;
import javafx.scene.control.Slider;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StandardPreviewSettings extends JPanel {
    private JCheckBox showPlayerBox;
    private JCheckBox rotateCameraBox;
    private JCheckBox rotateTexturesBox;
    private Camera camera;

    public StandardPreviewSettings(int width, int height, Camera camera) {
        this.camera = camera;
        setPreferredSize(new Dimension(width, height));
        super.setLayout(new GridBagLayout());
        //addShowPlayerBox();
        addRotateCameraBox();
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

    private void addRotateCameraBox() {
        GridBagConstraints gc = getGC(0, 1, 1);
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
        gc.anchor = 17;
        gc.gridx = x;
        gc.gridy = y;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        gc.gridwidth = width;
        return gc;
    }
}