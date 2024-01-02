package SimpleTestV2;

import ryukazev2.Engine;
import ryukazev2.core.Window;

public class Main {

    public static void main(String[] args) {
        Engine engine = new Engine()
                .setWindow(new Window()
                        .setTitle("Test")
                        .setHeight(720)
                        .setWidth(1280)
                        .build())
                .build();

        engine.run();
    }

}
