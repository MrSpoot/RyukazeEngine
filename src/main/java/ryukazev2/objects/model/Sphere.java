package ryukazev2.objects.model;

import ryukazev2.core.Transform;
import ryukazev2.objects.GameObject;
import ryukazev2.objects.mesh.SphereMesh;

import java.util.HashMap;

public class Sphere extends GameObject {

    public Sphere() {
        super(new Transform(),new SphereMesh(6),null, new HashMap<>());
    }
}
