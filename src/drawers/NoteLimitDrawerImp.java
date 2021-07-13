package drawers;

import uicomponents.MusicDrawable;

import java.awt.*;

public class NoteLimitDrawerImp implements MusicDrawable {
    private final MusicDrawConnectable lowerNoteLimit;
    private final MusicDrawConnectable upperNoteLimit;

    public NoteLimitDrawerImp(MusicDrawConnectable lowerNoteLimit, MusicDrawConnectable upperNoteLimit) {
        this.lowerNoteLimit = lowerNoteLimit;
        this.upperNoteLimit = upperNoteLimit;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        lowerNoteLimit.draw(graphics2D);
        upperNoteLimit.draw(graphics2D);

        int connX1 = upperNoteLimit.getPosition().x;
        int connX2 = lowerNoteLimit.getPosition().x;
        int connY1 = upperNoteLimit.getPosition().y;
        int connY2 = lowerNoteLimit.getPosition().y;

        graphics2D.setColor(Color.BLACK);
        int lineThickness = 5;

        graphics2D.setStroke(new BasicStroke(lineThickness));
        graphics2D.drawLine(connX1, connY1, connX2, connY2);
    }
}
