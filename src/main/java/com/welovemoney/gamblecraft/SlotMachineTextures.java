package com.welovemoney.gamblecraft;

import java.util.HashMap;
import java.util.Map;

public class SlotMachineTextures {
    public static final Map<Integer, String> TEXTURE_MAP = new HashMap<>();

    static {
        String[] items = {"apple", "diamond", "netherite", "netherstar", "potato"};
        int index = 1;

        for (String first : items) {
            for (String second : items) {
                for (String third : items) {
                    String key = String.join("_", first, second, third);
                    TEXTURE_MAP.put(index++,key);
                }
            }
        }
    }

    public static String getTexture(int index) {
        return TEXTURE_MAP.get(index);
    }
}
