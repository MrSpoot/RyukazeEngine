package ryukaze.objects.model;

import ryukaze.core.Transform;
import ryukaze.objects.GameObject;
import ryukaze.objects.mesh.PlaneMesh;

public class Plane extends GameObject {

    public Plane() {
        super(new Transform(),new PlaneMesh(),null,null);
    }

    public Plane(GameObject parent){
        super(new Transform(),new PlaneMesh(),null,parent);
    }

}
