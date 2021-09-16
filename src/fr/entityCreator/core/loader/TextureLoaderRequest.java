package fr.entityCreator.core.loader;

import fr.entityCreator.core.resources.TextureProperties;
import fr.entityCreator.core.resourcesProcessor.GLRequest;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.graphics.textures.Texture;
import fr.entityCreator.graphics.textures.TextureLoader;

import java.io.InputStream;

public class TextureLoaderRequest extends GLRequest {
    private Entity e;
    private InputStream file;
    private String Sfile;
    private TextureLoader texture;
    private boolean isForEntity;


    public TextureLoaderRequest(String file, Entity entity) {
        this.e = entity;
        this.Sfile = file;
        isForEntity = true;
    }



    public TextureLoaderRequest(String file){
        this.Sfile = file;
        this.isForEntity = false;
    }

    public TextureLoaderRequest(InputStream file) {
        this.file = file;
        isForEntity = false;
    }

    public TextureLoader getTexture() {
        return texture;
    }

    @Override
    public void execute() {
        if (file != null) {
            texture = Loader.INSTANCE.loadTexture(file);
        }else{
            texture = Loader.INSTANCE.loadTexture(Sfile);
        }
        if (isForEntity){
            Texture tex = new Texture(texture, TextureProperties.DEFAULT());
            e.getModel().setTexture(tex);
        }
        super.execute();
    }
}
