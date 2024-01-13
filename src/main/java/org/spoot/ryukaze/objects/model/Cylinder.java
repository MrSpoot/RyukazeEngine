package org.spoot.ryukaze.objects.model;

import org.spoot.ryukaze.objects.mesh.CylinderMesh;
import org.spoot.ryukaze.core.Transform;
import org.spoot.ryukaze.objects.GameObject;

public class Cylinder extends GameObject {

    public Cylinder() {
        super(new Transform(),new CylinderMesh(15),null,null);
    }

    public Cylinder(GameObject parent){
        super(new Transform(),new CylinderMesh(15),null,parent);
    }

}
