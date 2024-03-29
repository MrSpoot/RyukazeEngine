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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class Movement implements IScript {
    private Entity entity;
    private float xSens = 30f;
    private float ySens = 30f;

    private double lastSpawn = 0;
    private List<Entity> spheres = null;



    @Override
    public void init(Entity entity) {
        this.entity = entity;
        this.entity.getComponent(TransformComponent.class).setPosition(0,2,150);
        this.spheres = new ArrayList<>();
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
            cameraSpeed *= 5f;
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




                if(spheres.isEmpty()){

                    for(int i = 0; i < 50; i++){

                        Material mat = new Material();
                        mat.setDiffuse( new Texture(new Vector4f(getRandomFloat(0f,1f),getRandomFloat(0f,1f),getRandomFloat(0f,1f),1.0f)));

                        spheres.add(new Entity().linkComponent(new TransformComponent().setPosition(0,getRandomFloat(20f,50f),0).setScale(2f,2f,2f))
                                .linkComponent(new Rigidbody().setForce(new Vector3f(getRandomFloat(-100f,100f),0,getRandomFloat(-100f,100f))))
                                .linkComponent(new SphereCollider())
                                .linkComponent(new MeshComponent().setMaterial(mat).applyShape(new SphereShape(15)).build())
                                .linkComponent(new ShaderComponent().build()));

                    }


                }else{
                    for(Entity entity1 : spheres){
                        entity1.getComponent(Rigidbody.class).setVelocity(new Vector3f(0f)).setForce(new Vector3f(getRandomFloat(-100f,100f),0,getRandomFloat(-100f,100f)));
                        TransformComponent transformComponent = entity1.getComponent(TransformComponent.class);
                        transformComponent.setPosition(0f,getRandomFloat(20f,50f),0f);
                    }

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

    private float getRandomFloat(float min, float max){
        Random random = new Random();
        return min + random.nextFloat() * (max - min);
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
