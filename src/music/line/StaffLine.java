package music.line;

import music.LineDrawable;
import uicomponents.renderer.records.RenderConstants;
import java.awt.*;

public class StaffLine implements LineDrawable {
    private final Line line;
    private final int xStart;
    private final int xEnd;

    public StaffLine(int lineNumber) {
        this.line = new Line(lineNumber);
        this.xStart = RenderConstants.leftMargin;
        this.xEnd = RenderConstants.canvasWidth - RenderConstants.rightMargin;
    }

    public void draw(Graphics2D graphics2D){
        line.draw(graphics2D, xStart, xEnd);
    }
}
