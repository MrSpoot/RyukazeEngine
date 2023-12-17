package ryukazev2.graphics;

import lombok.Data;
import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ryukazev2.core.Engine;
import ryukazev2.core.Transform;
import ryukazev2.objects.GameObject;

import java.util.HashMap;

public class Camera extends GameObject {

    @Getter
    private Matrix4f projection;
    private final Vector3f up;

    public Camera(float fov, float zNear, float zFar){
        super(new Transform(),null,null);
        this.projection = new Matrix4f().perspective(fov,  (float)Engine.getWindow().getWidth() / (float)Engine.getWindow().getHeight(),zNear,zFar);
        this.up = new Vector3f(0.0f,1.0f,0.0f);
        this.transform.position = new Vector3f(0f,-1f,-15f);
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
