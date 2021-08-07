package fr.entityCreator.frame;

import fr.entityCreator.core.Timer;
import fr.entityCreator.core.loader.TextureLoaderRequest;
import fr.entityCreator.core.resourcesProcessor.GLRequestProcessor;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.graphics.textures.TextureLoader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class TextureChooseScreen {

    private JFileChooser chooser;
    private TexturePanel panel;
    private Entity entity;

    public TextureChooseScreen(TexturePanel texturePanel, Entity e, int id) {
        this.entity = e;
        this.panel = texturePanel;
        setup();
        applyChoice(texturePanel, id);
    }

    public TextureChooseScreen(Entity entity, JPanel parent) {
        this.entity = entity;
        setup();
        applyChoice(parent, 1);
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

    private void applyChoice(JPanel parent, int id) {
        File tex = getFileChoosen(parent);
        TextureLoader texID;
        TextureLoaderRequest request;
        assert tex != null;
        switch (id) {
            case 2:
                request = new TextureLoaderRequest(tex.getAbsolutePath());
                GLRequestProcessor.sendRequest(request);
                Timer.waitForRequest(request);
                texID = request.getTexture();
                entity.getModel().getTexture().setSpecularMap(texID);
                break;
            case 3:
                request = new TextureLoaderRequest(tex.getAbsolutePath());
                GLRequestProcessor.sendRequest(request);
                Timer.waitForRequest(request);
                texID = request.getTexture();
                entity.getModel().getTexture().setNormalMapFile(texID);
                break;
            default:
                entity.setTexturedFile(tex);
                entity.getModel().getTexture().setNewDiffuse(tex);
                break;
        }
        if (parent instanceof TexturePanel) {
            boolean success = panel.setNewIcon(id);
            if (success) {
                this.chooser.setVisible(false);
            } else {
                new ErrorPopUp("Impossible d'ouvrir la texture!");
            }
        }
        this.chooser.setVisible(false);
    }

    private File getFileChoosen(JPanel parent) {
        if (this.chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }
}
