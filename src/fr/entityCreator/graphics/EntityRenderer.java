package fr.entityCreator.graphics;


import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.animated.animation.animatedModel.AnimatedModel;
import fr.entityCreator.graphics.model.MeshModel;
import fr.entityCreator.graphics.model.Model;
import fr.entityCreator.graphics.textures.Texture;
import fr.entityCreator.graphics.shader.ClassicShader;
import fr.entityCreator.toolBox.OpenGL.OpenGlUtils;
import fr.entityCreator.toolBox.OpenGL.VAO;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class EntityRenderer implements IRenderer {

    private ClassicShader shader;

    public EntityRenderer(ClassicShader shader, Matrix4f projectionMatrix) {

        OpenGlUtils.cullBackFaces(true);
        this.shader = shader;
        this.shader.bind();
        this.shader.loadProjectionMatrix(projectionMatrix);
        this.shader.connectTextureUnits();
        this.shader.unBind();
    }

    public void render(List<Entity> entities) {
        for (Entity entity : entities) {
            Model model = entity.getModel();
            if (model.getTexture() == null) {
                return;
            }
            if (model instanceof AnimatedModel) {
                prepareAnimatedTexturedModel((AnimatedModel) model);
            } else {
                prepareTexturedModel(model);
            }
            prepareInstance(entity);
            glDrawElements(GL_TRIANGLES, entity.getModel().getMeshModel().getVertexCount(), GL_UNSIGNED_INT, 0);
            finish();
        }
    }

    private void prepareAnimatedTexturedModel(AnimatedModel model) {
        MeshModel mesh = model.getMeshModel();
        mesh.getVAO().bind(0, 1, 2, 3, 4);
        shader.isAnimated.loadBooleanToUniform(true);
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

    @Override
    public void prepareInstance(Entity entity) {
        shader.loadTransformationMatrix(entity.getTransform().getTransformation());
        shader.offset.loadVector2fToUniform(new Vector2f(entity.getTextureXOffset(),entity.getTextureYOffset()));
    }

    @Override
    public void prepareTexturedModel(Model model) {
        MeshModel meshModel = model.getMeshModel();
        meshModel.getVAO().bind(0, 1, 2);

        Texture texture = model.getTexture();
        shader.loadSpecular(texture.getReflectivity(), texture.getShineDamper());
        shader.numberOfRows.loadFloatToUniform(texture.getNumbersOfRows());
        shader.useSpecularMap.loadBooleanToUniform(texture.hasSpecularMap());
        shader.useFakeLighting.loadBooleanToUniform(texture.useFakeLighting());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        texture.bindTexture();
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL_TEXTURE_2D,texture.getProperties().getSpecularMap());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL_TEXTURE_2D,texture.getProperties().getNormalMap());
        if (texture.isTransparency())OpenGlUtils.cullBackFaces(false);
    }


    @Override
    public void finish() {
        OpenGlUtils.cullBackFaces(true);
        VAO.unbind(0, 1, 2, 3, 4);
        Texture.unBindTexture();
    }


}
