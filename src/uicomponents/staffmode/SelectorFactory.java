package uicomponents.staffmode;

import uicomponents.staffmode.eventhandler.JSelector;
import uicomponents.staffmode.eventhandler.StaffModeChangeObserver;

public interface SelectorFactory {
    JSelector makeSelector(StaffModeChangeObserver staffMode);
}
