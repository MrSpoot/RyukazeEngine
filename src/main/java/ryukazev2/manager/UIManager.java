package ryukazev2.manager;

import lombok.Getter;
import org.lwjgl.nanovg.NanoVG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukazev2.component.UIComponent;
import ryukazev2.core.UIEntity;
import ryukazev2.core.Window;
import ryukazev2.utils.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;

public class UIManager extends Manager{

    @Getter
    private long vg;
    @Getter
    private List<UIEntity> uiEntities;
    private List<String> fonts;

    private static final Logger LOGGER = LoggerFactory.getLogger(UIManager.class);

    public UIManager() {
        initNanoVG();
        this.uiEntities = new ArrayList<>();
        this.fonts = new ArrayList<>();
        ServiceLocator.registerService(UIManager.class,this);
    }

    public UIManager linkFont(String name, String path){
        NanoVG.nvgCreateFont(vg, name, path);
        return this;
    }

    public void subscribe(UIEntity entity){
        this.uiEntities.add(entity);
    }

    public void unsubscribe(UIEntity entity){
        this.uiEntities.remove(entity);
    }

    private void initNanoVG(){
        this.vg = nvgCreate(NVG_STENCIL_STROKES);

        if(this.vg == 0){
            LOGGER.error("[UI] Cannot create NanoVG");
        }
    }

}
