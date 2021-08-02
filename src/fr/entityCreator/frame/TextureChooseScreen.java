package fr.entityCreator.frame;

import fr.entityCreator.core.Timer;
import fr.entityCreator.core.loader.TextureLoaderRequest;
import fr.entityCreator.core.resourcesProcessor.GLRequestProcessor;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.graphics.textures.TextureLoader;
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

    public TextureChooseScreen(TexturePanel texturePanel,Entity e, boolean diffuse) {
        this.entity = e;
        this.panel = texturePanel;
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
        TextureLoader texID;
        assert tex != null;
        if (diffuse) {
            entity.setTexturedFile(tex);
            entity.getModel().getTexture().setNewDiffuse(tex);
        }else{
            TextureLoaderRequest request = new TextureLoaderRequest(tex.getAbsolutePath());
            GLRequestProcessor.sendRequest(request);
            Timer.waitForRequest(request);
            texID = request.getTexture();
            entity.getModel().getTexture().setSpecularMap(texID);
        }
        if (parent instanceof TexturePanel){
            boolean success = panel.setNewIcon(diffuse ? entity.getModel().getTexture().getNewDiffuse() : new File(entity.getModel().getTexture().getSpecularMapFile()), diffuse);
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
