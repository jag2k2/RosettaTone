package com.jag2k2.music;

import com.jag2k2.imageprocessing.StaffImage;
import com.jag2k2.uicomponents.renderer.records.RenderConstants;
import com.jag2k2.uicomponents.renderer.records.StaffConstants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Staff {

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
    public void draw(Graphics2D graphics2D, StaffImage clefImage) {
        int clefImageXPos = RenderConstants.leftMargin;
        int clefImageYPos = RenderConstants.topMargin + (RenderConstants.lineSpacing * staffConstants.clefLineOffset) + staffConstants.clefFineTuneYOffset;
        clefImage.draw(graphics2D, clefImageXPos, clefImageYPos);

        int lineThickness = RenderConstants.ledgerLineThickness;
        graphics2D.setStroke(new BasicStroke(lineThickness));

        int lineXPosStart = RenderConstants.leftMargin;
        int lineXPosEnd = RenderConstants.canvasWidth - RenderConstants.rightMargin;
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
