package fr.entityCreator.frame;



import fr.entityCreator.entity.camera.Camera;

import javax.swing.*;
import java.awt.*;

public class PreviewSettingsPanel extends JPanel {
    private int width;
    private int height;
    private boolean extraIsShown = false;
    private Camera camera;

    public PreviewSettingsPanel(int width, int height, Camera camera) {
        this.camera = camera;
        this.width = width;
        this.height = height;
        setBorder(BorderFactory.createTitledBorder("Option de visualisation"));
        setPreferredSize(new Dimension(width, height));
        super.setLayout(new BorderLayout());
        add(new StandardPreviewSettings(width / 3 * 2 - 10, height - 15, camera), "West");
    }

//    public void showExtraOptions() {
//        if (!this.extraIsShown) {
//            add(new ExtraPreviewOptions(this.width / 3 - 10, this.height - 15, this.camera), "East");
//            this.extraIsShown = true;
//        }
//    }
}