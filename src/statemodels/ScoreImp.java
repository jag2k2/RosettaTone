package statemodels;

import notification.LimitChangeObserver;
import trainer.ScoreKeepable;
import uicomponents.renderer.grandstaff.ScoreDrawable;
import uicomponents.renderer.records.RenderConstants;
import uicomponents.trainer.Resettable;
import utility.Maybe;

import java.awt.*;

public class ScoreImp implements ScoreKeepable, Resettable, ScoreDrawable, LimitChangeObserver {
    private int hits = 0;
    private int misses = 0;
    private Maybe<ConfigChangeNotifier> configChangeNotifier = new Maybe<>();

    public void addConfigChangeNotifier(ConfigChangeNotifier configChangeNotifier){
        this.configChangeNotifier = new Maybe<>(configChangeNotifier);
    }

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
        for(ConfigChangeNotifier notifier : configChangeNotifier){
            notifier.notifyObservers();
        }
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
