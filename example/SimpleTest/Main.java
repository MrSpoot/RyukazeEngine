package SimpleTest;

import org.joml.Vector3f;
import ryukaze.core.Engine;
import ryukaze.core.Options;
import ryukaze.core.Scene;
import ryukaze.graphics.Camera;
import ryukaze.objects.GameObject;
import ryukaze.objects.light.*;
import ryukaze.objects.material.Material;
import ryukaze.objects.mesh.Mesh;
import ryukaze.objects.mesh.SphereMesh;
import ryukaze.objects.model.*;
import ryukaze.physics.collider.SphereCollider;

public class Main {

    public static void main(String[] args) {

        Engine.init(new Options(true, 0, 1280,720 , 60));

        Scene scene = new Scene();
        Engine.setScene(scene);

        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.getTransform().setRotation(new Vector3f(0f,-1f,0f));
        directionalLight.setIntensity(0f);

        SpotLight spotLight = new SpotLight();
        spotLight.getTransform().setPosition(new Vector3f(0f,5f,0f));
        spotLight.setIntensity(0f);

        Sphere sphereLight = new Sphere();
        sphereLight.getTransform().setScale(new Vector3f(0.2f));

        PointLight pointlight = new PointLight();
        pointlight.setLinear(0.09f);
        pointlight.setQuadratic(0.032f);
        pointlight.getTransform().setPosition(new Vector3f(-10f,3f,0f));
        pointlight.addChildren(sphereLight);

        /*Axis axis = new Axis();
        axis.getTransform().setScale(new Vector3f(0.5f));
        axis.getTransform().setPosition(new Vector3f(0f,1f,0f));*/

        GameObject camera = new Camera(100,0.1f,1000f);
        camera.getTransform().setPosition(new Vector3f(0f,4.0f,-15f));

        MyController controller = new MyController();
        controller.addChildren(camera);
        controller.getTransform().setPosition(new Vector3f(0f,5f,0f));
        controller.getTransform().setRotation(new Vector3f(0f,(float)Math.toRadians(180f),0f));

        /*for(int i = -5; i < 10; i++){
            for (int j = -5; j < 10; j++) {
                GameObject cube = new Cube();
                cube.getTransform().setPosition(new Vector3f(i,i-j,j));
            }

        }*/

        GameObject bottom = new Plane();
        bottom.getTransform().setPosition(new Vector3f(0f,0f,0f));
        bottom.getTransform().setScale(new Vector3f(50,1f,50));

        Cube cube = new Cube();
        cube.getTransform().setPosition(new Vector3f(-10f,1f,0f));

        Engine.run();

    }

}
