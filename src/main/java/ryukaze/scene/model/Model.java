package ryukaze.scene.model;

import lombok.Data;
import org.joml.Vector3f;

@Data
public abstract class Model {

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    public Model(){
        this.position = new Vector3f(0.0f);
        this.rotation = new Vector3f(0.0f);
        this.scale = new Vector3f(1.0f);
    }

    public void render() {

    }

    public void update() {

    }
}
