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

        JMenu others = new JMenu("Autres");
        add(others);

        JMenuItem newStaticFile = new JMenuItem("Nouvelle Entité");
        JMenuItem openFile = new JMenuItem("Ouvrir");
        JMenuItem save = new JMenuItem("Sauvagarder");
        file.add(newStaticFile);
        file.add(openFile);
        file.add(save);

        JMenuItem settings = new JMenuItem("Options");
        others.add(settings);

        addOpenFileFunction(openFile, frame);
        addSaveFunction(save);
        addNewStaticFileFunction(newStaticFile);
        file.setFont(new Font("Segoe UI", 1, 12));
        newStaticFile.setFont(new Font("Segoe UI", 1, 12));
        openFile.setFont(new Font("Segoe UI", 1, 12));
        save.setFont(new Font("Segoe UI", 1, 12));

        others.setFont(new Font("Segoe UI", 1, 12));
        settings.setFont(new Font("Segoe UI", 1, 12));
        addSettingsFunction(settings);
    }

    private void addOpenFileFunction(JMenuItem open, final MainFrame mainFrame) {
        open.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                workspace.save();
                workspace.open();
                frame.setNewEntity(workspace.getCurrentEntity());
                frame.notifyModelSet();
//
//                new FileChooseScreen(MenuBar.this.workspace.getAvailableItems(), MenuBar.this.workspace, mainFrame);

            }
        });
    }

    private void addSettingsFunction(JMenuItem settings){
        settings.addActionListener(e ->{
            new OptionPanel();
        });
    }

    private void addAboutFunction(JMenuItem about){
        about.addActionListener(e ->{
            //new AboutPanel();
        });
    }

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


}
