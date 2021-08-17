package uicomponents;

import statemodels.ConfigChangeNotifier;
import uicomponents.renderer.grandstaff.StaffModeEvaluator;
import uicomponents.staffmode.StaffMode;
import uicomponents.util.CanSetMode;

public interface StaffModeState extends CanSetMode<StaffMode>, StaffModeEvaluator {
    void addConfigChangeNotifier(ConfigChangeNotifier configChangeNotifier);
}
