package org.spoot.ryukazev2.graphic.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spoot.ryukazev2.graphic.core.Scene;
import org.spoot.ryukazev2.graphic.utils.ServiceLocator;

import java.util.HashMap;
import java.util.Map;

public class SceneManager extends Manager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SceneManager.class);
    private final Map<String, Scene> scenes;

    public SceneManager(){
        this.scenes = new HashMap<>();
        ServiceLocator.registerService(SceneManager.class,this);
    }

    public Scene get(String scene){
        return this.scenes.get(scene);
    }

    public void save(String name){
        LOGGER.info("\033[0;33m[SCENE]\033[0m Saving scene...");
        Scene scene = new Scene(ServiceLocator.getService(EntityManager.class));
        if(this.scenes.containsKey(name)){
            this.scenes.replace(name,scene);
        }else{
            this.scenes.put(name,scene);
        }
    }

    public void load(String scene){
        if(this.scenes.containsKey(scene)){
            this.scenes.get(scene).load();
        }else{
            LOGGER.warn("\033[0;33m[SCENE]\033[0m This scene was not found");
        }
    }

}
