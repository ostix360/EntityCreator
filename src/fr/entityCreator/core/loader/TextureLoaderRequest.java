package fr.entityCreator.core.loader;

import fr.entityCreator.core.resources.TextureProperties;
import fr.entityCreator.core.resourcesProcessor.GLRequest;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.graphics.model.Texture;
import fr.entityCreator.graphics.textures.TextureLoader;

public class TextureLoaderRequest extends GLRequest {
    private Entity e;
    private String file;

    public TextureLoaderRequest(String file, Entity entity) {
        this.e = entity;
        this.file = file;
    }

    @Override
    public void execute() {
        Texture tex;
        TextureLoader loader = Loader.INSTANCE.loadTexture(file);
        tex = new Texture(loader, TextureProperties.DEFAULT);
        e.getModel().setTexture(tex);
        super.execute();
    }
}
