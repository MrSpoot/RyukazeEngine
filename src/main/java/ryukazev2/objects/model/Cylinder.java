package ryukazev2.objects.model;

import ryukazev2.core.Transform;
import ryukazev2.objects.GameObject;
import ryukazev2.objects.mesh.CylinderMesh;

public class Cylinder extends GameObject {

    public Cylinder() {
        super(new Transform(),new CylinderMesh(15),null);
    }

    public Cylinder(GameObject parent){
        super(new Transform(),new CylinderMesh(15),parent);
    }

}
