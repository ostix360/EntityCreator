package fr.entityCreator.graphics.shader;


import fr.entityCreator.toolBox.Logger;
import fr.entityCreator.toolBox.OpenGL.uniform.Uniform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram {

    private int vertexShaderID;
    private int fragmentShaderID;
    private int programID;

    public ShaderProgram(String shadersName) {
        loadShaders(shadersName);
        processProgram();
    }

    protected abstract void bindAllAttributes();

    protected void getAllUniformLocations(Uniform... uniforms) {
        for (Uniform uniform : uniforms) {
            uniform.storeUniform(programID);
        }
    }

    protected void validateProgram() {
        glValidateProgram(programID);
        if (glGetProgrami(programID, GL_VALIDATE_STATUS) == GL_FALSE) {
            Logger.err("Failed to validate shader program : " + glGetProgramInfoLog(programID),
                    new IllegalStateException("Could not compile shader"));
        }
    }

    protected void bindAttribute(int attribute, String variableName) {
        glBindAttribLocation(programID, attribute, variableName);
    }

    public void bind() {
        glUseProgram(programID);
    }

    public void unBind() {
        glUseProgram(0);
    }


    private void loadShaders(String shadersName) {
        StringBuilder vertexSource = readShader(shadersName + ".vert");
        StringBuilder fragmentSource = readShader(shadersName + ".frag");
        vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShaderID, vertexSource);
        fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShaderID, fragmentSource);
        processShader(vertexShaderID);
        processShader(fragmentShaderID);
    }

    private StringBuilder readShader(String file) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                                    ShaderProgram.class.getResourceAsStream("/shader/" + file)));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            Logger.err("nothing find for the file " + file + " in a shader folder");
            e.printStackTrace();
        }

        return sb;
    }

    private void processShader(int shaderID) {
        glCompileShader(shaderID);
        if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            Logger.err("Failed to compile shader : " + shaderID + " || GL error : " + glGetShaderInfoLog(shaderID),
                    new IllegalStateException("Could not compile shader"));
        }

    }


    private void processProgram() {
        programID = glCreateProgram();
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);

        bindAllAttributes();

        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
            Logger.err("Failed to linked shader program : " + glGetProgramInfoLog(programID),
                    new IllegalStateException("Could not compile shader "));
        }

        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID, fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);

        getAllUniformLocations();


    }


    public void cleanUp() {
        unBind();
        glDeleteProgram(programID);
    }
}
