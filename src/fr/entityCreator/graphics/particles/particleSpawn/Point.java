package fr.entityCreator.graphics.particles.particleSpawn;

import org.joml.Vector3f;

import java.io.PrintWriter;

public class Point implements ParticleSpawn {
    @Override
    public Vector3f getParticleSpawnPosition(float x, float y, float z, float rotX, float rotY, float rotZ, float scale) {
        return new Vector3f(x, y, z);
    }


    @Override
    public void export(PrintWriter writer) {
        writer.println(2);
    }

    @Override
    public void load(String[] values) {

    }
}
