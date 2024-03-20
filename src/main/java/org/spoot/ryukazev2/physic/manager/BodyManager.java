package org.spoot.ryukazev2.physic.manager;

import lombok.Getter;
import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.graphic.core.UIEntity;
import org.spoot.ryukazev2.manager.Manager;
import org.spoot.ryukazev2.physic.component.body.Body;
import org.spoot.ryukazev2.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BodyManager extends Manager {

    private final List<Body> bodies;

    public BodyManager() {
        this.bodies = new ArrayList<>();
        ServiceLocator.registerService(BodyManager.class, this);
    }

    public void subscribe(Body body){
        this.bodies.add(body);
    }

    public void unsubscribe(Body body){
        this.bodies.remove(body);
    }
}
