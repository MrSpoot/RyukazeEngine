package org.spoot.ryukaze;

import org.joml.Vector3f;
import org.junit.jupiter.api.Test;
import org.spoot.ryukazev2.core.Time;

public class TestVector {

    @Test
    void testVector(){

        Vector3f force = new Vector3f(0f);
        float mass = 1f;
        Vector3f velocity = new Vector3f(0f,-1f,0f);

        System.out.println("Velocity before recalculation : "+velocity.y);

        force.add(new Vector3f(0,-9.81f,0));

        velocity.add(force.div(mass));

        force.set(0,0,0);
        System.out.println("Velocity after recalculation : "+velocity.y);
        System.out.println("-------------");

    }

}
