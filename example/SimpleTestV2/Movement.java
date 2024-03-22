package SimpleTestV2;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.spoot.ryukazev2.component.TransformComponent;
import org.spoot.ryukazev2.graphic.component.game.MeshComponent;
import org.spoot.ryukazev2.graphic.component.game.ShaderComponent;
import org.spoot.ryukazev2.graphic.component.game.shape.SphereShape;
import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.graphic.core.Input;
import org.spoot.ryukazev2.core.Time;
import org.spoot.ryukazev2.core.interfaces.IScript;
import org.spoot.ryukazev2.graphic.core.InputTouch;
import org.spoot.ryukazev2.graphic.graphics.Material;
import org.spoot.ryukazev2.graphic.graphics.Texture;
import org.spoot.ryukazev2.physic.component.body.Rigidbody;
import org.spoot.ryukazev2.physic.component.collider.SphereCollider;

import static org.lwjgl.glfw.GLFW.*;

public class Movement implements IScript {
    private Entity entity;
    private float xSens = 30f;
    private float ySens = 30f;

    private double lastSpawn = 0;
    private Entity sphere = null;

    @Override
    public void init(Entity entity) {
        this.entity = entity;
        this.entity.getComponent(TransformComponent.class).setPosition(0,2,150);
        initTouch();
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        float mouseX = Input.getXAxisRaw() * Time.deltaTime * xSens;
        float mouseY = Input.getYAxisRaw() * Time.deltaTime * ySens;

        this.entity.getComponent(TransformComponent.class).rotate(-mouseY,mouseX,0);

        Vector3f forward = new Vector3f(0, 0, -1).rotate(this.entity.getComponent(TransformComponent.class).getRotation());
        Vector3f right = new Vector3f(forward).cross(new Vector3f(0, 1, 0)).normalize();

        Vector3f velocity = new Vector3f(0f);

        float cameraSpeed = 30f * Time.deltaTime;

        if(Input.isPressed("sprint")){
            cameraSpeed *= 30f;
        }
        if (Input.isPressed("forward")) {
            velocity.add(forward);
        }
        if (Input.isPressed("back")) {
            velocity.sub(forward);
        }
        if (Input.isPressed("right")) {
            velocity.sub(right);
        }
        if (Input.isPressed("left")) {
            velocity.add(right);
        }
        if(Input.isPressed("reset")){
            if(glfwGetTime() - lastSpawn > 1){

                if(sphere == null){
                    Material blue = new Material();
                    blue.setDiffuse( new Texture(new Vector4f(0,0,1f,1.0f)));
                    sphere = new Entity().linkComponent(new TransformComponent().setPosition(0,30f,0).setScale(3f,3f,3f))
                            .linkComponent(new Rigidbody())
                            .linkComponent(new SphereCollider().setScale(1.5f))
                            .linkComponent(new MeshComponent().setMaterial(blue).applyShape(new SphereShape(15)).build())
                            .linkComponent(new ShaderComponent().build());
                }else{
                    TransformComponent transformComponent = sphere.getComponent(TransformComponent.class);
                    transformComponent.setPosition(0f,30f,0f);
                }



                lastSpawn = glfwGetTime();
            }
            //this.entity.getComponent(TransformComponent.class).setPosition(0,2f,150f);
            //this.entity.getComponent(TransformComponent.class).setRotation(0,0,0);
        }

        if(glfwGetTime() - lastSpawn > 1){

        }

        velocity.mul(cameraSpeed);

        this.entity.getComponent(TransformComponent.class).translate(velocity);
    }

    @Override
    public void cleanup() {

    }

    private void initTouch(){

        new InputTouch("forward",GLFW_KEY_W);
        new InputTouch("back",GLFW_KEY_S);
        new InputTouch("left",GLFW_KEY_A);
        new InputTouch("right",GLFW_KEY_D);
        new InputTouch("sprint",GLFW_KEY_LEFT_SHIFT);
        new InputTouch("reset",GLFW_KEY_B);

    }
}
