package shaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileReader;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glDeleteShader;

public class ShaderProgram {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShaderProgram.class);
    private final int program;
    private final int vertex;
    private final int fragment;

    public ShaderProgram(){
        vertex = glCreateShader(GL_VERTEX_SHADER);
        fragment = glCreateShader(GL_FRAGMENT_SHADER);
        program = glCreateProgram();
        compileShader(vertex, FileReader.readFile("src/main/resources/shader/vertex.vert"));
        compileShader(fragment,FileReader.readFile("src/main/resources/shader/fragment.frag"));
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
            LOGGER.warn("Erreur de liaison du programme de shaders : "+glGetProgramInfoLog(shaderProgram));
        }
    }

    public int getProgramId(){
        return program;
    }

    public void useProgram(){
        glUseProgram(program);
    }

}
