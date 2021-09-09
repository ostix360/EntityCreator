package fr.entityCreator.graphics.shader;



import fr.entityCreator.entity.component.light.Light;
import fr.entityCreator.entity.camera.Camera;
import fr.entityCreator.toolBox.Color;
import fr.entityCreator.toolBox.Maths;
import fr.entityCreator.toolBox.OpenGL.uniform.*;
import org.joml.Matrix4f;
import org.joml.Vector4f;


public class TerrainShader extends ShaderProgram {


    private final MatrixUniform transformationMatrix = new MatrixUniform("transformationMatrix");
    private final MatrixUniform projectionMatrix = new MatrixUniform("projectionMatrix");
    private final MatrixUniform viewMatrix = new MatrixUniform("viewMatrix");
    private final Vector3fUniform lightPos = new Vector3fUniform("lightPos");
    private final Vector3fUniform lightColor = new Vector3fUniform("lightColor");
    private final Vector3fUniform lightAttenuation = new Vector3fUniform("attenuation");
    private final FloatUniform lightPower = new FloatUniform("lightPower");
    private final FloatUniform shine = new FloatUniform("shine");
    private final FloatUniform reflectivity = new FloatUniform("reflectivity");
    private final Vector3fUniform skyColour = new Vector3fUniform("skyColor");
    private final IntUniform backgroundTexture = new IntUniform("backgroundTexture");
    private final IntUniform rTexture = new IntUniform("rTexture");
    private final IntUniform gTexture = new IntUniform("gTexture");
    private final IntUniform bTexture = new IntUniform("bTexture");
    private final IntUniform blendMap = new IntUniform("blendMap");
    private final Vector4fUniform plane = new Vector4fUniform("plane");
    private final MatrixUniform toShadowMapSpace = new MatrixUniform("toShadowMapSpace");
    private final IntUniform shadowMap = new IntUniform("shadowMap");

    public TerrainShader() {
        super("terrainShader");
        super.getAllUniformLocations(transformationMatrix, projectionMatrix, viewMatrix
                , reflectivity, shine, backgroundTexture, rTexture, gTexture, bTexture, blendMap, skyColour);
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
        super.bindAttribute(2, "normal");
    }

    public void connectTerrainUnits() {
        backgroundTexture.loadIntToUniform(0);
        rTexture.loadIntToUniform(1);
        gTexture.loadIntToUniform(2);
        bTexture.loadIntToUniform(3);
        blendMap.loadIntToUniform(4);
        shadowMap.loadIntToUniform(5);
    }


    public void loadShaderMapSpace(Matrix4f matrix) {
        toShadowMapSpace.loadMatrixToUniform(matrix);
    }

    public void loadClipPlane(Vector4f value) {
        plane.loadVec4fToUniform(value);
    }

    public void loadSkyColour(Color colour) {
        skyColour.loadVector3fToUniform(colour.getVec3f());
    }

    public void loadSpecular(float reflectivity, float shineDamper) {
        this.reflectivity.loadFloatToUniform(reflectivity);
        this.shine.loadFloatToUniform(shineDamper);
    }

    public void loadLight(Light light) {
        lightPos.loadVector3fToUniform(light.getPosition());
        lightColor.loadVector3fToUniform(light.getColourVec3f());
        lightAttenuation.loadVector3fToUniform(light.getAttenuation());
        lightPower.loadFloatToUniform(light.getPower());
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

}
