package ryukaze.objects.material;

import lombok.Getter;
import org.joml.Vector4f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukaze.graphics.Image;
import ryukaze.utils.FileReader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

@Getter
public class Texture {

    private static final Logger LOGGER = LoggerFactory.getLogger(Texture.class);
    private int texture;
    private int width;
    private int height;

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
}
