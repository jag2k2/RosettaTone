package uicomponents;

import statemodels.ConfigChangeNotifier;
import uicomponents.renderer.grandstaff.StaffModeEvaluator;
import uicomponents.staffmode.eventhandler.StaffModeChangeObserver;

public interface StaffModeState extends StaffModeChangeObserver, StaffModeEvaluator {
    void addConfigChangeNotifier(ConfigChangeNotifier configChangeNotifier);
}
