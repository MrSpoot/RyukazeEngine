package engine;

import engine.scene.Scene;

public interface IGuiInstance {

    void drawGui();

    boolean handleGuiInput(Scene scene, Window window);

}
