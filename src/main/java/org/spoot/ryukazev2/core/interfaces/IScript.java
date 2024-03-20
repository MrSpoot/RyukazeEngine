package org.spoot.ryukazev2.core.interfaces;

import org.spoot.ryukazev2.graphic.core.Entity;

public interface IScript {

    void init(Entity entity);
    void update();
    void render();
    void cleanup();

}
