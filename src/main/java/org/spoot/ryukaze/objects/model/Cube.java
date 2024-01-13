package org.spoot.ryukaze.objects.model;

import org.spoot.ryukaze.objects.mesh.CubeMesh;
import org.spoot.ryukaze.core.Transform;
import org.spoot.ryukaze.objects.GameObject;

public class Cube extends GameObject {

    public Cube() {
        super(new Transform(),new CubeMesh(),null,null);
    }

    public Cube(GameObject parent){
        super(new Transform(),new CubeMesh(),null,parent);
    }
}
