package fr.entityCreator.entity.animated.animation.loaders;


import fr.entityCreator.core.Timer;
import fr.entityCreator.core.loader.Loader;
import fr.entityCreator.core.loader.ModelLoaderRequest;
import fr.entityCreator.core.resourcesProcessor.GLRequest;
import fr.entityCreator.core.resourcesProcessor.GLRequestProcessor;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.animated.animation.animatedModel.AnimatedModel;
import fr.entityCreator.entity.animated.animation.animatedModel.Joint;
import fr.entityCreator.entity.animated.colladaParser.colladaLoader.ColladaLoader;
import fr.entityCreator.entity.animated.colladaParser.dataStructures.AnimatedModelData;
import fr.entityCreator.entity.animated.colladaParser.dataStructures.JointData;
import fr.entityCreator.entity.animated.colladaParser.dataStructures.MeshData;
import fr.entityCreator.entity.animated.colladaParser.dataStructures.SkeletonData;
import fr.entityCreator.graphics.model.MeshModel;

public class AnimatedModelLoader {

    /**
     * Creates an AnimatedEntity from the data in an entity file. It loads up
     * the collada model data, stores the extracted data in a VAO, sets up the
     * joint heirarchy, and loads up the entity's texture.
     *
     * @param modelPath - the file containing the data for the entity.
     * @return The animated entity (no animation applied though)
     */
    public static AnimatedModel loadEntity(String modelPath, int maxWeights, Entity entity) {
        AnimatedModelData entityData = ColladaLoader.loadColladaModel(modelPath ,maxWeights);
        GLRequest request = new ModelLoaderRequest(entity,entityData.getMeshData(),true);
        GLRequestProcessor.sendRequest(request);
        SkeletonData skeletonData = entityData.getJointsData();
        Joint headJoint = createJoints(skeletonData.headJoint);
        return new AnimatedModel(entity.getName(), headJoint, skeletonData.jointCount);
    }

    /**
     * Constructs the joint-hierarchy skeleton from the data extracted from the
     * collada file.
     *
     * @param data - the joints data from the collada file for the head joint.
     * @return The created joint, with all its descendants added.
     */
    private static Joint createJoints(JointData data) {
        Joint joint = new Joint(data.index, data.nameId, data.bindLocalTransform);
        for (JointData child : data.children) {
            joint.addChild(createJoints(child));
        }
        return joint;
    }

    /**
     * Stores the mesh data in a VAO.
     *
     * @param data - all the data about the mesh that needs to be stored in the
     *             VAO.
     * @return The VAO containing all the mesh data for the model.
     */
    private static MeshModel createVao(MeshData data, Loader loader) {
        return loader.loadToVAO(data.getIndices(), data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getJointIds(), data.getVertexWeights());
    }

}
