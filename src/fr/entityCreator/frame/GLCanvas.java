package fr.entityCreator.frame;

import fr.entityCreator.core.Input;
import fr.entityCreator.core.loader.Loader;
import fr.entityCreator.core.resourcesProcessor.GLRequestProcessor;
import fr.entityCreator.entity.camera.Camera;
import fr.entityCreator.graphics.MasterRenderer;
import fr.entityCreator.graphics.particles.MasterParticle;
import fr.entityCreator.toolBox.OpenGL.DisplayManager;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;

import javax.swing.*;
import java.awt.event.*;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.opengl.GL.createCapabilities;

public class GLCanvas extends AWTGLCanvas implements MouseWheelListener, MouseListener, MouseMotionListener {
    private final MasterRenderer render;
    private final Camera cam;
    private float mouseDWheel = 0;

    public GLCanvas(GLData data, MasterRenderer render, Camera cam) {
        super(data);

        this.render = render;
        this.cam = cam;
    }

    @Override
    public void initGL() {
        this.addMouseWheelListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        createCapabilities();
        System.out.println("OpenGL version: " + effective.majorVersion + "." +
                effective.minorVersion + " (Profile: " + effective.profile + ")");
        DisplayManager.setHeight(this.getHeight());
        DisplayManager.setWidth(this.getWidth());
        render.init();
        render.setCam(cam);

        MasterParticle.init(Loader.INSTANCE, MasterRenderer.getProjectionMatrix());
    }

    @Override
    public void paintGL() {
        cam.move(mouseDWheel);
        mouseDWheel = 0;
        render.renderScene();
        MasterParticle.update(cam);
        MasterParticle.render(cam);
        DisplayManager.setHeight(this.getHeight());
        DisplayManager.setWidth(this.getWidth());
        GL11.glViewport(0, 0, getWidth(), getHeight());
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
        MasterParticle.cleanUp();
        super.disposeCanvas();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseDWheel = e.getWheelRotation() * -e.getScrollAmount();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Input.keysMouse[GLFW_MOUSE_BUTTON_1] = false;
        }else{
            Input.keysMouse[GLFW_MOUSE_BUTTON_1] = false;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Input.keysMouse[GLFW_MOUSE_BUTTON_1] = true;
        }else{
            Input.keysMouse[GLFW_MOUSE_BUTTON_1] = false;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Input.keysMouse[GLFW_MOUSE_BUTTON_1] = false;
        }else{
            Input.keysMouse[GLFW_MOUSE_BUTTON_1] = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Input.mouseX = e.getX();
        Input.mouseY = e.getY();
        Input.updateInput();
    }
}
