package ryukazev2.input;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "deltaTime")
public class InputTouch {

    private String name;
    private int action;
    private int touch;
    private float deltaTime;

    public InputTouch(String name, int action, int touch) {
        this.name = name;
        this.action = action;
        this.touch = touch;
        deltaTime = 0.0f;
    }
}
