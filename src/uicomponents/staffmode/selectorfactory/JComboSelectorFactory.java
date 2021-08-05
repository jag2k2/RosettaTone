package uicomponents.staffmode.selectorfactory;

import uicomponents.staffmode.SelectorFactory;
import uicomponents.staffmode.modehandler.JSelector;
import uicomponents.staffmode.modehandler.StaffModeChangeObserver;
import uicomponents.staffmode.selectors.JComboSelector;

public class JComboSelectorFactory implements SelectorFactory {
    @Override
    public JSelector makeSelector(StaffModeChangeObserver staffMode) {
        return new JComboSelector(staffMode);
    }
}
