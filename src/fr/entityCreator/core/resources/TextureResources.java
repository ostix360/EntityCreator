package fr.entityCreator.core.resources;

import com.google.gson.annotations.Expose;

public class TextureResources {
    @Expose
    private final String name;
    @Expose
    private final String path;
    @Expose
    private final TextureProperties textureProperties;

    public TextureResources(String name, String path, TextureProperties textureProperties) {
        this.name = name;
        this.path = path;
        this.textureProperties = textureProperties;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public TextureProperties getTextureProperties() {
        return textureProperties;
    }
}
