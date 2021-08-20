package uicomponents;

import statemodels.ConfigChangeNotifier;
import uicomponents.renderer.grandstaff.StaffModeEvaluator;
import statemodels.StaffMode;
import uicomponents.util.SelectableState;

public interface StaffModeState extends SelectableState<StaffMode>, StaffModeEvaluator {
    void addConfigChangeNotifier(ConfigChangeNotifier configChangeNotifier);
}
