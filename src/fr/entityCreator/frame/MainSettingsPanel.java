package fr.entityCreator.frame;

import fr.entityCreator.entity.Entity;

import javax.swing.*;
import java.awt.*;

public class MainSettingsPanel extends JPanel {
    private final int width;
    private final int height;
    private final MainFrame frame;
    private boolean isSetUp = false;
    private StandardInfoPanel standardInfo;
    private TexturePanel textureInfo;

    public MainSettingsPanel(int width, int height, MainFrame frame) {
        this.width = width;
        this.height = height;
        this.frame = frame;
        setBorder(BorderFactory.createTitledBorder("Parametre principaux de l'entité"));
        setPreferredSize(new Dimension(width, height));
        super.setLayout(new GridBagLayout());
    }

//    public void updateRadius() {
//        if (this.standardInfo != null) {
//            this.standardInfo.updateRadius();
//        }
//    }

    public void setEntity(Entity entity) {
        if(this.isSetUp){
            remove(standardInfo);
            remove(textureInfo);
            validate();
            repaint();
        }
        setupModelPanel(entity);
        setupTexturePanel(entity);
        this.isSetUp = true;

    }

    private void setupModelPanel(Entity entity) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        if (entity.getModel() == null) {
            new ModelChooseScreen(frame, entity, null, this);
            new TextureChooseScreen(entity, this);
        }
        this.standardInfo = new StandardInfoPanel((this.width - 10) / 2, this.height - 25, entity, frame);
        add(this.standardInfo, gc);
    }


    private void setupTexturePanel(Entity newEntity) {
       // this.standardInfo.setEntity(newEntity);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        this.textureInfo = new TexturePanel(newEntity, (this.width - 10) / 2, this.height - 25);
        this.textureInfo.setVisible(true);
        add(this.textureInfo, gc);
        validate();
        repaint();
    }
}
