package music;

import statemodels.ClefDrawable;
import uicomponents.renderer.records.RenderConstants;
import uicomponents.renderer.records.StaffConstants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Staff implements ClefDrawable {

    private final StaffConstants staffConstants;

    public Staff(StaffConstants staffConstants){
        this.staffConstants = staffConstants;
    }

    public boolean isLineVisible(int lineNumber){
        return (staffConstants.topVisibleLine <= lineNumber) && (lineNumber <= staffConstants.bottomVisibleLine);
    }

    public boolean isLineAboveVisible(int lineNumber){
        return lineNumber < staffConstants.topVisibleLine;
    }

    public boolean isLineBelowVisible(int lineNumber){
        return lineNumber > staffConstants.bottomVisibleLine;
    }

    public int getClosestVisibleLine(int lineNumber){
        int distanceFromTop = Math.abs(lineNumber - staffConstants.topVisibleLine);
        int distanceFromBottom = Math.abs(lineNumber - staffConstants.bottomVisibleLine);
        if (distanceFromTop < distanceFromBottom)
            return staffConstants.topVisibleLine;
        else
            return staffConstants.bottomVisibleLine;
    }

    @Override
    public void draw(Graphics2D graphics2D, BufferedImage image) {
        int clefImageXPos = RenderConstants.getClefXOffset();
        int clefImageYPos = (RenderConstants.lineSpacing * staffConstants.clefLineOffset) + staffConstants.clefFineTuneYOffset;
        graphics2D.drawImage(image, null, clefImageXPos, clefImageYPos);

        int lineThickness = RenderConstants.lineThickness;
        graphics2D.setStroke(new BasicStroke(lineThickness));

        int lineXPosStart = RenderConstants.getLineXStart();
        int lineXPosEnd = RenderConstants.getLineXEnd();
        for (int visibleLine : getVisibleLines()) {
            int lineYPos = RenderConstants.getLineYOffset(visibleLine);
            graphics2D.drawLine(lineXPosStart, lineYPos, lineXPosEnd, lineYPos);
        }
    }

    protected List<Integer> getVisibleLines(){
        List<Integer> visibleLines = new ArrayList<>();
        for (int i = staffConstants.topVisibleLine; i <= staffConstants.bottomVisibleLine; i++){
            if ((i % 2) == 0){
                visibleLines.add(i);
            }
        }
        return visibleLines;
    }
}
