package ryukaze.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import ryukaze.Window;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private Matrix4f projection;
    private Vector3f position;
    private Vector3f front;
    private Vector3f up;

    public Camera(float fov, float aspect, float zNear, float zFar, Vector3f position) {
        this.projection = new Matrix4f().perspective(fov, aspect,zNear,zFar);
        this.position = position;
        this.front = new Vector3f(0.0f,0.0f,-1.0f);
        this.up = new Vector3f(0.0f,1.0f,0.0f);

    }

    public void processInput(Window window){
        float cameraSpeed = 0.05f; // adjust accordingly
        if (glfwGetKey(window.getWindow(), GLFW_KEY_Z) == GLFW_PRESS)
            position.add(front.mul(cameraSpeed));
        if (glfwGetKey(window.getWindow(), GLFW_KEY_S) == GLFW_PRESS)
            position.sub(front.mul(cameraSpeed));
        if (glfwGetKey(window.getWindow(), GLFW_KEY_Q) == GLFW_PRESS)
            position.sub(new Vector3f().normalize(new Vector3f().cross(front,up)).mul(cameraSpeed));
        if (glfwGetKey(window.getWindow(), GLFW_KEY_D) == GLFW_PRESS)
            position.add(new Vector3f().normalize(new Vector3f().cross(front,up)).mul(cameraSpeed));
    }

    public void render(){

        Matrix4f view = new Matrix4f().lookAt(position,front,up);



    }
}
