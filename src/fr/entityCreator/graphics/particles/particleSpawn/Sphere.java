package fr.entityCreator.graphics.particles.particleSpawn;

import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.toolBox.Maths;
import org.joml.Vector3f;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.Objects;
import java.util.Random;

public class Sphere implements ParticleSpawn {

    private float radius;

    @Override
    public Vector3f getParticleSpawnPosition(float x, float y, float z, float rotX, float rotY, float rotZ, float scale) {
        Vector3f spherePoint = Maths.generateRandomUnitVector();

        spherePoint.mul(this.radius * scale);

        float fromCenter = new Random().nextFloat();

        spherePoint.mul(fromCenter);

        Vector3f offset = new Vector3f(x, y, z);

        spherePoint.add(offset, spherePoint);

        return spherePoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sphere sphere = (Sphere) o;
        return Float.compare(sphere.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius);
    }

    @Override
    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString("3;" + this.radius));
    }

    @Override
    public void load(String[] values) {
        this.radius = Float.parseFloat(values[1]);
    }

    public Sphere setRadius(float radius) {
        this.radius = radius;
        return this;
    }

}
