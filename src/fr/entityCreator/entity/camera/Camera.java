package fr.entityCreator.entity.camera;


import fr.entityCreator.core.Input;
import fr.entityCreator.entity.Player;
import fr.entityCreator.entity.Transform;
import fr.entityCreator.graphics.MasterRenderer;
import fr.entityCreator.toolBox.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;

public class Camera implements ICamera {

    private float distanceFromPlayer = 25;
    private float angleAroundPlayer = 0;
    private Matrix4f projection;
    private boolean rotate = true;

    private final Vector3f position = new Vector3f(-50, 35, -100);
    float pitch = 20;
    float yaw = 0;
    private final float roll = 0;

    float elapsedMouseDY;

    private final Transform player;

    public Camera(Transform player) {
        this.player = player;
    }

    private float terrainHeight;

    @Override
    public Matrix4f getViewMatrix() {
        return Maths.createViewMatrix(this);
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        return projection;
    }

    @Override
    public Matrix4f getProjectionViewMatrix() {
        return projection.mul(getViewMatrix());
    }

    public void move() {
        this.terrainHeight = 0;
        calculateZoom();
        calculateAngleAroundPlayerAndPitch();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        caculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotation().y() + angleAroundPlayer);
        this.projection = MasterRenderer.getProjectionMatrix();
    }

    public boolean isRotate() {
        return rotate;
    }

    public void setRotate(boolean rotate) {
        this.rotate = rotate;
    }

    private float calculateHorizontalDistance() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calculateZoom() {
        float zoomLevel = Input.getMouseDWhell();

        distanceFromPlayer -= zoomLevel;
        if (distanceFromPlayer <= 3) {
            distanceFromPlayer = 3;
        }
        if (distanceFromPlayer >= 105) {
            distanceFromPlayer = 105;
        }
    }

    private void caculateCameraPosition(float horzontalDistance, float verticalDistance) {
        if (this.rotate) {
            this.player.getRotation().add(0,0.5f,0);

        }
        float theta = player.getRotation().y() + angleAroundPlayer;
        float xoffset = (float) (horzontalDistance * Math.sin(Math.toRadians(theta)));
        float zoffset = (float) (horzontalDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - xoffset;
        position.y = player.getPosition().y + verticalDistance;
        if (position.y < terrainHeight) {
            position.y = terrainHeight;
        }
        position.z = player.getPosition().z - zoffset;
    }

    private void calculateAngleAroundPlayerAndPitch() {
        if (Input.keysMouse[GLFW_MOUSE_BUTTON_1]) {
            float angleChange = Input.mouseDX * 0.1f;
            angleAroundPlayer -= angleChange;
            float pitchChange = Input.mouseDY * 0.1f;
            pitch += pitchChange;
            if (pitch >= 90) {
                pitch = 90;
            }
            if (pitch <= -4) {
                if (elapsedMouseDY < pitchChange) distanceFromPlayer += pitchChange * 1.4;
                pitch = -4;
            }
            elapsedMouseDY = pitchChange;
        }
    }

    public void invertPitch() {
        this.pitch = -pitch;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
