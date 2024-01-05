package ryukazev2.core.interfaces;

import ryukazev2.core.Entity;

public interface IScript {

    void init(Entity entity);
    void update();
    void render();
    void cleanup();

}
