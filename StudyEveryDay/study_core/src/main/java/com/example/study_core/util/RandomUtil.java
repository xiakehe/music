package com.example.study_core.util;

import java.util.Random;

public class RandomUtil {

    private static Random random = new Random();

    public static int getRandomInt(int i) {
        return random.nextInt(i);
    }
}
