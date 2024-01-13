package org.spoot.ryukaze.objects.model;

import org.spoot.ryukaze.objects.mesh.SphereMesh;
import org.spoot.ryukaze.core.Transform;
import org.spoot.ryukaze.objects.GameObject;

public class Sphere extends GameObject {

    public Sphere() {
        super(new Transform(),new SphereMesh(25),null,null);
    }

    public Sphere(GameObject parent){
        super(new Transform(),new SphereMesh(25),null,parent);
    }
}
