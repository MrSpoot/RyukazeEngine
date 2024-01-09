package ryukazev2.component.ui;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.nanovg.NVGPaint;
import ryukaze.graphics.Image;
import ryukazev2.core.enumerations.Anchor;

import java.nio.IntBuffer;

import static org.lwjgl.nanovg.NanoVG.*;

public class UIRectComponent extends UIComponent{

    private Vector2f position;
    private Vector2f scale;

    private Image image;

    public UIRectComponent(String label) {
        super(label);
        this.position = new Vector2f(0f);
        this.scale = new Vector2f(1f);
        this.image = new Image(new Vector4f(1f));
    }

    public UIRectComponent setPosition(Vector2f position) {
        this.position = position;
        return this;
    }

    public UIRectComponent setscale(Vector2f scale) {
        this.scale = scale;
        return this;
    }

    public UIRectComponent setImage(Image image) {
        this.image = image;
        return this;
    }

    public UIRectComponent setAnchor(Anchor anchor) {
        this.anchor = anchor;
        return this;
    }

    public UIRectComponent build(){
        return this;
    }

    @Override
    public void render() {

        float width = image.getWidth() * scale.x;
        float height = image.getHeight() * scale.y;

        float posX = position.x - this.anchor.getXOffset(width);
        float posY = position.y - this.anchor.getYOffset(height);

        try (NVGPaint nvPaint = NVGPaint.calloc()) {
            int i = nvgCreateImageRGBA(this.vg,image.getWidth(),image.getHeight(),0,image.getByteBuffer());
            nvgRect(this.vg, posX, posY, width, height);
            nvgImagePattern(this.vg, posX, posY, width, height, 0, i, 1.0f,nvPaint);
            nvgFillPaint(this.vg,nvPaint);
            nvgFill(vg);

        }

    }
}
