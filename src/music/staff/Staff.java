package music.staff;

import music.line.StaffLine;
import music.LineDrawable;
import tuples.LineSetImp;
import imageprocessing.StaffImage;
import uicomponents.renderer.grandstaff.StaffDrawable;
import uicomponents.renderer.records.RenderConstants;
import uicomponents.renderer.records.StaffConstants;
import utility.LineSet;

import java.awt.*;

public class Staff implements StaffDrawable {

    private final StaffConstants staffConstants;

    public Staff(StaffConstants staffConstants){
        this.staffConstants = staffConstants;
    }

    public void draw(Graphics2D graphics2D, StaffImage clefImage) {
        int clefImageXPos = RenderConstants.leftMargin;
        int clefImageYPos = RenderConstants.topMargin + (RenderConstants.lineSpacing * staffConstants.clefLineOffset) + staffConstants.clefFineTuneYOffset;
        clefImage.draw(graphics2D, clefImageXPos, clefImageYPos);

        LineSet visibleLines = getVisibleLines();
        visibleLines.draw(graphics2D);
    }

    protected LineSet getVisibleLines(){
        LineSet visibleLines = new LineSetImp();
        for (int i = staffConstants.topVisibleLine; i <= staffConstants.bottomVisibleLine; i++){
            if ((i % 2) == 0){
                visibleLines.add(new StaffLine(i));
            }
        }
        return visibleLines;
    }
}
