package org.spoot.ryukazev2.physic.core;

import org.joml.Matrix3f;
import org.joml.Vector3f;
import org.spoot.ryukazev2.physic.component.collider.AABBCollider;
import org.spoot.ryukazev2.physic.component.collider.Collider;
import org.spoot.ryukazev2.physic.component.collider.OBBCollider;
import org.spoot.ryukazev2.physic.component.collider.SphereCollider;

public class CollisionDetector {

    private static final float EPSILON = 0.000001f;

    private CollisionDetector(){}

    public static boolean checkCollision(Collider collider1, Collider collider2){

        //Sphere-Sphere
        if(collider2 instanceof SphereCollider sphere1 && collider1 instanceof SphereCollider sphere2){
            //return checkCollision(sphere1,sphere2);
        }

        //OBB-OBB
        if(collider2 instanceof OBBCollider obb1 && collider1 instanceof OBBCollider obb2){
            return checkCollision(obb1,obb2);
        }

        //Sphere-AABB
        if(collider1 instanceof AABBCollider aabb && collider2 instanceof SphereCollider sphere){
            return checkCollision(sphere,aabb);
        }
        if(collider2 instanceof AABBCollider aabb && collider1 instanceof SphereCollider sphere){
            return checkCollision(sphere,aabb);
        }

        return false;
    }

    //Sphere-Sphere
    private static boolean checkCollision(SphereCollider sphere1, SphereCollider sphere2){
        float distanceSquared = sphere1.getCenter().sub(sphere2.getCenter()).lengthSquared();
        //TODO Change x usage
        float radiusSum = sphere1.getScale().x + sphere2.getScale().x;
        return distanceSquared <= (radiusSum * radiusSum);
    }

    //OBB-OBB
    private static boolean checkCollision(OBBCollider obb1, OBBCollider obb2){

        Matrix3f R = new Matrix3f(); // Rotation from obb1 to obb2
        Matrix3f absR = new Matrix3f(); // Absolute values of R, to use with the separating axis theorem

        // Compute rotation matrix expressing obb2 in obb1's coordinate frame
        Matrix3f rot1 = new Matrix3f().rotation(obb1.getRotation());
        Matrix3f rot2 = new Matrix3f().rotation(obb2.getRotation());
        R.set(rot1.transpose().mul(rot2));

        // Compute translation vector (in obb1's coordinate frame)
        Vector3f t = new Vector3f(obb2.getCenter()).sub(obb1.getCenter());
        rot1.transformTranspose(t);

        // Compute common subexpressions and add in an epsilon term to counteract arithmetic errors
        absR.set(R);
        absR = absolute(absR);

        // Test axes L = A0, L = A1, L = A2 (the three principal axes of obb1)
        for (int i = 0; i < 3; i++) {
            float ra = obb1.getHalfExtents().get(i);
            float rb = obb2.getHalfExtents().dot(absR.getRow(i, new Vector3f()));
            if (Math.abs(t.get(i)) > ra + rb) {
                return false; // Separating axis found
            }
        }

        // Test axes L = B0, L = B1, L = B2 (the three principal axes of obb2)
        for (int i = 0; i < 3; i++) {
            float ra = obb1.getHalfExtents().dot(absR.getColumn(i, new Vector3f()));
            float rb = obb2.getHalfExtents().get(i);
            if (Math.abs(t.dot(R.getColumn(i, new Vector3f()))) > ra + rb) {
                return false; // Separating axis found
            }
        }

        return true; // No separating axis found, there must be an intersection
    }

    //Sphere-AABB
    private static boolean checkCollision(SphereCollider sphere, AABBCollider aabb){
        Vector3f closestPoint = clamp(sphere.getCenter(),aabb.getMin(), aabb.getMax());
        float distanceSquared = closestPoint.sub(sphere.getCenter()).lengthSquared();
        //TODO Change x usage
        return distanceSquared <= (sphere.getScale().x / 2);
    }

    private static Vector3f clamp(Vector3f vector, Vector3f min, Vector3f max){
        vector.x = Math.max(min.x, Math.min(vector.x, max.x));
        vector.y = Math.max(min.y, Math.min(vector.y, max.y));
        vector.z = Math.max(min.z, Math.min(vector.z, max.z));
        return new Vector3f(vector);
    }

    public static Matrix3f absolute(Matrix3f matrix) {
        Matrix3f result = new Matrix3f();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                float value = matrix.get(row, col);
                result.set(row, col, Math.abs(value) + EPSILON);
            }
        }
        return result;
    }
}
