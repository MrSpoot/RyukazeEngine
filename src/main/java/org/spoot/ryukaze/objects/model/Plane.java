package org.spoot.ryukaze.objects.model;

import org.spoot.ryukaze.objects.mesh.PlaneMesh;
import org.spoot.ryukaze.core.Transform;
import org.spoot.ryukaze.objects.GameObject;

public class Plane extends GameObject {

    public Plane() {
        super(new Transform(),new PlaneMesh(),null,null);
    }

    public Plane(GameObject parent){
        super(new Transform(),new PlaneMesh(),null,parent);
    }

}
