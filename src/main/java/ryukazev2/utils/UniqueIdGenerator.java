package ryukazev2.utils;

import java.util.Random;

public class UniqueIdGenerator {

    private static final String ALPHANUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random random = new Random();

    public static String generateUniqueID(int randomPartLength) {
        long timestamp = System.currentTimeMillis();
        StringBuilder idBuilder = new StringBuilder();
        idBuilder.append(timestamp);
        for (int i = 0; i < randomPartLength; i++) {
            int index = random.nextInt(ALPHANUMERIC_CHARS.length());
            char randomChar = ALPHANUMERIC_CHARS.charAt(index);
            idBuilder.append(randomChar);
        }

        return idBuilder.toString();
    }
}
