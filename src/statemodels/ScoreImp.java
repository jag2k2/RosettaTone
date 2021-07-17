package statemodels;

import notification.LimitChangeObserver;
import trainer.ScoreKeepable;
import uicomponents.renderer.ScoreDrawable;
import uicomponents.renderer.records.RenderConstants;

import java.awt.*;

public class ScoreImp implements ScoreKeepable, ScoreDrawable, LimitChangeObserver {
    private int hits = 0;
    private int misses = 0;

    @Override
    public void addHit() {
        hits++;
    }

    @Override
    public void addMiss() {
        misses++;
    }

    @Override
    public void reset() {
        hits = 0;
        misses = 0;
    }

    @Override
    public void limitChanged() {
        reset();
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setFont(new Font("TimesRoman", Font.PLAIN, 40));

        int hitXPosition = RenderConstants.leftMargin + RenderConstants.hitXOffset;
        int hitYPosition = RenderConstants.topMargin + RenderConstants.hitYOffset;
        graphics2D.drawString("Hits: " + hits, hitXPosition, hitYPosition);

        int missXPosition = RenderConstants.leftMargin + RenderConstants.missXOffset;
        int missYPosition = RenderConstants.topMargin + RenderConstants.missYOffset;
        graphics2D.drawString("Misses: " + misses, missXPosition, missYPosition);
    }
}
