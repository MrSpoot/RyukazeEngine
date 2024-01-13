package org.spoot.ryukaze.physics;

import org.spoot.ryukaze.physics.collider.Collider;

import java.util.ArrayList;
import java.util.List;

public class PhysicsEngine {

    private List<Collider> colliders;

    public PhysicsEngine() {
        this.colliders = new ArrayList<>();
    }

    public void subscribe(Collider collider){
        this.colliders.add(collider);
    }

    public void unsubscribe(Collider collider){
        this.colliders.remove(collider);
    }

    public void update(){
        this.colliders.forEach((c1) -> {
            this.colliders.forEach((c2) -> {
                if(!c1.getId().equals(c2.getId())){
                    if(c1.collidesWith(c2)){
                        System.out.println("COLLISION");
                    }
                }

            });
        });
    }

}
