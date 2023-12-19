package ryukaze.graphics;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ryukaze.core.Engine;
import ryukaze.core.Transform;
import ryukaze.objects.GameObject;

public class Camera extends GameObject {

    @Getter
    private Matrix4f projection;
    private final Vector3f up;

    public Camera(float fov, float zNear, float zFar){
        super(new Transform(),null,null);
        this.projection = new Matrix4f().perspective(fov,  (float)Engine.getWindow().getWidth() / (float)Engine.getWindow().getHeight(),zNear,zFar);
        this.projection.m11(projection.m11() * -1); //Invert Y axis
        this.up = new Vector3f(0.0f,1.0f,0.0f);
        this.transform.position = new Vector3f(0f,-1f,-15f);
        Engine.getScene().setMainCamera(this);
    }

    public Matrix4f getLookAt(){
        return new Matrix4f().lookAt(this.getGlobalTransform().position,new Vector3f(this.getGlobalTransform().position).sub(this.getGlobalTransform().rotation),up);
    }

    @Override
    public void render(){
        Engine.getScene().getShader().setUniform("projection",projection);
        Engine.getScene().getShader().setUniform("view",getLookAt());
        Engine.getScene().getShader().setUniform("viewPos", this.getGlobalTransform().position);
    }
}
