package ryukazev2.objects.model;

import ryukazev2.core.Transform;
import ryukazev2.objects.GameObject;
import ryukazev2.objects.mesh.SphereMesh;

public class Sphere extends GameObject {

    public Sphere() {
        super(new Transform(),new SphereMesh(25),null);
    }

    public Sphere(GameObject parent){
        super(new Transform(),new SphereMesh(25),parent);
    }
}
