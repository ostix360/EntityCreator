package fr.entityCreator.core.loader;

import fr.entityCreator.core.resources.TextureProperties;
import fr.entityCreator.core.resourcesProcessor.GLRequest;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.graphics.textures.Texture;
import fr.entityCreator.graphics.textures.TextureLoader;

public class TextureLoaderRequest extends GLRequest {
    private Entity e;
    private String file;
    private TextureLoader texture;
    private boolean isForEntity;


    public TextureLoaderRequest(String file, Entity entity) {
        this.e = entity;
        this.file = file;
        isForEntity = true;
    }

    public TextureLoaderRequest(String file) {
        this.file = file.replaceAll("%20", " ");
        isForEntity = false;
    }

    public TextureLoader getTexture() {
        return texture;
    }

    @Override
    public void execute() {
        texture = Loader.INSTANCE.loadTexture(file);
        if (isForEntity){
            Texture tex = new Texture(texture, TextureProperties.DEFAULT());
            e.getModel().setTexture(tex);
        }
        super.execute();
    }
}
