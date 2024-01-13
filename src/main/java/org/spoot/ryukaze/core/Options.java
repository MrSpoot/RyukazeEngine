package org.spoot.ryukaze.core;

public class Options {

    public boolean compatibleProfile;
    public int fps;
    public int height;
    public int width;
    public int ups = 20;

    public Options(boolean compatibleProfile, int fps, int width, int height , int ups) {
        this.compatibleProfile = compatibleProfile;
        this.fps = fps;
        this.height = height;
        this.width = width;
        this.ups = ups;
    }

}
