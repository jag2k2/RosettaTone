package music;

import imageprocessing.StaffImage;
import uicomponents.renderer.RenderConstants;

public class Staff {

    private final String imagePath;
    private final double resizeFactor;
    private final int clefLineOffset;
    private final int clefFineTuneYOffset;
    private final int topVisibleLine;
    private final int bottomVisibleLine;

    public Staff(String imagePath, double resizeFactor, int clefLineOffset,
                 int clefFineTuneYOffset, int topVisibleLine, int bottomVisibleLine){
        this.imagePath = imagePath;
        this.resizeFactor = resizeFactor;
        this.clefLineOffset = clefLineOffset;
        this.clefFineTuneYOffset = clefFineTuneYOffset;
        this.topVisibleLine = topVisibleLine;
        this.bottomVisibleLine = bottomVisibleLine;
    }

    public StaffImage createStaffImage(){
        StaffImage staffImage = new StaffImage(imagePath);
        staffImage.resize(resizeFactor);
        return staffImage;
    }

    public int getClefYOffset(){
        return (RenderConstants.lineSpacing * clefLineOffset) + clefFineTuneYOffset;
    }

    public int getTopVisibleLine(){
        return topVisibleLine;
    }

    public int getBottomVisibleLine(){
        return bottomVisibleLine;
    }
}
