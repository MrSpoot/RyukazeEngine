package org.spoot.ryukazev2.graphic.manager;

import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.graphic.core.UIEntity;
import org.spoot.ryukazev2.graphic.component.game.ScriptComponent;
import org.spoot.ryukazev2.graphic.component.ui.UIScriptComponent;
import org.spoot.ryukazev2.graphic.utils.ServiceLocator;

public class ScriptManager extends Manager{

    public ScriptManager() {
        ServiceLocator.registerService(ScriptManager.class,this);
    }

    public void init(){
        for(Entity entity : ((EntityManager) this.services.get(EntityManager.class)).getEntityByComponent(ScriptComponent.class)){
           entity.getComponent(ScriptComponent.class).init();
        }
        for(UIEntity entity : ((EntityManager) this.services.get(EntityManager.class)).getUIEntityByComponent(UIScriptComponent.class)){
            entity.getComponentsByClass(UIScriptComponent.class).forEach(UIScriptComponent::init);
        }
    }

    public void update(){
        for(Entity entity : ((EntityManager) this.services.get(EntityManager.class)).getEntityByComponent(ScriptComponent.class)){
            entity.getComponent(ScriptComponent.class).update();
        }
        for(UIEntity entity : ((EntityManager) this.services.get(EntityManager.class)).getUIEntityByComponent(UIScriptComponent.class)){
            entity.getComponentsByClass(UIScriptComponent.class).forEach(UIScriptComponent::update);
        }
    }

    public void render(){
        for(Entity entity : ((EntityManager) this.services.get(EntityManager.class)).getEntityByComponent(ScriptComponent.class)){
            entity.getComponent(ScriptComponent.class).render();
        }
        for(UIEntity entity : ((EntityManager) this.services.get(EntityManager.class)).getUIEntityByComponent(UIScriptComponent.class)){
            entity.getComponentsByClass(UIScriptComponent.class).forEach(UIScriptComponent::render);
        }
    }

}
