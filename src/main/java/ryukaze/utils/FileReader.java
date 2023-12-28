package ryukaze.utils;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukaze.graphics.Image;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

public class FileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileReader.class);

    public static String readFile(String path){
        String content = "";
        try {
            // Lecture du contenu du fichier en utilisant la classe Files
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            content = new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static Image readImage(String path, boolean flip) {
            stbi_set_flip_vertically_on_load(flip);
            IntBuffer x = BufferUtils.createIntBuffer(1);
            IntBuffer y = BufferUtils.createIntBuffer(1);
            IntBuffer channels = BufferUtils.createIntBuffer(1);
            ByteBuffer image = stbi_load(path, x, y, channels, STBI_rgb_alpha);
            if (image == null) {
                LOGGER.warn("Could not decode image file ["+ path +"]: ["+ STBImage.stbi_failure_reason() +"]");
                return null;
            }
            int width = x.get();
            int height = y.get();
            stbi_set_flip_vertically_on_load(false);
            return new Image(image,width,height);
    }

}
