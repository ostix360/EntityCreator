package fr.entityCreator.frame;

import fr.entityCreator.creator.Workspace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    private final MainFrame frame;
    private final Workspace workspace;

    public MenuBar(MainFrame frame, Workspace workspace) {
        this.frame = frame;
        this.workspace = workspace;
        buildBar();
    }

    private void buildBar() {
        JMenu file = new JMenu("File");
        add(file);
        JMenuItem newStaticFile = new JMenuItem("Nouvelle static Entité");
        JMenuItem newAnimatedFile = new JMenuItem("Nouvelle Entité Animée");
        JMenuItem openFile = new JMenuItem("Ouvrir");
        JMenuItem save = new JMenuItem("Sauvagarder");
        file.add(newStaticFile);
        file.add(newAnimatedFile);
        file.add(openFile);
        file.add(save);
//        addOpenFileFunction(openFile, frame);
        addSaveFunction(save);
        addNewStaticFileFunction(newStaticFile);
//        addNewAnimatedFileFunction(newAnimatedFile);
        file.setFont(new Font("Segoe UI", 1, 12));
        newStaticFile.setFont(new Font("Segoe UI", 1, 12));
        newAnimatedFile.setFont(new Font("Segoe UI", 1, 12));
        openFile.setFont(new Font("Segoe UI", 1, 12));
        save.setFont(new Font("Segoe UI", 1, 12));
    }

//    private void addOpenFileFunction(JMenuItem open, final MainFrame mainFrame) {
//        open.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                MenuBar.this.workspace.save();
//
//                new FileChooseScreen(MenuBar.this.workspace.getAvailableItems(), MenuBar.this.workspace, mainFrame);
//
//            }
//        });
//    }

    private void addSaveFunction(JMenuItem save) {
        save.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                MenuBar.this.workspace.save();
            }
        });
    }

    private void addNewStaticFileFunction(JMenuItem newStaticFile) {

        newStaticFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                workspace.save();
                workspace.createNewEntity();
                frame.setNewEntity(workspace.getCurrentEntity());
            }
        });
    }

//    private void addNewAnimatedFileFunction(JMenuItem newAnimatedFile) {
//        newAnimatedFile.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                MenuBar.this.workspace.save();
//                MenuBar.this.workspace.createNewEntity(EntityType.ANIMATED);
//                MenuBar.this.mainFrame.setNewEntity(MenuBar.this.workspace.getCurrentEntity());
//
//            }
//        });
//    }

}
