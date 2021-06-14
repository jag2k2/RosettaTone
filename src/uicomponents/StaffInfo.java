package uicomponents;

import java.io.File;

public class StaffInfo {

    private final File clefFile;
    private final double scaleFactor;
    private final int clefLineOffset;
    private final int clefFineTuneYOffset;
    private final int staffYOffset;
    private final int topVisibleLine;
    private final int bottomVisibleLine;

    public StaffInfo(File clefFile, double scaleFactor, int clefLineOffset,
                     int clefFineTuneYOffset, int staffYOffset, int topVisibleLine, int bottomVisibleLine){
        this.clefFile = clefFile;
        this.scaleFactor = scaleFactor;
        this.clefLineOffset = clefLineOffset;
        this.clefFineTuneYOffset = clefFineTuneYOffset;
        this.staffYOffset = staffYOffset;
        this.topVisibleLine = topVisibleLine;
        this.bottomVisibleLine = bottomVisibleLine;
    }

    public File getImageFile() {
        return clefFile;
    }

    public double getScaleFactor(){
        return scaleFactor;
    }

    public int getClefYOffset(int lineSpacing){
        return (lineSpacing * clefLineOffset) + clefFineTuneYOffset + staffYOffset;
    }

    public int getStaffYOffset() {
        return staffYOffset;
    }

    public int getTopVisibleLine(){
        return topVisibleLine;
    }

    public int getBottomVisibleLine(){
        return bottomVisibleLine;
    }
}
