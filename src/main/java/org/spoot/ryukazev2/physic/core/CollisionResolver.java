package org.spoot.ryukazev2.physic.core;

import org.joml.Vector3f;
import org.spoot.ryukazev2.component.TransformComponent;
import org.spoot.ryukazev2.physic.component.body.Rigidbody;
import org.spoot.ryukazev2.physic.component.collider.AABBCollider;
import org.spoot.ryukazev2.physic.component.collider.Collider;
import org.spoot.ryukazev2.physic.component.collider.OBBCollider;
import org.spoot.ryukazev2.physic.component.collider.SphereCollider;

public class CollisionResolver {

    private CollisionResolver(){}

    public static void resolveCollision(Rigidbody rigidbody){
        rigidbody.getVelocity().mul(-0.9f);
    }

    public static void resolveCollision(Collider collider1, Collider collider2){

        //Sphere-Sphere
        if(collider2 instanceof SphereCollider sphere1 && collider1 instanceof SphereCollider sphere2){
            resolveCollision(sphere1,sphere2);
        }

        //OBB-OBB
        if(collider2 instanceof OBBCollider obb1 && collider1 instanceof OBBCollider obb2){

        }

        //Sphere-AABB
        if(collider1 instanceof AABBCollider aabb && collider2 instanceof SphereCollider sphere){
            resolveCollision(sphere,aabb);
        }
        if(collider2 instanceof AABBCollider aabb && collider1 instanceof SphereCollider sphere){
            resolveCollision(sphere,aabb);
        }

    }

    private static void resolveCollision(SphereCollider sphereCollider1,SphereCollider sphereCollider2){

        Rigidbody sphere1 = sphereCollider1.getEntity().getComponent(Rigidbody.class);
        Rigidbody sphere2 = sphereCollider2.getEntity().getComponent(Rigidbody.class);

        Vector3f normal = new Vector3f(sphereCollider1.getCenter()).sub(sphereCollider2.getCenter()).normalize();

        resolveElasticCollision(sphere1,sphere2,normal);

        /*float restitution = 1f;
        Vector3f v_old1 = new Vector3f(sphere1.getVelocity());
        Vector3f n1 = new Vector3f(sphereCollider1.getCenter()).sub(sphereCollider2.getCenter()).normalize();
        Vector3f v_new1 = v_old1.sub(n1.mul(v_old1.dot(n1) * (1 + restitution)));

        Vector3f v_old2 = new Vector3f(sphere2.getVelocity());
        Vector3f n2 = new Vector3f(sphereCollider2.getCenter()).sub(sphereCollider1.getCenter()).normalize();
        Vector3f v_new2 = v_old2.sub(n2.mul(v_old2.dot(n2) * (1 + restitution)));

        float penetrationDepth = sphereCollider1.getRadius() - sphereCollider1.getCenter().distance(sphereCollider2.getCenter());
        Vector3f correctionDirection = new Vector3f(sphereCollider1.getCenter()).sub(sphereCollider2.getCenter()).normalize();
        Vector3f correction = correctionDirection.mul(penetrationDepth);

        sphere1.getVelocity().set(v_new1);
        sphere2.getVelocity().set(v_new2);*/
    }

    private static void resolveCollision(SphereCollider sphereCollider, AABBCollider aabbCollider){

        Rigidbody sphere = sphereCollider.getEntity().getComponent(Rigidbody.class);
        Rigidbody aabb = aabbCollider.getEntity().getComponent(Rigidbody.class);

        Vector3f closestPoint = new Vector3f(Math.max(aabbCollider.getMin().x, Math.min(sphereCollider.getCenter().x, aabbCollider.getMax().x)),
                Math.max(aabbCollider.getMin().y, Math.min(sphereCollider.getCenter().y, aabbCollider.getMax().y)),
                Math.max(aabbCollider.getMin().z, Math.min(sphereCollider.getCenter().z, aabbCollider.getMax().z)));

        Vector3f normal = sphereCollider.getCenter().sub(closestPoint).normalize();

        resolveElasticCollision(sphere,aabb,normal);

        /*Vector3f closestPoint = new Vector3f(Math.max(aabbCollider.getMin().x, Math.min(sphereCollider.getCenter().x, aabbCollider.getMax().x)),
                Math.max(aabbCollider.getMin().y, Math.min(sphereCollider.getCenter().y, aabbCollider.getMax().y)),
                Math.max(aabbCollider.getMin().z, Math.min(sphereCollider.getCenter().z, aabbCollider.getMax().z)));

        float penetrationDepth = sphereCollider.getRadius() - sphereCollider.getCenter().distance(closestPoint);
        Vector3f correctionDirection = new Vector3f(sphereCollider.getCenter()).sub(closestPoint).normalize();
        Vector3f correction = correctionDirection.mul(penetrationDepth);

        float restitution = 1f;
        Vector3f v_old = new Vector3f(sphere.getVelocity());
        Vector3f n = sphereCollider.getCenter().sub(closestPoint).normalize();
        Vector3f v_new = v_old.sub(n.mul(v_old.dot(n) * (1 + restitution)));

        sphereCollider.getEntity().getComponent(TransformComponent.class).getPosition().add(correction);

        sphere.getVelocity().set(v_new);*/
    }

    public static void resolveElasticCollision(Rigidbody body1, Rigidbody body2, Vector3f normal) {
        if(body1 != null && body2 != null){
            float m1 = body1.getMass();
            float m2 = body2.getMass();

            Vector3f v1 = body1.getVelocity();
            Vector3f v2 = body2.getVelocity();

            Vector3f v1Final = new Vector3f(
                    ((m1 - m2) / (m1 + m2)) * v1.x + ((2 * m2) / (m1 + m2)) * v2.x,
                    ((m1 - m2) / (m1 + m2)) * v1.y + ((2 * m2) / (m1 + m2)) * v2.y,
                    ((m1 - m2) / (m1 + m2)) * v1.z + ((2 * m2) / (m1 + m2)) * v2.z);

            Vector3f v2Final = new Vector3f(
                    ((m2 - m1) / (m1 + m2)) * v2.x + ((2 * m1) / (m1 + m2)) * v1.x,
                    ((m2 - m1) / (m1 + m2)) * v2.y + ((2 * m1) / (m1 + m2)) * v1.y,
                    ((m2 - m1) / (m1 + m2)) * v2.z + ((2 * m1) / (m1 + m2)) * v1.z);

//            v1Final.sub(new Vector3f(normal).mul(v1Final.dot(normal) * (1 + 1)));
//            v2Final.sub(new Vector3f(normal).mul(v2Final.dot(normal) * (1 + 1)));

            body1.getVelocity().set(v1Final.mul(0.8f));
            body2.getVelocity().set(v2Final.mul(0.8f));
        }else if(body1 != null){
            Vector3f v1 = body1.getVelocity();
            v1.sub(new Vector3f(normal).mul(v1.dot(normal) * (1 + 1)));
            body1.setVelocity(v1);
        }else if(body2 != null){
            Vector3f v2 = body2.getVelocity();
            v2.sub(new Vector3f(normal).mul(-1).mul(v2.dot(new Vector3f(normal).mul(-1)) * (1 + 1)));
            body2.setVelocity(v2);
        }


    }
}
