package ryukaze.objects.model;

import ryukaze.core.Transform;
import ryukaze.objects.GameObject;
import ryukaze.objects.mesh.CustomMesh;

public class CustomModel extends GameObject {
    public CustomModel(String path) {
        super(new Transform(),new CustomMesh(path),null);
    }

    public CustomModel(String path,GameObject parent){
        super(new Transform(),new CustomMesh(path),parent);
    }
}
