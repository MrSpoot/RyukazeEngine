package org.spoot.ryukazev2.graphic.component.ui;

import org.joml.Vector2f;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import java.awt.*;

public class UITextComponent extends UIComponent {

    private String text;
    private float size;
    private Color color;
    private String font;
    private Vector2f position;

    public UITextComponent(String label) {
        super(label);
        this.text = "";
        this.size = 15f;
        this.color = Color.white;
        this.font = "Arial";
        this.position = new Vector2f(0, 0);
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

    public UITextComponent build() {
        return this;
    }

    @Override
    public void render() {
        try (NVGColor nvColor = NVGColor.calloc()) {
            NanoVG.nvgRGBA((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(), (byte) color.getAlpha(), nvColor);

            NanoVG.nvgFontSize(this.vg, size);
            NanoVG.nvgFontFace(this.vg, font);
            NanoVG.nvgTextAlign(this.vg, NanoVG.NVG_ALIGN_LEFT | NanoVG.NVG_ALIGN_MIDDLE);
            NanoVG.nvgFillColor(this.vg, nvColor);
            NanoVG.nvgText(this.vg, position.x, position.y, text);
        }
    }


}
