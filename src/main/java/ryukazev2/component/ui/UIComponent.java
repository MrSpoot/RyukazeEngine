package ryukazev2.component.ui;

import lombok.Getter;
import ryukazev2.core.UIEntity;
import ryukazev2.core.enumerations.Anchor;
import ryukazev2.manager.UIRenderManager;
import ryukazev2.utils.ServiceLocator;

public abstract class UIComponent {

    @Getter
    private UIEntity entity;
    @Getter
    private String label;
    protected Anchor anchor;
    protected long vg;

    public UIComponent(String label) {
        this.label = label;
        this.anchor = Anchor.TOP_LEFT;
        this.vg = ServiceLocator.getService(UIRenderManager.class).getVg();
    }
    public void linkEntity(UIEntity entity){
        this.entity = entity;
    }

    public abstract void render();

}
