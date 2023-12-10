import ryukaze.core.Window;
import ryukaze.scene.Scene;
import ryukaze.scene.model.Cube;

public class SimpleApplication {

    public static void main(String[] args) {

        Window window = new Window("Simple Application", new Window.WindowOptions());

        Scene scene = new Scene();
        scene.addModelToScene(new Cube());
        window.getSceneManager().setActualScene(scene);

        window.run();

    }

}
