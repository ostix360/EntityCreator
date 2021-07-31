package fr.entityCreator.frame;

import fr.entityCreator.core.resourcesProcessor.GLRequestProcessor;
import fr.entityCreator.entity.camera.Camera;
import fr.entityCreator.graphics.MasterRenderer;
import fr.entityCreator.toolBox.OpenGL.DisplayManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;

import javax.swing.*;

import static org.lwjgl.opengl.GL.*;

public class GLCanvas extends AWTGLCanvas {
    private final MasterRenderer render;
    private final Camera cam;

    public GLCanvas(GLData data, MasterRenderer render, Camera cam) {
        super(data);

        this.render = render;
        this.cam = cam;
    }

    @Override
    public void initGL() {
        createCapabilities();
        System.out.println("OpenGL version: " + effective.majorVersion + "." +
                effective.minorVersion + " (Profile: " + effective.profile + ")");
        DisplayManager.setHeight(this.getHeight());
        DisplayManager.setWidth(this.getWidth());
        render.init();
        render.setCam(cam);
    }

    @Override
    public void paintGL() {

        cam.move();
        render.renderScene();
        DisplayManager.setHeight(this.getHeight());
        DisplayManager.setWidth(this.getWidth());
        GL11.glViewport(0,0,getWidth(),getHeight());
        GLRequestProcessor.executeRequest();
        swapBuffers();
    }

    @Override
    public void repaint() {
        if (SwingUtilities.isEventDispatchThread()) {
            render();
        } else {
            SwingUtilities.invokeLater(() -> render());
        }
    }

    public void doDisposeCanvas() {
        render.cleanUp();
        super.disposeCanvas();
    }
}
