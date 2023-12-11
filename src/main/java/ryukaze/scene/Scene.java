package ryukaze.scene;

import lombok.Data;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ryukaze.core.Window;
import ryukaze.core.StateManager;
import ryukaze.graphics.Camera;
import ryukaze.scene.model.Model;
import ryukaze.shaders.ShaderProgram;

import java.util.ArrayList;

@Data
public class Scene {

    private final ShaderProgram shaderProgram;
    private final Camera camera;
    private final ArrayList<Model> models;

    public Scene(){
        Window window = StateManager.window;
        this.shaderProgram = new ShaderProgram("src/main/resources/shader/default.scene.vert","src/main/resources/shader/default.scene.frag");

        this.camera = new Camera(75, (float) window.getWidth() / window.getHeight(),0.1f,1000.0f,new Vector3f(0.0f,0.0f,-3.0f));

        shaderProgram.useProgram();

        shaderProgram.createUniform("projection");
        shaderProgram.createUniform("view");
        shaderProgram.createUniform("model");

        this.models = new ArrayList<>();
    }

    public void addModelToScene(Model model){
        this.models.add(model);
    }

    public void render(){

        shaderProgram.useProgram();
        shaderProgram.setUniform("projection",camera.getProjection());
        shaderProgram.setUniform("view",camera.getLookAt());
        Matrix4f _model = new Matrix4f().translate(new Vector3f(0.0f,0.0f,0.0f));
        shaderProgram.setUniform("model",_model);

        for(Model model : models){
            model.render();
        }
    }

    public void update(){
        this.camera.processInput(StateManager.window,1.0f);
    }

}
