package fr.entityCreator.graphics.particles;

import fr.entityCreator.graphics.shader.ShaderProgram;
import fr.entityCreator.toolBox.OpenGL.uniform.FloatUniform;
import fr.entityCreator.toolBox.OpenGL.uniform.MatrixUniform;
import org.joml.Matrix4f;

public class ParticleShader extends ShaderProgram {


	private final MatrixUniform projectionMatrix = new MatrixUniform("projectionMatrix");
	private final FloatUniform numberOfRows = new FloatUniform("numberOfRows");

	public ParticleShader() {
		super("particle");
		getAllUniformLocations(projectionMatrix,numberOfRows);
		validateProgram();
	}


	@Override
	protected void bindAllAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "modelViewMatrix");
		super.bindAttribute(5, "texOffsets");
		super.bindAttribute(6, "blendFactor");

	}

	protected void loadNumberOfRows(float numberOfRows) {
		this.numberOfRows.loadFloatToUniform(numberOfRows);
	}

	protected void loadProjectionMatrix(Matrix4f projection) {
		projectionMatrix.loadMatrixToUniform(projection);
	}

}
