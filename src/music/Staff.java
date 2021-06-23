package music;

import imageprocessing.StaffImage;
import uicomponents.renderer.CanvasRender;
import java.awt.image.BufferedImage;

public class Staff {

    private final StaffImage staffImage;
    private final int clefLineOffset;
    private final int clefFineTuneYOffset;
    private final int topVisibleLine;
    private final int bottomVisibleLine;

    public Staff(StaffImage staffImage, int clefLineOffset,
                 int clefFineTuneYOffset, int topVisibleLine, int bottomVisibleLine){
        this.staffImage = staffImage;
        this.clefLineOffset = clefLineOffset;
        this.clefFineTuneYOffset = clefFineTuneYOffset;
        this.topVisibleLine = topVisibleLine;
        this.bottomVisibleLine = bottomVisibleLine;
    }

    public BufferedImage getStaffImage(){
        return staffImage.getBufferedImage();
    }

    public int getClefYOffset(){
        return (CanvasRender.getLineSpacing() * clefLineOffset) + clefFineTuneYOffset;
    }

    public int getTopVisibleLine(){
        return topVisibleLine;
    }

    public int getBottomVisibleLine(){
        return bottomVisibleLine;
    }
}
