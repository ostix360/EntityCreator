package fr.entityCreator.frame;

import fr.entityCreator.entity.Entity;
import fr.entityCreator.toolBox.ToolDirectory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TextureChooseScreen {

    private JFileChooser chooser;
    private TexturePanel panel;
    private Entity entity;

    public TextureChooseScreen(TexturePanel texturePanel, boolean diffuse) {
        setup();
        applyChoice(texturePanel,diffuse);
    }

    public TextureChooseScreen(Entity entity,JPanel parent) {
        this.entity = entity;
        setup();
        applyChoice(parent,true);
    }

    private void setup() {
        chooser = new JFileChooser("D:\\Projet LWJGL\\2D\\Test003\\src\\main\\resources\\textures");
        chooser.setApproveButtonText("Ouvrir");
        chooser.setDialogTitle("Selectioner une texture!");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Uniquement les images (.png)", "png");
        chooser.addChoosableFileFilter(filter);
        chooser.setVisible(true);
    }

    private void applyChoice(JPanel parent,boolean diffuse) {
        File tex = getFileChoosen(parent);
        entity.setTexturedFile(tex);
        entity.getModel().getTexture().setNewDiffuse(tex);
        if (parent instanceof TexturePanel){
            boolean success = panel.setNewIcon(entity.getModel().getTexture().getNewDiffuse(), diffuse);
            if (success) {
                this.chooser.setVisible(false);
            } else {
                new ErrorPopUp("Impossible d'ouvrir la texture!");
            }
        }
        this.chooser.setVisible(false);
    }

    private File getFileChoosen(JPanel parent){
        if (this.chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION){
            return chooser.getSelectedFile();
        }else{
            return null;
        }
    }
}
