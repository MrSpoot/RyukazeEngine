package ryukazev2.component;

import lombok.Getter;

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

    public CameraComponent build(){
        return this;
    }

}
