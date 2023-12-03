package utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {

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

}
