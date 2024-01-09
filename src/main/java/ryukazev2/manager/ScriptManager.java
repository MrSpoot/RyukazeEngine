package ryukazev2.manager;

import ryukazev2.component.game.ScriptComponent;
import ryukazev2.component.ui.UIScriptComponent;
import ryukazev2.core.Entity;
import ryukazev2.core.UIEntity;
import ryukazev2.utils.ServiceLocator;

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
