package fr.entityCreator.graphics;

import fr.entityCreator.entity.BoundingModel;
import fr.entityCreator.entity.Entity;
import fr.entityCreator.entity.animated.animation.animatedModel.AnimatedModel;
import fr.entityCreator.graphics.model.MeshModel;
import fr.entityCreator.graphics.model.Model;
import fr.entityCreator.graphics.shader.ClassicShader;
import fr.entityCreator.graphics.shader.ShaderProgram;
import fr.entityCreator.graphics.textures.Texture;
import fr.entityCreator.toolBox.Config;
import fr.entityCreator.toolBox.OpenGL.OpenGlUtils;
import fr.entityCreator.toolBox.OpenGL.VAO;
import javafx.scene.shape.Mesh;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class CollisionObjectRenderer {
    public static List<BoundingModel> boundingModels = new ArrayList<>();

    public ClassicShader shader;


    public CollisionObjectRenderer(ClassicShader shader, Matrix4f projectionMatrix) {

        OpenGlUtils.cullBackFaces(true);
        this.shader = shader;
        this.shader.bind();
        this.shader.loadProjectionMatrix(projectionMatrix);
        this.shader.unBind();
    }

    public void render(Entity entity){
        for (BoundingModel boundingModels : boundingModels) {
            MeshModel model = boundingModels.getModel();
            prepareTexturedModel(model);
            prepareInstance(entity,boundingModels);
            glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
            finish();
        }
    }

    public void prepareInstance(Entity entity,BoundingModel model) {
        shader.loadTransformationMatrix(entity.getTransform().getTransformation().mul(model.getRelativeTransform().getTransformation(),new Matrix4f()));
        shader.offset.loadVector2fToUniform(new Vector2f(entity.getTextureXOffset(),entity.getTextureYOffset()));
    }

    public void prepareTexturedModel(MeshModel meshModel) {
        meshModel.getVAO().bind(0, 1, 2);
        shader.useFakeLighting.loadBooleanToUniform(true);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL_TEXTURE_2D,Config.WHITE.getId());
    }

    public void finish() {
        OpenGlUtils.cullBackFaces(true);
        VAO.unbind(0, 1, 2);
        Texture.unBindTexture();
    }

}
