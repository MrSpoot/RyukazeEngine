package input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shaders.ShaderProgram;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class InputManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputManager.class);
    private static int mode = GL_FILL;

    public static void processInput(long window){

        if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS){
            glfwSetWindowShouldClose(window,true);
        }
        if(glfwGetKey(window, GLFW_KEY_F1) == GLFW_PRESS){
            if(mode == GL_FILL){
                glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);
                mode = GL_LINE;
            }else{
                glPolygonMode(GL_FRONT_AND_BACK,GL_FILL);
                mode = GL_FILL;
            }

        }

    }

}
