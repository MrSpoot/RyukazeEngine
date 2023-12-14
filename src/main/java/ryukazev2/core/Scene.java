package ryukazev2.core;

import lombok.Data;
import ryukazev2.graphics.Shader;
import ryukazev2.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

@Data
public class Scene {

    private final Shader shader;
    private final List<GameObject> objects;

    public Scene(){
        this.shader = new Shader("src/main/resources/shader/default.scene.vert","src/main/resources/shader/default.scene.frag");
        this.objects = new ArrayList<>();
    }

    public void subscribe(GameObject object) {
        objects.add(object);
    }

    public void unsubscribe(GameObject object) {
        objects.remove(object);
    }


    public void render() {
        shader.useProgram();
        objects.forEach(GameObject::_render);
    }

    public void update() {
        objects.forEach(GameObject::_update);
    }
}
