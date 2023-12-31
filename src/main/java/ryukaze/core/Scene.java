package ryukaze.core;

import lombok.Data;
import ryukaze.graphics.Camera;
import ryukaze.graphics.Shader;
import ryukaze.graphics.SkyBox;
import ryukaze.objects.GameObject;
import ryukaze.objects.controller.Controller;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;

@Data
public class Scene {

    private final Shader shader;
    private final List<Controller> controllers;
    private Camera mainCamera;
    private SkyBox sceneSkyBox;
    private Renderer renderer;
    private PostProcessingRenderer postProcessingRenderer;

    public Scene(){
        this.shader = new Shader("src/main/resources/shader/default.scene.vert","src/main/resources/shader/default.scene.frag");
        this.controllers = new ArrayList<>();
        this.sceneSkyBox = new SkyBox();
        this.renderer = new Renderer();
        this.postProcessingRenderer = new PostProcessingRenderer();
        glfwSetCursorPosCallback(Engine.getWindow().getWindowHandle(),(w,x,y) -> processMouseController((float)x,(float)y));
    }

    public void setMainCamera(Camera camera){
        this.mainCamera = camera;
        this.renderer.setCamera(camera);
    }

    public void subscribe(GameObject object) {
        Renderable renderable = new Renderable(0f,object);
        this.renderer.subscribe(renderable);
    }

    public void subscribeController(Controller controller) {
        controllers.add(controller);
    }

    public void processMouseController(float x, float y){
        controllers.forEach((c) -> c._processMouseInput(x,y));
    }

    public void render() {

        this.postProcessingRenderer.bindPostProcessingRender();

        if(this.sceneSkyBox != null){
            this.sceneSkyBox.render();
        }
        shader.useProgram(); //It works without this i don't know why wtf
        this.renderer.render();

        this.postProcessingRenderer.unbindPostProcessingRender();

        if(this.sceneSkyBox != null){
            this.sceneSkyBox.render();
        }

        this.renderer.render();
    }

    public void update() {
        this.renderer.update();
    }
}
