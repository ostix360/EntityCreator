package fr.entityCreator.entity;


import com.flowpowered.react.math.*;
import fr.entityCreator.core.Input;
import fr.entityCreator.graphics.model.Model;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {



    private static final float RUN_SPEED = 160;
    private static final float TURN_SPEED = 780;
    public static final float GRAVITY = 100f;
    private static final float JUMP_POWER = 1;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    public boolean canJump = true;

    private final int health = 10;
    private final int sprintTime = 60;
    private final boolean isSprinting = false;


    public Player(Model model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
    }

    public Player(Entity e) {
        super(e.getModel(), e.getPosition(), e.getRotation(), e.getScale());
    }

    @Override
    public void update() {
        this.move();
        super.update();
    }

    public void move() {
        checkInputs();
        super.increaseRotation(new Vector3f(0, this.currentTurnSpeed * 0.0023f, 0));
        float distance = currentSpeed * 0.006f;
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotation().y())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotation().y())));
        forceToCenter.set(new Vector3(dx, 0, dz));
        //    upwardsSpeed += GRAVITY;
        if (upwardsSpeed <= 0f) {
            canJump = true;
            upwardsSpeed = 0f;
        }
        forceToCenter.add(new Vector3(0, upwardsSpeed, 0));

        super.increasePosition(new Vector3f(dx, upwardsSpeed, dz));
        if (!canJump) {
            this.upwardsSpeed -= GRAVITY;
        }

    }

    private void jump() {
        if (canJump) {
            this.upwardsSpeed = JUMP_POWER;
            canJump = false;
        }
    }

    private void checkInputs() {
        this.movement = MovementType.STATIC;
        if (Input.keys[GLFW_KEY_W] || Input.keys[GLFW_KEY_UP]) {
            this.movement = MovementType.FORWARD;
            this.currentSpeed = RUN_SPEED;
        } else if (Input.keys[GLFW_KEY_S] || Input.keys[GLFW_KEY_DOWN]) {
            this.movement = MovementType.BACK;
            this.currentSpeed = -RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if (Input.keys[GLFW_KEY_A] || Input.keys[GLFW_KEY_LEFT]) {
            this.currentTurnSpeed = TURN_SPEED;
        } else if (Input.keys[GLFW_KEY_D] || Input.keys[GLFW_KEY_RIGHT]) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }

        if (Input.keys[GLFW_KEY_SPACE]) {
            this.movement = MovementType.JUMP;
            this.jump();
        }
        if (Input.keys[GLFW_KEY_LEFT_SHIFT]){
            this.upwardsSpeed -= RUN_SPEED;
        }
    }


    public int getHealth() {
        return health;
    }

    public int getSprintTime() {
        return sprintTime;
    }


}

