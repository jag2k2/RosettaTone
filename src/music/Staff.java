package music;

import java.io.File;

public class Staff {

    private final File clefFile;
    private final double scaleFactor;
    private final int clefLineOffset;
    private final int clefFineTuneYOffset;
    private final int topVisibleLine;
    private final int bottomVisibleLine;

    public Staff(File clefFile, double scaleFactor, int clefLineOffset,
                 int clefFineTuneYOffset, int topVisibleLine, int bottomVisibleLine){
        this.clefFile = clefFile;
        this.scaleFactor = scaleFactor;
        this.clefLineOffset = clefLineOffset;
        this.clefFineTuneYOffset = clefFineTuneYOffset;
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
        return (lineSpacing * clefLineOffset) + clefFineTuneYOffset;
    }

    public int getTopVisibleLine(){
        return topVisibleLine;
    }

    public int getBottomVisibleLine(){
        return bottomVisibleLine;
    }
}
