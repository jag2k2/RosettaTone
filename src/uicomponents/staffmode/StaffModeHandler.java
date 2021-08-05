package uicomponents.staffmode;

import uicomponents.staffmode.modehandler.JSelector;

public interface StaffModeHandler {
    JSelector createModeSelector(SelectorFactory selectorFactory, StaffMode defaultMode);
}
