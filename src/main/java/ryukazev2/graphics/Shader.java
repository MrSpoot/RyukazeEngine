package ryukazev2.graphics;

import lombok.Getter;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class Shader {

    private static final Logger LOGGER = LoggerFactory.getLogger(Shader.class);

    @Getter
    private final int program;
    private final int vertex;
    private final int fragment;
    private final Map<String, Integer> uniforms = new HashMap<>();

    public Shader(String vertexPath, String fragmentPath){
        vertex = glCreateShader(GL_VERTEX_SHADER);
        fragment = glCreateShader(GL_FRAGMENT_SHADER);
        program = glCreateProgram();

        String vertexCode = FileReader.readFile(vertexPath);
        String fragmentCode = FileReader.readFile(fragmentPath);

        compileShader(vertex, vertexCode);
        compileShader(fragment,fragmentCode);
        compileProgram(program);

        extractUniforms(vertexCode);
        extractUniforms(fragmentCode);
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

    // UNIFORM

    public void createUniform(String uniformName){
        int uniformLocation = glGetUniformLocation(program, uniformName);
        if(uniformLocation >= 0){
            uniforms.put(uniformName,uniformLocation);
        }else{
            LOGGER.warn("Could not find uniform ["+uniformName+"] in shader program ["+program+"]");
        }

    }

    public Integer getUniformLocation(String uniformName) {
        return uniforms.get(uniformName);
    }

    public void setUniform(String uniformName, int value){
        useProgram();
        if(getUniformLocation(uniformName) != null){
            glUniform1i(getUniformLocation(uniformName),value);
        }else{
            LOGGER.warn("Could not find uniform ["+uniformName+"] in shader program ["+program+"]");
        }
    }

    public void setUniform(String uniformName, Matrix4f value) {
        useProgram();
        if(getUniformLocation(uniformName) != null){
            try (MemoryStack stack = MemoryStack.stackPush()) {
                glUniformMatrix4fv(getUniformLocation(uniformName), false, value.get(stack.mallocFloat(16)));
            }
        }
    }

    public void setUniform(String uniformName, Vector4f value){
        useProgram();
        if(getUniformLocation(uniformName) != null){
            glUniform4f(getUniformLocation(uniformName),value.x,value.y,value.z,value.w);
        }else{
            LOGGER.warn("Could not find uniform ["+uniformName+"] in shader program ["+program+"]");
        }
    }

    public void setUniform(String uniformName, Vector3f value){
        useProgram();
        if(getUniformLocation(uniformName) != null){
            glUniform3f(getUniformLocation(uniformName),value.x,value.y,value.z);
        }else{
            LOGGER.warn("Could not find uniform ["+uniformName+"] in shader program ["+program+"]");
        }
    }

    public void setUniform(String uniformName, Vector2f value){
        useProgram();
        if(getUniformLocation(uniformName) != null){
            glUniform2f(getUniformLocation(uniformName),value.x,value.y);
        }else{
            LOGGER.warn("Could not find uniform ["+uniformName+"] in shader program ["+program+"]");
        }
    }

    public void setUniform(String uniformName, float value){
        useProgram();
        if(getUniformLocation(uniformName) != null){
            glUniform1f(getUniformLocation(uniformName),value);
        }else{
            LOGGER.warn("Could not find uniform ["+uniformName+"] in shader program ["+program+"]");
        }
    }

    public void useProgram(){
        glUseProgram(program);
    }

    private void extractUniforms(String shaderCode) {
        Pattern pattern = Pattern.compile("\\buniform\\s+\\w+\\s+(\\w+)\\s*;");

        Matcher matcher = pattern.matcher(shaderCode);

        while (matcher.find()) {
            String uniformName = matcher.group(1);
            LOGGER.info("Create uniform ["+uniformName+"] for shader ["+program+"]");
            createUniform(uniformName);
        }
    }

}
