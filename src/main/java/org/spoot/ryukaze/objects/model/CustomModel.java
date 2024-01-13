package org.spoot.ryukaze.objects.model;

import org.spoot.ryukaze.objects.mesh.CustomMesh;
import org.spoot.ryukaze.core.Transform;
import org.spoot.ryukaze.objects.GameObject;

public class CustomModel extends GameObject {
    public CustomModel(String path) {
        super(new Transform(),new CustomMesh(path),null,null);
    }

    public CustomModel(String path,GameObject parent){
        super(new Transform(),new CustomMesh(path),null,parent);
    }
}
