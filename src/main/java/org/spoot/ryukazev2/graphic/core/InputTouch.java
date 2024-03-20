package org.spoot.ryukazev2.graphic.core;

import lombok.Data;
import org.spoot.ryukazev2.graphic.manager.InputManager;
import org.spoot.ryukazev2.graphic.utils.ServiceLocator;

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
