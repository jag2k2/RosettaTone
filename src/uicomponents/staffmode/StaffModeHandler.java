package uicomponents.staffmode;

import uicomponents.staffmode.eventhandler.JSelector;

public interface StaffModeHandler {
    JSelector createModeSelector(SelectorFactory selectorFactory, StaffMode defaultMode);
}
