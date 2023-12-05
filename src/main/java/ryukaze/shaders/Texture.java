package ryukaze.shaders;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

public class Texture {

    private final int texture;
    private int width;
    private int height;

    public Texture(String path, boolean flip) {
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);

        ByteBuffer image = readImage(path, flip);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE,image);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public ByteBuffer readImage(String path, boolean flip){
        stbi_set_flip_vertically_on_load(flip);
        IntBuffer x = BufferUtils.createIntBuffer(1);
        IntBuffer y = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer image = stbi_load(path, x, y, channels, STBI_rgb_alpha);
        if (image == null) {
            System.err.println("Could not decode image file ["+ path +"]: ["+ STBImage.stbi_failure_reason() +"]");
        }
        width = x.get();
        height = y.get();
        stbi_set_flip_vertically_on_load(false);
        return image;
    }

    public int getTexture(){
        return texture;
    }
}
