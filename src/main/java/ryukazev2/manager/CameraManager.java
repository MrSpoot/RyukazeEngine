package ryukazev2.manager;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import ryukazev2.component.game.CameraComponent;
import ryukazev2.component.game.TransformComponent;
import ryukazev2.core.Entity;
import ryukazev2.core.Window;
import ryukazev2.utils.ServiceLocator;

import java.util.Optional;

public class CameraManager extends Manager {

    public CameraManager(){
        ServiceLocator.registerService(CameraManager.class,this);
    }

    public Entity getActiveCamera(){
        Optional<Entity> entity = ServiceLocator.getService(EntityManager.class).getEntityByComponent(TransformComponent.class,CameraComponent.class).stream().findFirst();
        return entity.get();
    }

    public boolean checkCameraExisting(){
        Optional<Entity> entity = ServiceLocator.getService(EntityManager.class).getEntityByComponent(TransformComponent.class,CameraComponent.class).stream().findFirst();
        return entity.isPresent();
    }

    public Matrix4f getViewMatrix(){
        Optional<Entity> entity = ServiceLocator.getService(EntityManager.class).getEntityByComponent(TransformComponent.class,CameraComponent.class).stream().findFirst();
        if(entity.isPresent()){
            TransformComponent transform = entity.get().getComponent(TransformComponent.class);
            CameraComponent camera = entity.get().getComponent(CameraComponent.class);

            return new Matrix4f().lookAt(transform.getPosition(),camera.getTarget(),new Vector3f(0f,1f,0f));
        }
        return new Matrix4f();
    }

    public Matrix4f getProjectionMatrix(){
        Optional<Entity> entity = ServiceLocator.getService(EntityManager.class).getEntityByComponent(TransformComponent.class,CameraComponent.class).stream().findFirst();
        if(entity.isPresent()){
            CameraComponent component = entity.get().getComponent(CameraComponent.class);
            Window window = ServiceLocator.getService(SystemManager.class).getWindow();

            Matrix4f projection = new Matrix4f().perspective(component.getFov(),(float) window.getWidth() / (float)window.getHeight(),component.getZNear(),component.getZFar());
            projection.m11(projection.m11() * -1); //Invert Y axis
            return projection;
        }
        return new Matrix4f();
    }

}
