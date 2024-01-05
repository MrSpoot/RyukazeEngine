package ryukazev2.core;

import ryukazev2.manager.EntityManager;
import ryukazev2.utils.ServiceLocator;

public class Scene {

    private final EntityManager entityManager;

    public Scene(){
        this.entityManager = new EntityManager();
    }

    public Scene(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void load(){
        ServiceLocator.registerService(EntityManager.class,this.entityManager);
    }

}
