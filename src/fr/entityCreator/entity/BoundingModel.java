package fr.entityCreator.entity;

import com.flowpowered.react.collision.shape.*;
import fr.entityCreator.core.exporter.DataTransformer;
import fr.entityCreator.graphics.model.MeshModel;
import fr.entityCreator.toolBox.Config;
import org.joml.Vector3f;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BoundingModel {

    private MeshModel m;
    private Transform relativeTransform;

    public BoundingModel(MeshModel m) {
        this.m = m;
        this.relativeTransform = new Transform(new Vector3f(),new Vector3f(),1);
    }

    public static BoundingModel load(String content) {
        String[] lines = content.split("\n");
        Transform transform = Transform.load(lines[0]);
        MeshModel cm = loadModel(lines[1]);
        return new BoundingModel(cm).setRelativeTransform(transform);
    }

    private static MeshModel loadModel(String name) {
        return null;
    }

    public void setModel(MeshModel m) {
        this.m = m;
    }

    public BoundingModel setRelativeTransform(Transform relativeTransform) {
        this.relativeTransform = relativeTransform;
        return this;
    }

    public MeshModel getModel() {
        return m;
    }

    public Transform getRelativeTransform() {
        return relativeTransform;
    }

    public void export(FileChannel fc) throws IOException {
        relativeTransform.export(fc);
        fc.write(DataTransformer.casteString(m.getModelFile().getName() + "\n"));
        exportModel();
    }

    private void exportModel() throws IOException {
        String name = m.getModelFile().getName();
        File output = new File(Config.OUTPUT_FOLDER +
                "/models/entities/collision/" + name);
        if (!output.exists()){
            output.getParentFile().mkdirs();
            output.createNewFile();
        }
        try(FileInputStream fis = new FileInputStream(m.getModelFile());
            FileChannel fcReader = fis.getChannel();
            FileOutputStream fos = new FileOutputStream(output);
            FileChannel fcWriter = fos.getChannel();
        ){
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (fcReader.read(buffer) !=-1) {
                buffer.flip();
                fcWriter.write(buffer);
                buffer.flip();
            }
        }
    }



    @Override
    public String toString() {
        return m.getModelFile() == null ? "???" : m.getModelFile().getName().replace(".obj","");
    }
}
