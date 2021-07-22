package music.line;

import music.LineDrawable;
import uicomponents.renderer.records.RenderConstants;
import java.awt.*;

public class StaffLine extends Line implements LineDrawable {
    private final int xStart;
    private final int xEnd;

    public StaffLine(int lineNumber) {
        super(lineNumber);
        this.xStart = RenderConstants.leftMargin;
        this.xEnd = RenderConstants.canvasWidth - RenderConstants.rightMargin;
    }

    public void draw(Graphics2D graphics2D){
        super.draw(graphics2D, xStart, xEnd);
    }
}
