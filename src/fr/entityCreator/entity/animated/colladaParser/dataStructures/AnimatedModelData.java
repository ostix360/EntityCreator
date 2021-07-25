package fr.entityCreator.entity.animated.colladaParser.dataStructures;

import fr.entityCreator.graphics.model.ModelData;

/**
 * Contains the extracted data for an animated model, which includes the mesh data, and skeleton (joints heirarchy) data.
 *
 * @author Karl
 */
public class AnimatedModelData {

    private final SkeletonData joints;
    private final ModelData mesh;

    public AnimatedModelData(ModelData mesh, SkeletonData joints) {
        this.joints = joints;
        this.mesh = mesh;
    }

    public SkeletonData getJointsData() {
        return joints;
    }

    public ModelData getMeshData() {
        return mesh;
    }

}
