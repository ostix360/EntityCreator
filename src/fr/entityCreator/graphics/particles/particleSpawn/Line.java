package fr.entityCreator.graphics.particles.particleSpawn;

import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.toolBox.Maths;
import org.joml.Vector3f;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.Objects;

public class Line implements ParticleSpawn {

    private Vector3f axis;
    private float length;

    @Override
    public Vector3f getParticleSpawnPosition(float x, float y, float z, float rotX, float rotY, float rotZ, float scale) {
        Vector3f actualAxis = Maths.rotateVector(this.axis, rotX, rotY, rotZ);
        actualAxis.normalize();
        actualAxis.mul(this.length * scale);
        actualAxis.mul(this.random.nextFloat() - 0.5F);
        Vector3f offset = new Vector3f(x, y, z);
        offset.add(actualAxis, actualAxis);
        return actualAxis;
    }

    @Override
    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString("1;" + this.length + ";" + this.axis.x + ";" + this.axis.y + ";" + this.axis.z));
    }

    @Override
    public void load(String[] values) {
        this.length = Float.parseFloat(values[1]);
        Vector3f axis = new Vector3f(Float.parseFloat(values[2]),Float.parseFloat(values[3]),Float.parseFloat(values[4]));
        this.axis = axis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Float.compare(line.length, length) == 0 && Objects.equals(axis, line.axis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(axis, length);
    }

    public Line setAxis(Vector3f axis) {
        this.axis = axis;
        return this;
    }

    public Line setLength(float length) {
        this.length = length;
        return this;
    }
}
