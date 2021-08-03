package fr.entityCreator.graphics.particles.particleSpawn;

import fr.entityCreator.core.exporter.DataTransformer;
import org.joml.Vector3f;

import java.io.IOException;
import java.nio.channels.FileChannel;

public class Point implements ParticleSpawn {
    @Override
    public Vector3f getParticleSpawnPosition(float x, float y, float z, float rotX, float rotY, float rotZ, float scale) {
        return new Vector3f(x, y, z);
    }


    @Override
    public void export(FileChannel fc) throws IOException {
        fc.write(DataTransformer.casteString("2"));
    }

    @Override
    public void load(String[] values) {

    }
}
