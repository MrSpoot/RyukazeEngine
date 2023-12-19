package SimpleTest;

import org.joml.Vector3f;
import ryukaze.core.Transform;
import ryukaze.objects.GameObject;
import ryukaze.objects.material.Material;
import ryukaze.objects.model.Cube;

public class Axis extends GameObject{

    public Axis() {
        super(new Transform(), null, null);

        Cube x = new Cube();
        Material mx = new Material();
        mx.setAmbient(new Vector3f(1f,0f,0f));
        x.getTransform().setScale(new Vector3f(2f,0.1f,0.1f));
        x.getTransform().setPosition(new Vector3f(1f,0f,0f));
        x.getMesh().setMaterial(mx);

        Cube y = new Cube();
        Material my = new Material();
        my.setAmbient(new Vector3f(0f,1f,0f));
        y.getTransform().setScale(new Vector3f(0.1f,2f,0.1f));
        y.getTransform().setPosition(new Vector3f(0f,1f,0f));
        y.getMesh().setMaterial(my);

        Cube z = new Cube();
        Material mz = new Material();
        mz.setAmbient(new Vector3f(0f,0f,1f));
        z.getTransform().setScale(new Vector3f(0.1f,0.1f,2f));
        z.getTransform().setPosition(new Vector3f(0f,0f,1f));
        z.getMesh().setMaterial(mz);

        this.addChildren(x);
        this.addChildren(y);
        this.addChildren(z);

    }


}
