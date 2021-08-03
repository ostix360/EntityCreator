package fr.entityCreator.core.exporter;

import java.nio.ByteBuffer;

public class DataTransformer {

    public static ByteBuffer casteString(String data) {
        byte[] bytes = data.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        return buffer;
    }
}
