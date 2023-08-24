package engine;

import java.io.IOException;

public class Utils {

    public static String readFile(String filePath) {
        String str;
        try {
            str = new String(Utils.class.getClassLoader().getResourceAsStream(filePath).readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Error reading file [" + filePath + "]", e);
        }
        return str;
    }
}
