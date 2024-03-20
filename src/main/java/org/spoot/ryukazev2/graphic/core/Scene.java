package org.spoot.ryukazev2.graphic.core;

import org.spoot.ryukazev2.graphic.manager.EntityManager;
import org.spoot.ryukazev2.graphic.utils.ServiceLocator;

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
