package ryukazev2.manager;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukazev2.core.Window;
import ryukazev2.utils.ServiceLocator;
import static org.lwjgl.nanovg.NanoVGGL3.NVG_STENCIL_STROKES;
import static org.lwjgl.nanovg.NanoVGGL3.nvgCreate;

public class UIManager extends Manager{

    private final long vg;

    private static final Logger LOGGER = LoggerFactory.getLogger(UIManager.class);

    public UIManager() {
        this.vg = nvgCreate(NVG_STENCIL_STROKES);

        if(this.vg == 0){
            LOGGER.error("[NANOVG] Cannot create NanoVG");
        }

        NanoVG.nvgCreateFont(vg, "retro", "src/main/resources/fonts/retro_gaming.ttf");


        ServiceLocator.registerService(UIManager.class,this);
    }

    public void render(){

        Window window = ServiceLocator.getService(SystemManager.class).getWindow();

        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Couleur et autres propriétés
            NVGColor color = NVGColor.mallocStack(stack);
            NanoVG.nvgRGBA((byte) 255, (byte) 255, (byte) 255, (byte) 255, color);

            NVGColor color2 = NVGColor.mallocStack(stack);
            NanoVG.nvgRGBA((byte) 0, (byte) 0, (byte) 0, (byte) 255, color2);

            // Commencer le dessin
            NanoVG.nvgBeginFrame(vg, window.getWidth(), window.getHeight(), 1);

            // Dessiner du texte
            NanoVG.nvgFontSize(vg, 18.0f);
            NanoVG.nvgFontFace(vg, "retro");
            NanoVG.nvgTextAlign(vg, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE);
            NanoVG.nvgFillColor(vg, color2);
            NanoVG.nvgText(vg, 5, 23, "FPS : "+ServiceLocator.getService(SystemManager.class).getLoop().getFps());

            NanoVG.nvgFontSize(vg, 18.0f);
            NanoVG.nvgFontFace(vg, "retro");
            NanoVG.nvgTextAlign(vg, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE);
            NanoVG.nvgFillColor(vg, color2);
            NanoVG.nvgText(vg, 5, 46, "UPS : "+ServiceLocator.getService(SystemManager.class).getLoop().getUps());

            NanoVG.nvgBeginPath(this.vg);
            NanoVG.nvgCircle(this.vg, (float) window.getWidth() /2, (float) window.getHeight() /2,2);
            NanoVG.nvgFillColor(vg, color2);
            NanoVG.nvgFill(this.vg);
            // Terminer le dessin
            NanoVG.nvgEndFrame(vg);

            // Gestion des événements, buffer swapping, etc.
        }
    }

}
