package org.spoot.ryukazev2.graphic.component.ui;

import lombok.Getter;
import org.spoot.ryukazev2.graphic.core.UIEntity;
import org.spoot.ryukazev2.graphic.core.enumerations.Anchor;
import org.spoot.ryukazev2.graphic.manager.UIRenderManager;
import org.spoot.ryukazev2.utils.ServiceLocator;

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
