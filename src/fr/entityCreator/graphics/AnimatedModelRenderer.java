package fr.entityCreator.graphics;

import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.animated.animation.animatedModel.AnimatedModel;
import fr.entityCreator.entity.camera.ICamera;
import fr.entityCreator.graphics.model.MeshModel;
import fr.entityCreator.graphics.shader.AnimatedModelShader;
import fr.entityCreator.graphics.textures.Texture;
import fr.entityCreator.toolBox.OpenGL.OpenGlUtils;
import fr.entityCreator.toolBox.OpenGL.VAO;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;


/**
 * This class deals with rendering an animated entity. Nothing particularly new
 * here. The only exciting part is that the joint transforms get loaded up to
 * the shader in a uniform array.
 *
 * @author Karl
 */
public class AnimatedModelRenderer {

    private final AnimatedModelShader shader;

    /**
     * Initializes the shader program used for rendering animated models.
     */
    public AnimatedModelRenderer(AnimatedModelShader shader) {
        this.shader = shader;
    }

    /**
     * Renders an animated entity. The main thing to note here is that all the
     * joint transforms are loaded up to the shader to a uniform array. Also 5
     * attributes of the VAO are enabled before rendering, to include joint
     * indices and weights.
     *
     * @param entity   - the animated entity to be rendered.
     * @param camera   - the camera used to render the entity.
     * @param lightDir - the direction of the light in the scene.
     */
    public void render(Entity entity ) {
        if (entity.getModel() == null || entity.getModel().getMeshModel() == null || entity.getModel().getTexture() == null){
            return;
        }
        prepareAnimatedTexturedModel((AnimatedModel) entity.getModel());

        prepare(entity);

        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getMeshModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        VAO.unbind(0, 1, 2, 3, 4);
        Texture.unBindTexture();
        finish();
    }

    private void prepareAnimatedTexturedModel(AnimatedModel model) {
        MeshModel mesh = model.getMeshModel();

        mesh.getVAO().bind(0, 1, 2, 3, 4);
        shader.jointTransforms.loadMatrixArray(model.getJointTransforms());

        Texture texture = model.getTexture();
        shader.loadSpecular(texture.getReflectivity(), texture.getShineDamper());
        shader.useSpecularMap.loadBooleanToUniform(texture.hasSpecularMap());
        shader.useFakeLighting.loadBooleanToUniform(texture.useFakeLighting());
        shader.numberOfRows.loadFloatToUniform(texture.getNumbersOfRows());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        texture.bindTexture();
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL_TEXTURE_2D,texture.getProperties().getSpecularMap());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL_TEXTURE_2D,texture.getProperties().getNormalMap());
        if (texture.isTransparency())OpenGlUtils.cullBackFaces(false);
    }

    /**
     * Deletes the shader program when the game closes.
     */
    public void cleanUp() {
        shader.cleanUp();
    }

    /**
     * Starts the shader program and loads up the projection view matrix, as
     * well as the light direction. Enables and disables a few settings which
     * should be pretty self-explanatory.
     *
     * @param camera   - the camera being used.
     * @param lightDir - the direction of the light in the scene.
     */
    private void prepare(Entity entity) {
        OpenGlUtils.antialias(true);
        OpenGlUtils.disableBlending();
        OpenGlUtils.enableDepthTesting(true);
        shader.loadTransformationMatrix(entity.getTransform().getTransformation());
        shader.offset.loadVector2fToUniform(new Vector2f(entity.getTextureXOffset(),entity.getTextureYOffset()));
    }

    /**
     * Stops the shader program after rendering the entity.
     */
    private void finish() {
        shader.unBind();
    }

}
