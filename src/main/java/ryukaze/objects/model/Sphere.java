package ryukaze.objects.model;

import ryukaze.core.Transform;
import ryukaze.objects.GameObject;
import ryukaze.objects.mesh.SphereMesh;

public class Sphere extends GameObject {

    public Sphere() {
        super(new Transform(),new SphereMesh(25),null);
    }

    public Sphere(GameObject parent){
        super(new Transform(),new SphereMesh(25),parent);
    }
}
