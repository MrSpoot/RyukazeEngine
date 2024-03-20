package org.spoot.ryukazev2.core.interfaces;

import org.spoot.ryukazev2.graphic.core.UIEntity;

public interface IUIScript {

    void init(UIEntity entity);
    void update();
    void render();
    void cleanup();

}
