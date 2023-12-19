package ryukaze.objects.model;

import ryukaze.core.Transform;
import ryukaze.objects.GameObject;
import ryukaze.objects.mesh.CubeMesh;

public class Cube extends GameObject {

    public Cube() {
        super(new Transform(),new CubeMesh(),null);
    }

    public Cube(GameObject parent){
        super(new Transform(),new CubeMesh(),parent);
    }
}
