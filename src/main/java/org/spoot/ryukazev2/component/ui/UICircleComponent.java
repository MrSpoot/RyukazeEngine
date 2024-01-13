package org.spoot.ryukazev2.component.ui;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.nanovg.NVGPaint;
import org.spoot.ryukaze.graphics.Image;
import org.spoot.ryukazev2.core.enumerations.Anchor;

import static org.lwjgl.nanovg.NanoVG.*;

public class UICircleComponent extends UIComponent{

    private Vector2f position;
    private Vector2f scale;

    private Image image;

    public UICircleComponent(String label) {
        super(label);
        this.position = new Vector2f(0f);
        this.scale = new Vector2f(1f);
        this.image = new Image(new Vector4f(1f));
    }

    public UICircleComponent setPosition(Vector2f position) {
        this.position = position;
        return this;
    }

    public UICircleComponent setscale(Vector2f scale) {
        this.scale = scale;
        return this;
    }

    public UICircleComponent setImage(Image image) {
        this.image = image;
        return this;
    }

    public UICircleComponent setAnchor(Anchor anchor) {
        this.anchor = anchor;
        return this;
    }

    public UICircleComponent build(){
        return this;
    }

    @Override
    public void render() {

        float width = image.getWidth() * scale.x;
        float height = image.getHeight() * scale.y;

        float posX = position.x - (width / 2 - this.anchor.getXOffset(width));
        float posY = position.y - (height / 2 - this.anchor.getYOffset(height));

        try (NVGPaint nvPaint = NVGPaint.calloc()) {
            int i = nvgCreateImageRGBA(this.vg,image.getWidth(),image.getHeight(),0,image.getByteBuffer());
            nvgEllipse(this.vg, posX, posY, width / 2, height / 2);
            nvgImagePattern(this.vg, posX - width / 2, posY - height / 2, width, height, 0, i, 1.0f,nvPaint);
            nvgFillPaint(this.vg,nvPaint);
            nvgFill(vg);

        }

    }

}
