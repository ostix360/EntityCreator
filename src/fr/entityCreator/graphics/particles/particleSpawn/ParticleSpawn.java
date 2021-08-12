package fr.entityCreator.graphics.particles.particleSpawn;

import org.joml.Random;
import org.joml.Vector3f;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

public interface ParticleSpawn {
    Random random = new Random();

    Vector3f getParticleSpawnPosition(float x, float y, float z, float rotX, float rotY, float rotZ, float scale);

    JPanel getSettingsPanel();

    void export(FileChannel fc) throws IOException;

    void load(String[] values);

    int getID();
}
