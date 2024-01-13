package SimpleTest;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.spoot.ryukaze.core.Transform;
import org.spoot.ryukaze.objects.GameObject;
import org.spoot.ryukaze.objects.material.Material;
import org.spoot.ryukaze.objects.material.Texture;
import org.spoot.ryukaze.objects.model.Cube;

public class Axis extends GameObject{

    public Axis() {
        super(new Transform(), null, null,null);

        Cube x = new Cube(this);
        Material mx = new Material();
        mx.getTextures().put("diffuse",new Texture(new Vector4f(1f,0f,0f,1f)));
        x.getTransform().setScale(new Vector3f(3f,0.1f,0.1f));
        x.getTransform().setPosition(new Vector3f(1.5f,0f,0f));
        x.getMesh().setMaterial(mx);

        Cube y = new Cube(this);
        Material my = new Material();
        my.getTextures().put("diffuse",new Texture(new Vector4f(0f,1f,0f,1f)));
        y.getTransform().setScale(new Vector3f(0.1f,3f,0.1f));
        y.getTransform().setPosition(new Vector3f(0f,1.5f,0f));
        y.getMesh().setMaterial(my);

        Cube z = new Cube(this);
        Material mz = new Material();
        mz.getTextures().put("diffuse",new Texture(new Vector4f(0f,0f,1f,1f)));
        z.getTransform().setScale(new Vector3f(0.1f,0.1f,3f));
        z.getTransform().setPosition(new Vector3f(0f,0f,1.5f));
        z.getMesh().setMaterial(mz);

        this.addChildren(x);
        this.addChildren(y);
        this.addChildren(z);

    }


}
