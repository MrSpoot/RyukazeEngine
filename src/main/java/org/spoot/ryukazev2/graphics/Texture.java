package org.spoot.ryukazev2.graphics;

import lombok.Getter;
import org.joml.Vector4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spoot.ryukaze.utils.FileReader;
import org.spoot.ryukaze.graphics.Image;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

@Getter
public class Texture {

    private static final Logger LOGGER = LoggerFactory.getLogger(Texture.class);
    private int texture;
    private int width;
    private int height;
    private boolean hasTransparency;

    public Texture(int textureId){
        this.texture = textureId;
        this.width = 1280;
        this.height = 720;
        this.hasTransparency = false;
    }

    public Texture(Vector4f color) {
        Image image = new Image(color);
        loadTexture(image);
    }

    public Texture(String path ,boolean flip) {
        Image image = FileReader.readImage(path, flip);
        loadTexture(image);
    }

    private void loadTexture(Image image) {
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);

        if (image != null) {

            this.hasTransparency = hasTransparency(image.getByteBuffer());

            this.width = image.getWidth();
            this.height = image.getHeight();

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image.getByteBuffer());
            glGenerateMipmap(GL_TEXTURE_2D);
        } else {
            LOGGER.warn("Failed to load texture, Image is null");
        }
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private boolean hasTransparency(ByteBuffer imageData) {
        int originalPosition = imageData.position();

        while (imageData.hasRemaining()) {
            imageData.position(imageData.position() + 3);

            if (imageData.hasRemaining()) {
                byte alpha = imageData.get();
                if ((alpha & 0xFF) < 255) {
                    imageData.position(originalPosition);
                    return true;
                }
            }
        }

        imageData.position(originalPosition);
        return false;
    }
}
