package ryukazev2.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ryukazev2.manager.InputManager;
import ryukazev2.utils.ServiceLocator;

@Data
public class InputTouch {

    private String name;
    private int touch;

    public InputTouch(String name, int touch) {
        this.name = name;
        this.touch = touch;
        ServiceLocator.getService(InputManager.class).linkNewTouch(this);
    }
}
