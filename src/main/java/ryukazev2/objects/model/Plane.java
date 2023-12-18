package ryukazev2.objects.model;

import ryukazev2.core.Transform;
import ryukazev2.objects.GameObject;
import ryukazev2.objects.mesh.PlaneMesh;

public class Plane extends GameObject {

    public Plane() {
        super(new Transform(),new PlaneMesh(),null);
    }

    public Plane(GameObject parent){
        super(new Transform(),new PlaneMesh(),parent);
    }

}
