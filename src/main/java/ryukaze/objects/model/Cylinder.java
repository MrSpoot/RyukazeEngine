package ryukaze.objects.model;

import ryukaze.core.Transform;
import ryukaze.objects.GameObject;
import ryukaze.objects.mesh.CylinderMesh;

public class Cylinder extends GameObject {

    public Cylinder() {
        super(new Transform(),new CylinderMesh(15),null,null);
    }

    public Cylinder(GameObject parent){
        super(new Transform(),new CylinderMesh(15),null,parent);
    }

}
