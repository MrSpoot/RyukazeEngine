package org.spoot.ryukazev2.core.enumerations;

public enum Anchor {
    TOP_LEFT,
    TOP_RIGHT,
    TOP_CENTER,
    BOTTOM_LEFT,
    BOTTOM_RIGHT,
    BOTTOM_CENTER,
    CENTER_LEFT,
    CENTER_RIGHT,
    CENTER;

    public float getXOffset(float width){
        return switch (this) {
            case TOP_RIGHT, BOTTOM_RIGHT, CENTER_RIGHT -> width;
            case TOP_CENTER, BOTTOM_CENTER, CENTER -> width / 2;
            case TOP_LEFT, BOTTOM_LEFT, CENTER_LEFT -> 0;
        };
    }

    public float getYOffset(float height){
        return switch (this) {
            case BOTTOM_CENTER, BOTTOM_RIGHT, BOTTOM_LEFT -> height;
            case CENTER, CENTER_RIGHT, CENTER_LEFT -> height / 2;
            case TOP_LEFT, TOP_CENTER, TOP_RIGHT -> 0;
        };
    }

}
