package engine.graph;

import engine.scene.Scene;
import engine.Window;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;

public class Render {

    private SceneRender sceneRender;

    public Render(){
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        sceneRender = new SceneRender();
    }

    public void cleanup(){
        sceneRender.cleanup();
    }

    public void render(Window window, Scene scene){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0,0, window.getWidth(), window.getHeight());
        sceneRender.render(scene);
    }

}
