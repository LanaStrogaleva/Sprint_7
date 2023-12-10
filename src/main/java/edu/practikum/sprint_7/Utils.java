package edu.practikum.sprint_7;

import java.util.Random;

public class Utils {
    public static String randomString(int length) {
        Random random = new Random();
        int leftLimit = 97; // буква 'a'
        int rightLimit = 122; // буква 'z'
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i< length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat()* (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }
}
