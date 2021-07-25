package fr.entityCreator.graphics.shader;


import fr.entityCreator.entity.Light;
import fr.entityCreator.entity.camera.Camera;
import fr.entityCreator.toolBox.Color;
import fr.entityCreator.toolBox.Maths;
import fr.entityCreator.toolBox.OpenGL.uniform.*;
import org.joml.Matrix4f;

public class ClassicShader extends ShaderProgram{


    private final MatrixUniform transformationMatrix = new MatrixUniform("transformationMatrix");
    private final MatrixUniform projectionMatrix = new MatrixUniform("projectionMatrix");
    private final MatrixUniform viewMatrix = new MatrixUniform("viewMatrix");
    private final Vector3fUniform lightPos = new Vector3fUniform("lightPos");
    private final Vector3fUniform lightColor = new Vector3fUniform("lightColor");
    private final Vector3fUniform lightAttenuation = new Vector3fUniform("attenuation");
    private final FloatUniform lightPower = new FloatUniform("lightPower");
    private final FloatUniform reflectivity = new FloatUniform("reflectivity");
    private final FloatUniform shine = new FloatUniform("shine");
    public final MatrixUniformArray jointTransforms = new MatrixUniformArray("jointTransforms", 50);
    public final BooleanUniform isAnimated = new BooleanUniform("isAnimated");
    private final Vector3fUniform skyColor = new Vector3fUniform("skyColor");

    public ClassicShader() {
        super("shader");
        super.getAllUniformLocations(transformationMatrix, projectionMatrix, viewMatrix,
                reflectivity, shine, skyColor, jointTransforms, isAnimated);
        super.getAllUniformLocations(lightPos);
        super.getAllUniformLocations(lightColor);
        super.getAllUniformLocations(lightAttenuation);
        super.getAllUniformLocations(lightPower);
        super.validateProgram();
    }

    @Override
    protected void bindAllAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normals");
        super.bindAttribute(3, "jointIndices");
        super.bindAttribute(4, "weights");
    }


    public void loadLight(Light light) {
        lightPos.loadVector3fToUniform(light.getPosition());
        lightColor.loadVector3fToUniform(light.getColourVec3f());
        lightAttenuation.loadVector3fToUniform(light.getAttenuation());
        lightPower.loadFloatToUniform(light.getPower());
    }

    public void loadSpecular(float reflectivity, float shineDamper) {
        this.reflectivity.loadFloatToUniform(reflectivity);
        this.shine.loadFloatToUniform(shineDamper);
    }


    // Projection Transformation View Matrix

    public void loadTransformationMatrix(Matrix4f matrix) {
        transformationMatrix.loadMatrixToUniform(matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        projectionMatrix.loadMatrixToUniform(matrix);
    }

    public void loadViewMatrix(Camera cam) {
        viewMatrix.loadMatrixToUniform(Maths.createViewMatrix(cam));
    }


    public void loadSkyColor(Color skyColor) {
        this.skyColor.loadVector3fToUniform(skyColor.getVec3f());
    }
}
