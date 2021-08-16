package uicomponents;

import notification.LimitChangeObserver;
import trainer.CanIncrementScore;
import uicomponents.renderer.grandstaff.ScoreDrawable;
import uicomponents.trainer.eventhandler.Resettable;

public interface ScoreKeepable extends CanIncrementScore, Resettable, ScoreDrawable, LimitChangeObserver {

}
