package ryukazev2.manager;

import ryukazev2.component.ScriptComponent;
import ryukazev2.core.Entity;
import ryukazev2.utils.ServiceLocator;

public class ScriptManager extends Manager{

    public ScriptManager() {
        ServiceLocator.registerService(ScriptManager.class,this);
    }

    public void init(){
        for(Entity entity : ((EntityManager) this.services.get(EntityManager.class)).getEntityByComponent(ScriptComponent.class)){
           entity.getComponent(ScriptComponent.class).init();
        }
    }

    public void update(float deltaTime){
        for(Entity entity : ((EntityManager) this.services.get(EntityManager.class)).getEntityByComponent(ScriptComponent.class)){
            entity.getComponent(ScriptComponent.class).update(deltaTime);
        }
    }

    public void render(){
        for(Entity entity : ((EntityManager) this.services.get(EntityManager.class)).getEntityByComponent(ScriptComponent.class)){
            entity.getComponent(ScriptComponent.class).render();
        }
    }

}
