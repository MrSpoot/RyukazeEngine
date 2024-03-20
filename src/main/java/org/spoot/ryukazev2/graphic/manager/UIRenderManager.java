package org.spoot.ryukazev2.graphic.manager;

import lombok.Getter;
import org.lwjgl.nanovg.NanoVG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spoot.ryukazev2.graphic.component.ui.UIComponent;
import org.spoot.ryukazev2.graphic.core.UIEntity;
import org.spoot.ryukazev2.graphic.core.Window;
import org.spoot.ryukazev2.manager.Manager;
import org.spoot.ryukazev2.manager.SystemManager;
import org.spoot.ryukazev2.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.nanovg.NanoVGGL3.*;

public class UIRenderManager extends Manager {

    @Getter
    private long vg;
    @Getter
    private List<String> fonts;

    private static final Logger LOGGER = LoggerFactory.getLogger(UIRenderManager.class);

    public UIRenderManager() {
        initNanoVG();
        this.fonts = new ArrayList<>();
        ServiceLocator.registerService(UIRenderManager.class,this);
    }

    public UIRenderManager linkFont(String name, String path){
        NanoVG.nvgCreateFont(vg, name, path);
        return this;
    }

    private void initNanoVG(){
        this.vg = nvgCreate(NVG_ANTIALIAS  | NVG_STENCIL_STROKES);

        if(this.vg == 0){
            LOGGER.error("[UI] Cannot create NanoVG");
        }
    }

    public void render(){
        Window window = ServiceLocator.getService(SystemManager.class).getWindow();

        NanoVG.nvgBeginFrame(this.vg, window.getWidth(), window.getHeight(), 1);

        for(UIEntity entity : ServiceLocator.getService(EntityManager.class).getUiEntities()){
            entity.getComponents().values().forEach((i) -> i.values().forEach(UIComponent::render));
        }

        NanoVG.nvgEndFrame(this.vg);
    }

}
