package ryukaze.input;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class InputTouch {

    private String name;
    private int action;
    private int touch;

    public InputTouch(String name, int action, int touch) {
        this.name = name;
        this.action = action;
        this.touch = touch;
    }
}
