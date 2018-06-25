package com.jukusoft.rts.core.utils;

import java.util.Random;

public class RandomUtils {

    private static Random random = new Random();

    private RandomUtils() {
        //
    }

    public static int getRandomNumber(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static boolean rollTheDice(int x) {
        return getRandomNumber(1, x) == 1;
    }

    public static float getRandomFloat (float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

}
