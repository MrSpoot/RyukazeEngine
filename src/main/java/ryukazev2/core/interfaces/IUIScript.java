package ryukazev2.core.interfaces;

import ryukazev2.core.UIEntity;

public interface IUIScript {

    void init(UIEntity entity);
    void update();
    void render();
    void cleanup();

}
