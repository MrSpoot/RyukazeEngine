package ryukaze.shaders;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class UniformsMap {

    private final int programId;
    private final Map<String, Integer> uniforms;

    public UniformsMap(int programId){
        this.programId = programId;
        uniforms = new HashMap<>();
    }

    public void createUniform(String uniformName){
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if(uniformLocation < 0){
            throw new RuntimeException("Could not find uniform ["+uniformName+"] in shader program ["+programId+"]");
        }
        uniforms.put(uniformName,uniformLocation);
    }

    public int getUniformLocation(String uniformName) {
        Integer location = uniforms.get(uniformName);
        if(location == null){
            throw new RuntimeException("Could not find uniform ["+uniformName+"]");
        }
        return location.intValue();
    }

    public void setUniform(String uniformName, int value){
        glUniform1i(getUniformLocation(uniformName),value);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            Integer location = uniforms.get(uniformName);
            if (location == null) {
                throw new RuntimeException("Could not find uniform [" + uniformName + "]");
            }
            glUniformMatrix4fv(location.intValue(), false, value.get(stack.mallocFloat(16)));
        }
    }

    public void setUniform(String uniformName, Vector4f value){
        glUniform4f(getUniformLocation(uniformName),value.x,value.y,value.z,value.w);
    }

    public void setUniform(String uniformName, Vector3f value){
        glUniform3f(getUniformLocation(uniformName),value.x,value.y,value.z);
    }

    public void setUniform(String uniformName, Vector2f value){
        glUniform2f(getUniformLocation(uniformName),value.x,value.y);
    }

    public void setUniform(String uniformName, float value){
        glUniform1f(getUniformLocation(uniformName),value);
    }


}
