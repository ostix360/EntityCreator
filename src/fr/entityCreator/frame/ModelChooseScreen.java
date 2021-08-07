package fr.entityCreator.frame;

import fr.entityCreator.entity.Entity;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class ModelChooseScreen {
    private JFileChooser chooser;
    private final Entity entity;
    private final JButton button;
    private JButton open;
    private JList<FileInList> files;
    private boolean isForAnimatedModel = false;

    public ModelChooseScreen(MainFrame frame, Entity entity, JButton button, JPanel parent) {
        this.entity = entity;
        this.button = button;
        setup();
        applyChoice(parent, frame);
    }

    private void applyChoice(JPanel parent, MainFrame frame) {
        entity.setModelFile(getFileChoosen(parent));
        frame.notifyModelSet();
        if (button != null) {
            button.setText("Changer de Model...");
            button.setForeground(new Color(0, 155, 0));
        }
        this.chooser.setVisible(false);
    }

    private void setup() {
        chooser = new JFileChooser("D:\\Projet LWJGL\\3D\\Projet_1\\src\\main\\resources\\models");
        chooser.setApproveButtonText("Ouvrir");
        chooser.setDialogTitle("Selectioner un model!");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Uniquement les model (.obj ou .dae)", "obj", "dae");
        chooser.resetChoosableFileFilters();
        chooser.addChoosableFileFilter(filter);
        chooser.setVisible(true);
    }

    private File getFileChoosen(JPanel parent) {
        if (this.chooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            if (this.chooser.getSelectedFile().getName().endsWith("dae")) {
                isForAnimatedModel = true;
            }
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }


}
