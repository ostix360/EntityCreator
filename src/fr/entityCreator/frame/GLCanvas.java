package fr.entityCreator.frame;

import fr.entityCreator.core.resourcesProcessor.GLRequest;
import fr.entityCreator.core.resourcesProcessor.GLRequestProcessor;
import fr.entityCreator.graphics.MasterRenderer;
import fr.entityCreator.toolBox.OpenGL.DisplayManager;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;

import javax.swing.*;

import static org.lwjgl.opengl.GL.*;

public class GLCanvas extends AWTGLCanvas {
    private final MasterRenderer render;

    public GLCanvas(GLData data ,MasterRenderer render) {
        super(data);

        this.render = render;
    }

    @Override
    public void initGL() {
        createCapabilities();
        System.out.println("OpenGL version: " + effective.majorVersion + "." +
                effective.minorVersion + " (Profile: " + effective.profile + ")");
        render.init();
    }

    @Override
    public void paintGL() {
        DisplayManager.setHeight(this.getHeight());
        DisplayManager.setWidth(this.getWidth());
        render.renderScene();
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
