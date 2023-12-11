package ryukaze.shaders;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukaze.utils.FileReader;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDeleteShader;

public class ShaderProgram {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShaderProgram.class);
    private final int program;
    private final int vertex;
    private final int fragment;
    private final Map<String, Integer> uniforms;

    public ShaderProgram(){
        this("src/main/resources/shader/default.scene.vert.old","src/main/resources/shader/default.scene.frag.old");
    }

    public ShaderProgram(String vertexPath, String fragmentPath){
        this.uniforms = new HashMap<>();
        vertex = glCreateShader(GL_VERTEX_SHADER);
        fragment = glCreateShader(GL_FRAGMENT_SHADER);
        program = glCreateProgram();
        compileShader(vertex, FileReader.readFile(vertexPath));
        compileShader(fragment,FileReader.readFile(fragmentPath));
        compileProgram(program);

    }

    private void compileProgram(int programId){
        glAttachShader(programId,vertex);
        glAttachShader(programId,fragment);
        glLinkProgram(program);
        glDeleteShader(vertex);
        glDeleteShader(fragment);
        checkShaderProgramLinking(program);
    }

    private void compileShader(int shaderId, String source){
        glShaderSource(shaderId,source);
        glCompileShader(shaderId);

        checkShaderCompilation(shaderId);
    }

    private void checkShaderCompilation(int shader) {
        int compiled = glGetShaderi(shader, GL_COMPILE_STATUS);
        if (compiled != GL_TRUE) {
            LOGGER.warn("Erreur de compilation du shader : "+glGetShaderInfoLog(shader));
        }
    }

    private void checkShaderProgramLinking(int shaderProgram) {
        int linked = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (linked != GL_TRUE) {
            LOGGER.warn("Erreur de liaison du programme de ryukaze.shaders : "+glGetProgramInfoLog(shaderProgram));
        }
    }

    public void createUniform(String uniformName){
        int uniformLocation = glGetUniformLocation(program, uniformName);
        if(uniformLocation < 0){
            throw new RuntimeException("Could not find uniform ["+uniformName+"] in shader program ["+program+"]");
        }
        uniforms.put(uniformName,uniformLocation);
    }

    public Integer getUniformLocation(String uniformName) {
        Integer location = uniforms.get(uniformName);
        return location;
    }

    public void setUniform(String uniformName, int value){
        if(getUniformLocation(uniformName) == null){
            createUniform(uniformName);
        }
        glUniform1i(getUniformLocation(uniformName),value);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        if(getUniformLocation(uniformName) == null){
            createUniform(uniformName);
        }
        try (MemoryStack stack = MemoryStack.stackPush()) {
            Integer location = uniforms.get(uniformName);
            if (location == null) {
                throw new RuntimeException("Could not find uniform [" + uniformName + "]");
            }
            glUniformMatrix4fv(location, false, value.get(stack.mallocFloat(16)));
        }
    }

    public void setUniform(String uniformName, Vector4f value){
        if(getUniformLocation(uniformName) == null){
            createUniform(uniformName);
        }
        glUniform4f(getUniformLocation(uniformName),value.x,value.y,value.z,value.w);
    }

    public void setUniform(String uniformName, Vector3f value){
        if(getUniformLocation(uniformName) == null){
            createUniform(uniformName);
        }
        glUniform3f(getUniformLocation(uniformName),value.x,value.y,value.z);
    }

    public void setUniform(String uniformName, Vector2f value){
        if(getUniformLocation(uniformName) == null){
            createUniform(uniformName);
        }
        glUniform2f(getUniformLocation(uniformName),value.x,value.y);
    }

    public void setUniform(String uniformName, float value){
        if(getUniformLocation(uniformName) == null){
            createUniform(uniformName);
        }
        glUniform1f(getUniformLocation(uniformName),value);
    }

    public int getProgramId(){
        return program;
    }

    public void useProgram(){
        glUseProgram(program);
    }

}
