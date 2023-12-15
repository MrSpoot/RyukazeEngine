package ryukazev2.core;

import lombok.Data;
import ryukazev2.graphics.Shader;
import ryukazev2.objects.GameObject;
import ryukazev2.objects.controller.Controller;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;

@Data
public class Scene {

    private final Shader shader;
    private final List<GameObject> objects;
    private final List<Controller> controllers;

    public Scene(){
        this.shader = new Shader("src/main/resources/shader/default.scene.vert","src/main/resources/shader/default.scene.frag");
        this.objects = new ArrayList<>();
        this.controllers = new ArrayList<>();
        glfwSetCursorPosCallback(Engine.getWindow().getWindowHandle(),(w,x,y) -> processMouseController((float)x,(float)y));
    }

    public void subscribe(GameObject object) {
        objects.add(object);
    }

    public void unsubscribe(GameObject object) {
        objects.remove(object);
    }

    public void subscribeController(Controller controller) {
        controllers.add(controller);
    }

    public void unsubscribeController(Controller controller) {
        controllers.remove(controller);
    }

    public void processMouseController(float x, float y){
        controllers.forEach((c) -> c._processMouseInput(x,y));
    }

    public void render() {
        shader.useProgram();
        objects.forEach(GameObject::_render);
    }

    public void update() {
        objects.forEach(GameObject::_update);
    }
}
