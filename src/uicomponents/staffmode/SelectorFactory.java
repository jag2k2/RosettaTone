package uicomponents.staffmode;

import uicomponents.staffmode.modehandler.JSelector;
import uicomponents.staffmode.modehandler.StaffModeChangeObserver;

public interface SelectorFactory {
    JSelector makeSelector(StaffModeChangeObserver staffMode);
}
