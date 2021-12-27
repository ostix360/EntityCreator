package fr.entityCreator.core.loader;

import fr.entityCreator.core.resourcesProcessor.GLRequest;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.animated.animation.animatedModel.AnimatedModel;
import fr.entityCreator.entity.animated.colladaParser.dataStructures.*;
import fr.entityCreator.graphics.model.MeshModel;
import fr.entityCreator.graphics.model.Model;
import fr.entityCreator.graphics.model.ModelData;

public class ModelLoaderRequest extends GLRequest {
    private Entity e;
    private ModelData data;
    private boolean isAnimated = false;
    private MeshModel model;
    private String texture;
    private AnimatedModelData animData;

    public ModelLoaderRequest(Entity e, ModelData data,boolean isAnimated) {
        this.e = e;
        this.data = data;
        this.isAnimated = isAnimated;
        if (e.getModel() == null || e.getModel().isAnimated() != isAnimated){
             e.setModel(new Model(e.getName(),isAnimated));
        }

    }

    public ModelLoaderRequest(AnimatedModelData data, String texture, Entity e) {
        this.animData = data;
        this.data = data.getMeshData();
        this.isAnimated = true;
        this.texture = texture;
        this.e = e;
    }

    public ModelLoaderRequest(ModelData data,String tex, Entity e) {
        this.data = data;
        this.texture = tex;
        this.e = e;
    }
    public ModelLoaderRequest(ModelData data) {
        this.data = data;
    }

    @Override
    public void execute() {
        if (isAnimated){
          model = Loader.INSTANCE.loadToVAO(data.getIndices(), data.getVertices(),
       data.getTexcoords(), data.getNormals(), data.getJointsId(), data.getVertexWeights());
        }else{
            model = Loader.INSTANCE.loadToVAO(data.getVertices(),
                    data.getTexcoords(), data.getNormals(), data.getIndices());
        }
        if (e != null)e.getModel().setMeshModel(model);
        super.execute();
    }

    public MeshModel getModel() {
        return model;
    }
}
