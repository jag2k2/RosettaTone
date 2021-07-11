package music;

import imageprocessing.StaffImage;
import uicomponents.renderer.RenderConstants;

import java.util.ArrayList;
import java.util.List;

public class Staff {

    public final String imagePath;
    public final double resizeFactor;
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

    public boolean isLineVisible(int lineNumber){
        return (topVisibleLine <= lineNumber) && (lineNumber <= bottomVisibleLine);
    }

    public boolean isLineAboveVisible(int lineNumber){
        return lineNumber < topVisibleLine;
    }

    public boolean isLineBelowVisible(int lineNumber){
        return lineNumber > bottomVisibleLine;
    }

    public int getClosestVisibleLine(int lineNumber){
        int distanceFromTop = Math.abs(lineNumber - topVisibleLine);
        int distanceFromBottom = Math.abs(lineNumber - bottomVisibleLine);
        if (distanceFromTop < distanceFromBottom)
            return topVisibleLine;
        else
            return bottomVisibleLine;
    }

    public List<Integer> getVisibleLines(){
        List<Integer> visibleLines = new ArrayList<>();
        for (int i = topVisibleLine; i <= bottomVisibleLine; i++){
            if ((i % 2) == 0){
                visibleLines.add(i);
            }
        }
        return visibleLines;
    }
}
