package ryukazev2.component.game;

import lombok.Getter;
import org.joml.Vector3f;

@Getter
public class CameraComponent extends Component {

    private float fov;
    private float zNear;
    private float zFar;

    public CameraComponent(){
        this.fov = 75f;
        this.zNear = 0.1f;
        this.zFar = 1000f;
    }

    public CameraComponent setFov(float fov){
        this.fov = fov;
        return this;
    }

    public CameraComponent setZNear(float zNear){
        this.zNear = zNear;
        return this;
    }

    public CameraComponent setZFar(float zFar){
        this.zFar = zFar;
        return this;
    }

    public Vector3f getTarget(){
        Vector3f forward = new Vector3f(0,0,-1);
        forward.rotate(this.getEntity().getComponent(TransformComponent.class).getRotation());

        return new Vector3f(this.getEntity().getComponent(TransformComponent.class).getPosition()).add(forward);
    }

    public CameraComponent build(){
        return this;
    }

}
