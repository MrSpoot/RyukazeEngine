package ryukazev2.component;

import lombok.Getter;
import ryukazev2.core.UIEntity;

public abstract class UIComponent {

    @Getter
    private UIEntity entity;

    public abstract void render();

    public void linkEntity(UIEntity entity){
        this.entity = entity;
    }
}
