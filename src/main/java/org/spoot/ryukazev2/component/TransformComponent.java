package org.spoot.ryukazev2.component;

import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Getter
public class TransformComponent extends Component {

    private Vector3f position;
    private Quaternionf  rotation;
    private Vector3f scale;

    public TransformComponent() {
        this.position = new Vector3f();
        this.rotation = new Quaternionf();
        this.scale = new Vector3f(1f);
    }

    public TransformComponent setPosition(float x, float y, float z){
        this.position = new Vector3f(x,y,z);
        return this;
    }

    public TransformComponent setRotation(float x, float y, float z){
        this.rotation = new Quaternionf().rotateXYZ((float) Math.toRadians(x),
                (float) Math.toRadians(y),
                (float) Math.toRadians(z));
        return this;
    }

    public TransformComponent setScale(float x, float y, float z){
        this.scale = new Vector3f(x,y,z);
        return this;
    }

    public void translate(Vector3f value){
        this.position.add(value);
    }

    public void rotate(float x, float y, float z) {
        rotation.rotateX((float) Math.toRadians(x));
        rotation.rotateLocalY((float) Math.toRadians(y));
        rotation.rotateZ((float) Math.toRadians(z));
    }

    public Matrix4f getModelMatrix(){

        Matrix4f modelMatrix = new Matrix4f()
                .translate(this.position)
                .rotate(this.rotation)
                .scale(this.scale);

        if(this.getEntity().getParent() != null){
            if(this.getEntity().getParent().hasAllComponents(TransformComponent.class)){
                modelMatrix = new Matrix4f(this.getEntity().getParent().getComponent(TransformComponent.class).getModelMatrix().mul(modelMatrix));
            }
        }

        return modelMatrix;
    }
}
