package fr.entityCreator.frame;

import fr.entityCreator.core.Timer;
import fr.entityCreator.core.loader.TextureLoaderRequest;
import fr.entityCreator.core.resourcesProcessor.GLRequestProcessor;
import fr.entityCreator.entity.component.particle.ParticleComponentPanel;
import fr.entityCreator.graphics.particles.ParticleSystem;
import fr.entityCreator.graphics.particles.ParticleTexture;
import fr.entityCreator.graphics.textures.TextureLoader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class ParticleTextureChooser {

    private JFileChooser chooser;
    private TexturePanel panel;

    public ParticleTextureChooser(ParticleComponentPanel parent, JButton button) {
        setup();
        applyChoice(parent, button);
    }

    private void setup() {
        chooser = new JFileChooser("D:\\Projet LWJGL\\2D\\Test003\\src\\main\\resources\\textures");
        chooser.setApproveButtonText("Ouvrir");
        chooser.setDialogTitle("Selectioner une texture pour vos particules!");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.resetChoosableFileFilters();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Uniquement les images (.png)", "png");
        chooser.addChoosableFileFilter(filter);
        chooser.setVisible(true);
    }

    private void applyChoice(ParticleComponentPanel parent, JButton button) {
        File tex = getFileChoosen(parent);
        TextureLoader texID;
        TextureLoaderRequest request = new TextureLoaderRequest(tex.getAbsolutePath());
        GLRequestProcessor.sendRequest(request);
        Timer.waitForRequest(request);
        texID = request.getTexture();
        parent.setTexture(texID);
        button.setText("Changer la texture de vos particle");
        button.setForeground(new Color(0, 255, 0));
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
