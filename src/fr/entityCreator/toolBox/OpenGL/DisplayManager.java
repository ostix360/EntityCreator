package fr.entityCreator.toolBox.OpenGL;


import fr.entityCreator.toolBox.Logger;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class DisplayManager {

    private static int width = 1080;
    private static int height = 720;



    public static void setWidth(int width) {
        DisplayManager.width = width;
    }

    public static void setHeight(int height) {
        DisplayManager.height = height;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

}
