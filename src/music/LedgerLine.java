package music;

import java.awt.*;

public class LedgerLine extends Line implements LineDrawable {
    private final int xStart;
    private final int xEnd;

    public LedgerLine(int lineNumber, int noteXPos, int noteWidth) {
        super(lineNumber);
        this.xStart = noteXPos - 2;
        this.xEnd = noteXPos + noteWidth + 2;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D, xStart, xEnd);
    }
}
