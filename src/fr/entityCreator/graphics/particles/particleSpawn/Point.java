package fr.entityCreator.graphics.particles.particleSpawn;

import fr.entityCreator.core.exporter.DataTransformer;
import org.joml.Vector3f;

import javax.swing.*;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Point implements ParticleSpawn {
    private JPanel panel;

    public Point() {
        this.panel = new JPanel();
    }

    @Override
    public Vector3f getParticleSpawnPosition(float x, float y, float z, float rotX, float rotY, float rotZ, float scale) {
        return new Vector3f(x, y, z);
    }

    @Override
    public JPanel getSettingsPanel() {
        return panel;
    }


    @Override
    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString("2"));
    }

    @Override
    public String toString() {
        return "Point";
    }

    @Override
    public void load(String[] values) {

    }

    @Override
    public int getID() {
        return 2;
    }
}
