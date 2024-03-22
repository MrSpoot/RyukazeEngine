package org.spoot.ryukazev2.manager;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spoot.ryukazev2.graphic.GraphicsEngine;
import org.spoot.ryukazev2.core.Loop;
import org.spoot.ryukazev2.graphic.core.Window;
import org.spoot.ryukazev2.graphic.core.Input;
import org.spoot.ryukazev2.graphic.manager.InputManager;
import org.spoot.ryukazev2.graphic.manager.RenderManager;
import org.spoot.ryukazev2.graphic.manager.UIRenderManager;
import org.spoot.ryukazev2.physic.manager.BodyManager;
import org.spoot.ryukazev2.physic.manager.ColliderManager;
import org.spoot.ryukazev2.utils.ServiceLocator;

@EqualsAndHashCode(callSuper = true)
@Data
public class SystemManager extends Manager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphicsEngine.class);

    private Window window;
    private Loop loop;
    private float lastUpdateTime;

    public SystemManager() {
        this.window = new Window().build();
        this.loop = new Loop().build();
        this.lastUpdateTime = 0f;
        ServiceLocator.registerService(SystemManager.class,this);
    }

    public SystemManager setWindow(Window window){
        this.window = window;
        return this;
    }

    public void render(){
        if(this.window.shouldClose() || ((InputManager) this.services.get(InputManager.class)).isPressed("exit_engine")){
            stop();
        }

        ((InputManager) this.services.get(InputManager.class)).process();
        ((ScriptManager) this.services.get(ScriptManager.class)).render();

        this.window.preRender();

        ((RenderManager) this.services.get(RenderManager.class)).render();
        ((UIRenderManager) this.services.get(UIRenderManager.class)).render();
        this.window.postRender();
    }

    public void update(){

        ((BodyManager) this.services.get(BodyManager.class)).processVelocity();
        ((ColliderManager) this.services.get(ColliderManager.class)).processCollision();
        ((BodyManager) this.services.get(BodyManager.class)).applyVelocity();
        ((ScriptManager) this.services.get(ScriptManager.class)).update();
    }

    public void initManger(){
        LOGGER.info("\033[1;32m[INITIALIZE]\u001B[0m Engine managers");

        ((ScriptManager) this.services.get(ScriptManager.class)).init();
    }

    public void initStatics() {
        Input.init();
    }

    public void run(){
        initManger();
        initStatics();
        LOGGER.info("\033[1;32m[RUN]\u001B[0m Engine instance");
        this.loop.run();
    }

    public void stop(){
        LOGGER.info("\033[1;32m[STOP]\u001B[0m Engine instance");
        this.loop.stop();
    }
}
