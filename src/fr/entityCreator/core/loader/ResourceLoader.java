package fr.entityCreator.core.loader;


import fr.entityCreator.entity.*;
import fr.entityCreator.entity.animated.animation.loaders.*;

public class ResourceLoader {


    public static ModelLoaderRequest loadTexturedModel(String path, String texture, Entity e) {
        if (texture == null) {
            new IllegalArgumentException("texture for the model " + path + "is not available ");
        }
        assert texture != null;
        return new ModelLoaderRequest(LoadMeshModel.loadModel(path), texture,e);
    }

    public static ModelLoaderRequest loadTexturedAnimatedModel(String path, String texture, Entity e) {
        if (texture == null) {
            new IllegalArgumentException("texture for the model " + path + "is not available ");
        }
        assert texture != null;
        return new ModelLoaderRequest(AnimatedModelLoader.loadMeshData(path,  3 ),texture,e);
    }
}
