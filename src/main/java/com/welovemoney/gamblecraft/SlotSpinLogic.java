package com.welovemoney.gamblecraft;

import org.apache.commons.lang3.tuple.Pair;

import java.sql.Struct;
import java.util.Random;

public class SlotSpinLogic {
    private static final String[] REEL_SYMBOLS = {"Potato", "Apple", "Diamond", "Nether Star", "Wild"};
    private static final int[] SYMBOL_WEIGHTS = {40, 30, 16, 6, 8}; // Adjusted weights to simulate payout percentage
    private static final int REEL_COUNT = 3;

    // Initialize the array to avoid null references
    private static String[] slotSymbols = new String[REEL_COUNT];

    public static String[] getReelSymbols() {
        return slotSymbols;
    }

    public static Result toEnum(String[] result) {
        switch (result[0]) {
            case "Potato" -> {
                if (result[1].equals("2")) {
                    return Result.DOUBLE_POTATO;
                } else {
                    return Result.TRIPLE_POTATO;
                }
            }
            case "Apple" -> {
                if (result[1].equals("2")) {
                    return Result.DOUBLE_APPLE;
                } else {
                    return Result.TRIPLE_APPLE;
                }
            }
            case "Diamond" -> {
                if (result[1].equals("2")) {
                    return Result.DOUBLE_DIAMOND;
                } else {
                    return Result.TRIPLE_DIAMOND;
                }
            }
            case "Nether Star" -> {
                if (result[1].equals("2")) {
                    return Result.DOUBLE_STAR;
                } else {
                    return Result.TRIPLE_STAR;
                }
            }
            case "Wild" -> {
                if (result[1].equals("3")) {
                    return Result.TRIPLE_WILD;
                }
            }
        }
        return Result.NONE;
    }

    public static Pair<Result, String[]> rollOne(Random random) {
        String[] reels = new String[REEL_COUNT];
        for (int i = 0; i < REEL_COUNT; i++) {
            reels[i] = spinReel(random);
        }

        // Update the slotSymbols array with the new spin result
        slotSymbols = reels.clone();

        // Display the result
        System.out.println();
        for (String reel : reels) {
            System.out.print(reel + " ");
        }
        System.out.println();

        // Check if all symbols match
        String[] results = threeOfaKind(reels);
        if (results[0].equals("")) {
            results = twoOfaKind(reels);
        }

        return Pair.of(toEnum(results), results);
    }

    private static String spinReel(Random random) {
        int totalWeight = 0;
        for (int weight : SYMBOL_WEIGHTS) {
            totalWeight += weight;
        }

        int randomValue = random.nextInt(totalWeight);
        int cumulativeWeight = 0;

        for (int i = 0; i < REEL_SYMBOLS.length; i++) {
            cumulativeWeight += SYMBOL_WEIGHTS[i];
            if (randomValue < cumulativeWeight) {
                return REEL_SYMBOLS[i];
            }
        }

        return REEL_SYMBOLS[REEL_SYMBOLS.length - 1]; // Fallback (shouldn't happen)
    }

    public static String[] twoOfaKind(String[] reels) {
        if (reels[0].equals(reels[1])) {
            return new String[]{reels[0], "2"};
        } else if (reels[0].equals(reels[2])) {
            return new String[]{reels[0], "2"};
        } else if (reels[1].equals(reels[2])) {
            return new String[]{reels[1], "2"};
        } else if (reels[0].equals("Wild")) {
            return new String[]{reels[1], "2"};
        } else if (reels[1].equals("Wild")) {
            return new String[]{reels[0], "2"};
        } else if (reels[2].equals("Wild")) {
            return new String[]{reels[1], "2"};
        } else {
            return new String[]{"", ""};
        }
    }

    public static String[] threeOfaKind(String[] reels) {
        if (reels[0].equals(reels[1]) && reels[1].equals(reels[2])) {
            return new String[]{reels[0], "3"};
        } else if (reels[0].equals(reels[1]) && reels[2].equals("Wild")) {
            return new String[]{reels[0], "3"};
        } else if (reels[0].equals(reels[2]) && reels[1].equals("Wild")) {
            return new String[]{reels[0], "3"};
        } else if (reels[1].equals(reels[2]) && reels[0].equals("Wild")) {
            return new String[]{reels[1], "3"};
        } else {
            return new String[]{"", ""};
        }
    }
}
