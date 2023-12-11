package ryukazev2.objects.model;

import ryukazev2.objects.GameObject;
import ryukazev2.objects.mesh.CubeMesh;

import java.util.HashMap;

public class Cube extends GameObject {

    public Cube() {
        super(new HashMap<>(), new CubeMesh());
    }
}
