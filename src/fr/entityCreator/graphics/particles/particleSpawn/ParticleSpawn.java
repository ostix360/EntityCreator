package fr.entityCreator.graphics.particles.particleSpawn;

import org.joml.Random;
import org.joml.Vector3f;

import java.io.PrintWriter;

public interface ParticleSpawn {
    Random random = new Random();

    Vector3f getParticleSpawnPosition(float x, float y, float z, float rotX, float rotY, float rotZ, float scale);

    void export(PrintWriter writer);

    void load(String[] values);
}
