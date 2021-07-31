package fr.entityCreator.frame;

import fr.entityCreator.creator.Workspace;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.camera.Camera;
import fr.entityCreator.entity.component.Component;
import fr.entityCreator.entity.component.ComponentType;
import fr.entityCreator.graphics.MasterRenderer;
import org.lwjgl.opengl.awt.GLData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class MainFrame {
    public static final String TITLE = "Entity Creator";
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private final JFrame frame;
    private JPanel settingsPanel;
    private JPanel mainPanel;
    private JPanel componentsPanel;
    private GLCanvas canvas;
    private Semaphore signalTerminate = new Semaphore(0);
    private Semaphore signalTerminated = new Semaphore(0);
    public static final Font VSMALL_FONT = new Font("Segoe UI", 0, 11);
    public static final Font SMALL_FONT = new Font("Segoe UI", 1, 13);
    public static final Font MEDIUM_FONT = new Font("Segoe UI", 1, 20);
    private final Workspace workspace;

    private AddComponentPanel addComponentPanel;
    private ComponentListPanel componentListPanel;
    private PreviewSettingsPanel previewSettings;
    private MainSettingsPanel mainSettings;
    private ComponentPanel currentComponentPanel;

    public MainFrame(MasterRenderer renderer, Workspace workspace,Camera cam) {
        this.workspace = workspace;
        frame = new JFrame(TITLE) {
            @Override
            public void dispose() {
                // request the cleanup
                signalTerminate.release();
                try {
                    // wait until the thread is done with the cleanup
                    signalTerminated.acquire();
                } catch (InterruptedException ignored) {
                }
                super.dispose();
            }
        };
        initFrame();
        initIcon();
        initMenuBar(workspace);
        initMainPanel();
        initInnerPanel(renderer, cam);
        frame.pack();
        frame.setVisible(true);
        frame.transferFocus();
    }


    public void setNewEntity(Entity entity) {
        this.mainSettings.setEntity(entity);
        //this.previewSettings.showExtraOptions();
        if (this.currentComponentPanel != null) {
            this.currentComponentPanel.destroy();
            this.settingsPanel.remove(this.currentComponentPanel);
            this.currentComponentPanel = null;
        }
        if (entity.hasModel()) {
            this.addComponentPanel.showStaticEntity(entity);

            for (Component loaded : entity.getComponents()) {
                addLoadedComponent(loaded, entity, this.componentListPanel);
            }
        } else {
            this.addComponentPanel.clearAll();
        }
        this.frame.validate();
        this.frame.repaint();
    }
    private void addLoadedComponent(Component loadedComponent, Entity entity, ComponentListPanel listPanel){
        listPanel.addComponent(loadedComponent);
    }

    public Runnable getRenderRunnable() {
        return new Runnable() {
            public void run() {
                while (true) {
                    canvas.render();
                    try {
                        if (signalTerminate.tryAcquire(10, TimeUnit.MILLISECONDS)) {
                            canvas.doDisposeCanvas();
                            signalTerminated.release();
                            return;
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        };
    }

    private void initFrame() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        frame.setResizable(false);
    }

    private void initIcon() {
        BufferedImage image;
        try {
            image = ImageIO.read(MainFrame.class.getResourceAsStream("/icon.png"));
            frame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMenuBar(Workspace workspace) {
        MenuBar menu = new MenuBar(this, workspace);
        frame.setJMenuBar(menu);
    }

    private void initMainPanel() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(600, 650));
        frame.add(this.mainPanel, gc);
        gc.gridx = 1;
        componentsPanel = new JPanel();
        componentsPanel.setPreferredSize(new Dimension(230, 650));
        frame.add(this.componentsPanel, gc);
        gc.gridx = 2;
        settingsPanel = new JPanel();
        settingsPanel.setPreferredSize(new Dimension(420, 650));
        frame.add(this.settingsPanel, gc);
    }

    private void initInnerPanel(MasterRenderer renderer, Camera camera) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        mainSettings = new MainSettingsPanel(580, 180, this);
        mainSettings.setVisible(true);
        mainPanel.add(this.mainSettings, gc);
        gc.gridy = 1;
        previewSettings = new PreviewSettingsPanel(580, 105, camera);
        mainPanel.add(this.previewSettings, gc);
        GLData data = new GLData();
        data.samples = 1;
        data.swapInterval = 1;
        data.majorVersion = 2;
        data.minorVersion = 1;
        data.profile = GLData.Profile.CORE;
        canvas = new GLCanvas(data, renderer);
        canvas.setPreferredSize(new Dimension(580, 345));
        gc.gridy = 2;
        mainPanel.add(this.canvas);

        componentListPanel = new ComponentListPanel(220, 585, this);
        addComponentPanel = new AddComponentPanel(220, 50, this.componentListPanel);
        componentsPanel.add(this.addComponentPanel);
        componentsPanel.add(this.componentListPanel);
    }

    public void notifyModelSet() {
        Entity entity = this.workspace.getCurrentEntity();
        this.addComponentPanel.showStaticEntity(entity);


    }

    public void setComponentPanel(ComponentPanel panel) {
        if (this.currentComponentPanel != null) {
            this.currentComponentPanel.destroy();
            this.settingsPanel.remove(this.currentComponentPanel);
        }
        this.currentComponentPanel = panel;
        this.settingsPanel.add(panel);
        this.frame.validate();
        this.frame.repaint();
    }


    public void clearComponentPanel() {

        if (this.currentComponentPanel != null) {

            this.currentComponentPanel.destroy();
            this.settingsPanel.remove(this.currentComponentPanel);
        }

        this.currentComponentPanel = null;
        this.frame.validate();
        this.frame.repaint();
    }
}
