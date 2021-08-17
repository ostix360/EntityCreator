package fr.entityCreator.core;

public class Input {

    public static boolean[] keysMouse = new boolean[65535];
    public static boolean[] keys = new boolean[65535];
    public static float mouseX;
    public static float mouseY;
    public static float mouseDY;
    public static float mouseDX;
    public static float beforePositionX;
    public static float beforePositionY;

    public static void updateInput() {
        mouseDY = 0;
        mouseDX = 0;


        mouseDX = (float) mouseX - beforePositionX;
        mouseDY = (float) mouseY - beforePositionY;


        beforePositionX = mouseX;
        beforePositionY = mouseY;


    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

}
