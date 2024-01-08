package ryukazev2.component;

import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryStack;
import ryukazev2.manager.UIManager;
import ryukazev2.utils.ServiceLocator;

import java.awt.*;

public class UITextComponent extends UIComponent{

    private String text;
    private float size;
    private Color color;
    private String font;
    private Vector2f position;

    public UITextComponent() {
        this.text = "";
        this.size = 15f;
        this.color = Color.white;
        this.font = "Arial";
        this.position = new Vector2f(0,0);
    }

    public UITextComponent setText(String text) {
        this.text = text;
        return this;
    }

    public UITextComponent setSize(float size) {
        this.size = size;
        return this;
    }

    public UITextComponent setColor(Color color) {
        this.color = color;
        return this;
    }

    public UITextComponent setFont(String font) {
        this.font = font;
        return this;
    }

    public UITextComponent setPosition(Vector2f position) {
        this.position = position;
        return this;
    }

    public UITextComponent build(){
        return this;
    }

    @Override
    public void render() {

        long vg = ServiceLocator.getService(UIManager.class).getVg();

        try (MemoryStack stack = MemoryStack.stackPush()) {

            NVGColor nvColor = NVGColor.mallocStack(stack);
            NanoVG.nvgRGBA((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(), (byte) color.getAlpha(), nvColor);

            NanoVG.nvgFontSize(vg, size);
            NanoVG.nvgFontFace(vg, font);
            NanoVG.nvgTextAlign(vg, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE);
            NanoVG.nvgFillColor(vg, nvColor);
            NanoVG.nvgText(vg, position.x, position.y, text);
        }


    }
}
