package com.jag2k2.uicomponents.renderer.records;

public class StaffConstants {
    public final double resizeFactor;
    public final int clefLineOffset;
    public final int clefFineTuneYOffset;
    public final int topVisibleLine;
    public final int bottomVisibleLine;

    public StaffConstants(double resizeFactor, int clefLineOffset, int clefFineTuneYOffset, int topVisibleLine, int bottomVisibleLine){
        this.resizeFactor = resizeFactor;
        this.clefLineOffset = clefLineOffset;
        this.clefFineTuneYOffset = clefFineTuneYOffset;
        this.topVisibleLine = topVisibleLine;
        this.bottomVisibleLine = bottomVisibleLine;
    }
}
