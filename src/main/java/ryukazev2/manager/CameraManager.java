package ryukazev2.manager;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import ryukazev2.component.CameraComponent;
import ryukazev2.component.TransformComponent;
import ryukazev2.core.Entity;

public class CameraManager {

    private Entity camera;
    private Matrix4f projection;

    public CameraManager(){

    }

    public Matrix4f getViewMatrix(){
        TransformComponent transform = camera.getComponent(TransformComponent.class);
        return new Matrix4f().lookAt(transform.getPosition(),new Vector3f(transform.getPosition()).sub(transform.getRotation()),new Vector3f(0f,1f,0f));
    }

    public Matrix4f getProjectionMatrix(){
        return this.projection;
    }

}
