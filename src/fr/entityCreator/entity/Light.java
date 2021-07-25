package fr.entityCreator.entity;


import fr.entityCreator.toolBox.Color;
import org.joml.Vector3f;

public class Light {
    private Vector3f position;
    private final Color colour;
    private Vector3f attenuation = new Vector3f(1, 0, 0);
    private float power = 1;


    public Light(Vector3f position, Color colour, float power) {
        this.position = position;
        this.colour = colour;
        this.power = power;
    }

    public Light(Vector3f position, Color colour) {
        this.position = position;
        this.colour = colour;
    }

    public Light(Vector3f position, Color colour, Vector3f attenuation) {
        this.position = position;
        this.colour = colour;
        this.attenuation = attenuation;
    }

    public float getPower() {
        return power;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getAttenuation() {
        return attenuation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColourVec3f() {
        return colour.getVec3f();
    }

}
