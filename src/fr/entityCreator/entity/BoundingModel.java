package fr.entityCreator.entity;

import com.flowpowered.caustic.api.util.*;
import com.flowpowered.react.collision.shape.*;
import fr.entityCreator.core.exporter.*;
import fr.entityCreator.graphics.model.*;
import fr.entityCreator.toolBox.*;
import gnu.trove.list.*;
import gnu.trove.list.array.*;
import org.joml.*;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class BoundingModel {

    private MeshModel m;
    private ConvexMeshShape convexMeshShape;
    private Transform relativeTransform;

    public BoundingModel(MeshModel m) {
        this.m = m;
        setconvexMesh();
        this.relativeTransform = new Transform(new Vector3f(), new Vector3f(), 1);
    }

    private void setconvexMesh() {
        TFloatList meshPositions = new TFloatArrayList();
        TIntList meshIndices = new TIntArrayList();
        for (int i = 0; i < m.getVAO().getPosition().length; i++) {
            meshPositions.add(m.getVAO().getPosition()[i]);
        }
        meshIndices.addAll(m.getVAO().getIndices());
        TFloatList positions = new TFloatArrayList(meshPositions);
        TIntList indices = new TIntArrayList(meshIndices);
        MeshGenerator.toWireframe(positions, indices, false);
        final ConvexMeshShape meshShape = new ConvexMeshShape(positions.toArray(), positions.size() / 3, 12);
        for (int i = 0; i < indices.size(); i += 2) {
            meshShape.addEdge(indices.get(i), indices.get(i + 1));
        }
        meshShape.setIsEdgesInformationUsed(true);
        meshPositions.clear();
        meshIndices.clear();
        this.convexMeshShape = meshShape;

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
        if (!output.exists()) {
            output.getParentFile().mkdirs();
            output.createNewFile();
        }
        try (FileInputStream fis = new FileInputStream(m.getModelFile());
             FileChannel fcReader = fis.getChannel();
             FileOutputStream fos = new FileOutputStream(output);
             FileChannel fcWriter = fos.getChannel();
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (fcReader.read(buffer) != -1) {
                buffer.flip();
                fcWriter.write(buffer);
                buffer.flip();
            }
        }
    }


    @Override
    public String toString() {
        return m.getModelFile() == null ? "???" : m.getModelFile().getName().replace(".obj", "");
    }
}
