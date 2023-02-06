package com.codecool.dungeoncrawl.util;

import java.util.Random;

public class GenerateRandom {

    static Random random = new Random();

    public static int nextInt(int lower, int upper) {
        return random.nextInt(upper - lower) + lower;
    }
}
