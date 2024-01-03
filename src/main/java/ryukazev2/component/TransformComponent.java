package ryukazev2.component;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@Getter
public class TransformComponent extends Component{

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    public TransformComponent() {
        this.position = new Vector3f(0f);
        this.rotation = new Vector3f(0f);
        this.scale = new Vector3f(1f);
    }

    public TransformComponent setPosition(float x, float y, float z){
        this.position = new Vector3f(x,y,z);
        return this;
    }

    public TransformComponent setRotation(float x, float y, float z){
        this.rotation = new Vector3f(x,y,z);
        return this;
    }

    public TransformComponent setScale(float x, float y, float z){
        this.scale = new Vector3f(x,y,z);
        return this;
    }

    public Matrix4f getModelMatrix(){
        return new Matrix4f()
                .translate(this.position)
                .rotateXYZ(this.rotation)
                .scale(this.scale);
    }
}
