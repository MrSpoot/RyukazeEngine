package ryukazev2.objects.model;

import ryukazev2.core.Transform;
import ryukazev2.objects.GameObject;
import ryukazev2.objects.mesh.CustomMesh;

public class CustomModel extends GameObject {
    public CustomModel(String path) {
        super(new Transform(),new CustomMesh(path),null);
    }

    public CustomModel(String path,GameObject parent){
        super(new Transform(),new CustomMesh(path),parent);
    }
}
