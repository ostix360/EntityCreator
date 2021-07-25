package fr.entityCreator.core.loader;

import fr.entityCreator.core.resourcesProcessor.GLRequest;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.graphics.model.MeshModel;
import fr.entityCreator.graphics.model.Model;
import fr.entityCreator.graphics.model.ModelData;

public class ModelLoaderRequest extends GLRequest {
    private Entity e;
    private ModelData data;
    private boolean isAnimated = false;

    public ModelLoaderRequest(Entity e, ModelData data,boolean isAnimated) {
        this.e = e;
        this.data = data;
        this.isAnimated = isAnimated;
        e.setModel(new Model(e.getName()));
    }

    @Override
    public void execute() {
        MeshModel model;
        if (isAnimated){
          model = Loader.INSTANCE.loadToVAO(data.getIndices(), data.getVertices(),
       data.getTexcoords(), data.getNormals(), data.getJointsId(), data.getVertexWeights());
        }else{
            model = Loader.INSTANCE.loadToVAO(data.getVertices(),
                    data.getTexcoords(), data.getNormals(), data.getIndices());
        }
        e.getModel().setMeshModel(model);
        super.execute();
    }
}
