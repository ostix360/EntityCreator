package fr.entityCreator.frame;

import fr.entityCreator.toolBox.ToolDirectory;

import javax.swing.*;
import java.awt.*;

public class OptionPanel {

    private JDialog frame;
    private JButton outputFolder;

    public OptionPanel() {
        setupFrame();
        addButton();
        this.frame.setVisible(true);
    }

    private void addButton() {
        this.outputFolder = new JButton("Selectionner votre dossier de sauvegarde");
        this.outputFolder.addActionListener(e -> {
            JFileChooser output = new JFileChooser();
            output.setDialogTitle("Selectionner votre dossier de sauvegarde");
            output.setDialogType(JFileChooser.OPEN_DIALOG);
            output.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            output.setMultiSelectionEnabled(false);
            output.setApproveButtonText("Ouvrir");
            output.setVisible(true);
            while (output.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
                System.out.println("not selected");
            }
            ToolDirectory.OUTPUT_FOLDER = output.getSelectedFile();
            output.setVisible(false);
        });
        this.frame.add(outputFolder);
    }

    private void setupFrame() {
        this.frame = new JDialog();
        this.frame.setTitle("Option");
        this.frame.setLayout(new GridBagLayout());
        this.frame.setPreferredSize(new Dimension(550,345));
    }
}
