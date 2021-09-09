package fr.entityCreator.graphics.shader;

import fr.entityCreator.entity.Light;
import fr.entityCreator.toolBox.Color;
import fr.entityCreator.toolBox.OpenGL.uniform.*;
import org.joml.Matrix4f;

public class AnimatedModelShader extends ShaderProgram {

    private static final int MAX_JOINTS = 50;// max number of joints in a skeleton


    public MatrixUniform projectionMatrix = new MatrixUniform("projectionMatrix");
    public MatrixUniform viewMatrix = new MatrixUniform("viewMatrix");
    public MatrixUniformArray jointTransforms = new MatrixUniformArray("jointTransforms", MAX_JOINTS);
    public MatrixUniform transformation = new MatrixUniform("transformationMatrix");
    private final Vector3fUniform lightPos = new Vector3fUniform("lightPos");
    private final Vector3fUniform lightColor = new Vector3fUniform("lightColor");
    private final Vector3fUniform lightAttenuation = new Vector3fUniform("attenuation");
    private final FloatUniform lightPower = new FloatUniform("lightPower");
    private final FloatUniform reflectivity = new FloatUniform("reflectivity");
    private final FloatUniform shine = new FloatUniform("shine");
    public final BooleanUniform useSpecularMap = new BooleanUniform("useSpecularMap");
    private final IntUniform specularMap = new IntUniform("specularMap");
    private final IntUniform diffuseMap = new IntUniform("textureSampler");
    private final IntUniform normalMap = new IntUniform("normalMap");
    public final BooleanUniform useFakeLighting = new BooleanUniform("useFakeLighting");
    private final Vector3fUniform skyColor = new Vector3fUniform("skyColor");
    public final Vector2fUniform offset = new Vector2fUniform("offset");
    public final FloatUniform numberOfRows = new FloatUniform("numberOfRows");

    /**
     * Creates the shader program for the {@link AnimatedModelRenderer} by
     * loading up the vertex and fragment shader code files. It also gets the
     * location of all the specified uniform variables, and also indicates that
     * the diffuse texture will be sampled from texture unit 0.
     */
    public AnimatedModelShader() {
        super("animation");
        super.getAllUniformLocations(projectionMatrix, transformation, viewMatrix, jointTransforms,
                reflectivity, shine, skyColor,useSpecularMap,
                specularMap,diffuseMap,normalMap,useFakeLighting,offset,numberOfRows);
        super.getAllUniformLocations(lightPos);
        super.getAllUniformLocations(lightColor);
        super.getAllUniformLocations(lightAttenuation);
        super.getAllUniformLocations(lightPower);
        super.validateProgram();
    }
    public void connectTextureUnits(){
        diffuseMap.loadIntToUniform(0);
        specularMap.loadIntToUniform(1);
        normalMap.loadIntToUniform(2);
    }

    @Override
    protected void bindAllAttributes() {
        super.bindAttribute(0, "in_position");
        super.bindAttribute(1, "in_textureCoords");
        super.bindAttribute(2, "in_normal");
        super.bindAttribute(3, "in_jointIndices");
        super.bindAttribute(4, "in_weights");
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
        transformation.loadMatrixToUniform(matrix);
    }


    public void loadSkyColor(Color skyColor) {
        this.skyColor.loadVector3fToUniform(skyColor.getVec3f());
    }
}
