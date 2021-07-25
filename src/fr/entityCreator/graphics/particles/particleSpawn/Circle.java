package fr.entityCreator.graphics.particles.particleSpawn;

import fr.entityCreator.toolBox.Maths;
import org.joml.Math;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.PrintWriter;
import java.util.Objects;

public class Circle implements ParticleSpawn {

    private Vector3f center;
    private float radius;

    @Override
    public Vector3f getParticleSpawnPosition(float x, float y, float z, float rotX, float rotY, float rotZ, float scale) {
        Vector3f actualHeading = Maths.rotateVector(this.center, rotX, rotY, rotZ);
        Vector3f randomPerpendicular = new Vector3f();
        do {
            Vector3f random = Maths.generateRandomUnitVector();
            new Vector3f(random).cross(actualHeading, randomPerpendicular);
        } while (randomPerpendicular.length() == 0.0F);
        randomPerpendicular.normalize();
        randomPerpendicular.mul(this.radius);
        float a = this.random.nextFloat();
        float b = this.random.nextFloat();
        if (a > b) {
            float temp = a;
            a = b;
            b = temp;
        }
        float randX = (float) (b * Math.cos(6.283185307179586D * (a / b)));
        float randY = (float) (b * Math.sin(6.283185307179586D * (a / b)));
        float distance = new Vector2f(randX, randY).length();
        randomPerpendicular.mul(distance * scale);
        Vector3f offset = new Vector3f(x, y, z);
        new Vector3f(offset).add(randomPerpendicular, randomPerpendicular);
        return randomPerpendicular;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return Float.compare(circle.radius, radius) == 0 && Objects.equals(center, circle.center);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, radius);
    }

    @Override
    public void export(PrintWriter writer) {
        writer.println("0;" + this.radius + ";" + this.center.x() + ";" + this.center.y() + ";" + this.center.z());
    }

    @Override
    public void load(String[] values) {
        this.radius = Float.parseFloat(values[1]);
        Vector3f center = new Vector3f(Float.parseFloat(values[2]),Float.parseFloat(values[3]),Float.parseFloat(values[4]));
        this.center = center;
    }

    public Circle setCenter(Vector3f center) {
        this.center = center.normalize();
        return this;
    }

    public Circle setRadius(float radius) {
        this.radius = radius;
        return this;
    }
}
