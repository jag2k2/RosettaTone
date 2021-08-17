package uicomponents;

import statemodels.ConfigChangeNotifier;
import uicomponents.renderer.grandstaff.CanCheckEnabled;
import uicomponents.trainer.eventhandler.Enableable;

public interface TrainerState extends CanCheckEnabled, Enableable {
    void addConfigChangeNotifier(ConfigChangeNotifier configChangeNotifier);
}
