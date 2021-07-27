package music.line;

import music.LineDrawable;

import java.awt.*;

public class LedgerLine implements LineDrawable {
    private final Line line;
    private final int xStart;
    private final int xEnd;

    public LedgerLine(Line line, int noteXPos, int noteWidth) {
        this.line = line;
        this.xStart = noteXPos - 2;
        this.xEnd = noteXPos + noteWidth + 2;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        line.draw(graphics2D, xStart, xEnd);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof LedgerLine){
            LedgerLine toCompare = (LedgerLine) obj;
            return line.equals(toCompare.line);
        }
        return false;
    }

    @Override
    public String toString() {
        return line.toString();
    }

    @Override
    public int hashCode() {
        return line.hashCode();
    }
}
