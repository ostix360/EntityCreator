package fr.entityCreator.entity.component.ai;

import org.joml.*;

public class AIProperties {
    private float updatePerSecond;
    private float speed;
    private float speedError;
    private float speedTurn;
    private float speedTurnError;
    private float rotateProbabilities;
    private float staticTime;
    private final Vector3f pos;
    private float distance;

    public AIProperties(float updatePerSecond, float speed, float speedError, float speedTurn, float speedTurnError, float rotateProbabilities, float staticTime, Vector3f pos, float distance) {
        this.updatePerSecond = updatePerSecond * 60;
        this.speed = speed;
        this.speedError = speedError;
        this.speedTurn = speedTurn;
        this.speedTurnError = speedTurnError;
        this.rotateProbabilities = rotateProbabilities;
        this.staticTime = staticTime;
        this.pos = pos;
        this.distance = distance;
    }

    public void setUpdatePerSecond(float updatePerSecond) {
        this.updatePerSecond = updatePerSecond;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setSpeedError(float speedError) {
        this.speedError = speedError;
    }

    public void setSpeedTurn(float speedTurn) {
        this.speedTurn = speedTurn;
    }

    public void setSpeedTurnError(float speedTurnError) {
        this.speedTurnError = speedTurnError;
    }

    public void setRotateProbabilities(float rotateProbabilities) {
        this.rotateProbabilities = rotateProbabilities;
    }

    public void setStaticTime(float staticTime) {
        this.staticTime = staticTime;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Vector3f getPos() {
        return pos;
    }

    public float getDistance() {
        return distance;
    }

    public float getRotateProbabilities() {
        return rotateProbabilities;
    }

    public float getSpeedError() {
        return speedError;
    }

    public float getUpdate() {
        return updatePerSecond;
    }

    public float getSpeed() {
        return speed;
    }

    public float getSpeedTurn() {
        return speedTurn;
    }

    public float getSpeedTurnError() {
        return speedTurnError;
    }

    public float getStaticTime() {
        return staticTime;
    }
}
