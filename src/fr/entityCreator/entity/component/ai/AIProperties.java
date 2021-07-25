package fr.entityCreator.entity.component.ai;

import java.util.Objects;

public class AIProperties {
    private final float updatePerSecond;
    private final float speed;
    private final float speedError;
    private final float speedTurn;
    private final float speedTurnError;
    private final float rotateProbabilities;
    private final float staticTime;

    public AIProperties(float updatePerSecond, float speed, float speedError, float speedTurn, float speedTurnError, float rotateProbabilities, float staticTime) {
        this.updatePerSecond = updatePerSecond * 60;
        this.speed = speed;
        this.speedError = speedError;
        this.speedTurn = speedTurn;
        this.speedTurnError = speedTurnError;
        this.rotateProbabilities = rotateProbabilities;
        this.staticTime = staticTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AIProperties that = (AIProperties) o;
        return Float.compare(that.updatePerSecond, updatePerSecond) == 0 && Float.compare(that.speed, speed) == 0 && Float.compare(that.speedError, speedError) == 0 && Float.compare(that.speedTurn, speedTurn) == 0 && Float.compare(that.speedTurnError, speedTurnError) == 0 && Float.compare(that.rotateProbabilities, rotateProbabilities) == 0 && Float.compare(that.staticTime, staticTime) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(updatePerSecond, speed, speedError, speedTurn, speedTurnError, rotateProbabilities, staticTime);
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
