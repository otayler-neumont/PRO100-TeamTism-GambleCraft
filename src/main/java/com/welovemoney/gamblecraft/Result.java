package com.welovemoney.gamblecraft;

public enum Result {
    TRIPLE_STAR,
    DOUBLE_STAR,
    TRIPLE_POTATO,
    DOUBLE_POTATO,
    TRIPLE_APPLE,
    DOUBLE_APPLE,
    TRIPLE_DIAMOND,
    DOUBLE_DIAMOND,
    TRIPLE_WILD,
    NONE;

    @Override
    public String toString() {
        switch (this) {
            case TRIPLE_STAR:
                return "Triple Star";
            case DOUBLE_STAR:
                return "Double Star";
            case TRIPLE_POTATO:
                return "Triple Potato";
            case DOUBLE_POTATO:
                return "Double Potato";
            case TRIPLE_APPLE:
                return "Triple Apple";
            case DOUBLE_APPLE:
                return "Double Apple";
            case TRIPLE_DIAMOND:
                return "Triple Diamond";
            case DOUBLE_DIAMOND:
                return "Double Diamond";
            case TRIPLE_WILD:
                return "Triple Wild";
            case NONE:
                return "None";
            default:
                throw new IllegalArgumentException("Unknown enum type");
        }
    }
}

