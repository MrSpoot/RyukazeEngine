package ryukaze.graphics;

import lombok.Getter;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ryukaze.Window;

import javax.sql.rowset.FilteredRowSet;

import static org.joml.Math.cos;
import static org.joml.Math.sin;
import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    @Getter
    private Matrix4f projection;
    private Vector3f position;
    private Vector3f front;
    private Vector3f up;
    private float lastX;
    private float lastY;
    private float yaw;
    private float pitch;
private boolean isFirstMouse = true;

    public Camera(float fov, float aspect, float zNear, float zFar, Vector3f position) {
        this.projection = new Matrix4f().perspective(fov, aspect,zNear,zFar);
        this.position = position;
        this.front = new Vector3f(0.0f,0.0f,-1.0f);
        this.up = new Vector3f(0.0f,1.0f,0.0f);
    }

    public void processInput(Window window, float deltaTime){
        float cameraSpeed = 2.5f * deltaTime; // adjust accordingly
        if(glfwGetKey(window.getWindow(), GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS){
            cameraSpeed = 10.0f * deltaTime;
        }
        if (glfwGetKey(window.getWindow(), GLFW_KEY_S) == GLFW_PRESS)
            position.add(new Vector3f(front).mul(cameraSpeed));
        if (glfwGetKey(window.getWindow(), GLFW_KEY_W) == GLFW_PRESS)
            position.sub(new Vector3f(front).mul(cameraSpeed));
        if (glfwGetKey(window.getWindow(), GLFW_KEY_D) == GLFW_PRESS)
            position.sub(new Vector3f(new Vector3f(front).cross(up)).normalize().mul(cameraSpeed));
        if (glfwGetKey(window.getWindow(), GLFW_KEY_A) == GLFW_PRESS)
            position.add(new Vector3f(new Vector3f(front).cross(up)).normalize().mul(cameraSpeed));
    }

    public void processMouseInput(long window, float width, float height, float xpos, float ypos){

        if(isFirstMouse){
            lastX = xpos;
            lastY = ypos;
            isFirstMouse = false;
        }

        float xOffset = xpos - lastX;
        float yOffset = ypos - lastY;

        lastX = xpos;
        lastY = ypos;

        float sensitivity = 0.05f;
        xOffset *= sensitivity;
        yOffset *= sensitivity;

        pitch += yOffset;
        yaw += xOffset;

        if(pitch > 89.0f){
            pitch = 89.0f;
        }
        if(pitch < -89.0f){
            pitch = -89.0f;
        }

        this.front.x = cos(Math.toRadians(yaw)) * cos(Math.toRadians(pitch));//pitch yaw
        this.front.y = sin(Math.toRadians(pitch));//pitch
        this.front.z = sin(Math.toRadians(yaw)) * cos(Math.toRadians(pitch));//pitch yaw

        this.front.normalize();

    }

    public Matrix4f getLookAt(){
        return new Matrix4f().lookAt(position,new Vector3f(position).sub(front),up);
    }
}
