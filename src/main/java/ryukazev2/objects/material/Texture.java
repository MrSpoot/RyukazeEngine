package ryukazev2.objects.material;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukazev2.graphics.Image;
import ryukazev2.utils.FileReader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

@Getter
public class Texture {

    private static final Logger LOGGER = LoggerFactory.getLogger(Texture.class);
    private final int texture;
    private int width;
    private int height;

    public Texture(String path, boolean flip) {

        this.width = 0;
        this.height = 0;

        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);

        Image image = FileReader.readImage(path, flip);

        if(image != null){
            this.width = image.getWidth();
            this.height = image.getHeight();

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image.getByteBuffer());
            glGenerateMipmap(GL_TEXTURE_2D);
        }else{
            LOGGER.warn("Failed to load texture, Image is null");
        }
        glBindTexture(GL_TEXTURE_2D,0);
    }

    public void render(){
        glBindTexture(GL_TEXTURE_2D,texture);
    }

}
