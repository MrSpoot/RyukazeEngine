package ryukazev2.core;

public interface IScript {

    void init(Entity entity);
    void update(float deltaTime);
    void render();
    void cleanup();

}
