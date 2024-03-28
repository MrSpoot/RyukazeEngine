package org.spoot.ryukazev2.physic.component.collider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.joml.Vector3f;

@Getter
@AllArgsConstructor
public class OBBCollider extends Collider{

    private Vector3f halfExtents;

}
