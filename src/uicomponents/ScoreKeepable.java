package uicomponents;

import notification.LimitChangeObserver;
import statemodels.ConfigChangeNotifier;
import trainer.CanIncrementScore;
import uicomponents.renderer.grandstaff.ScoreDrawable;
import uicomponents.trainer.eventhandler.Resettable;

public interface ScoreKeepable extends CanIncrementScore, Resettable, ScoreDrawable, LimitChangeObserver {
    void addConfigChangeNotifier(ConfigChangeNotifier configChangeNotifier);
}
