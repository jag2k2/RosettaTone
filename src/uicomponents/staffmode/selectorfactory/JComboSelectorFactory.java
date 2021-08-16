package uicomponents.staffmode.selectorfactory;

import uicomponents.staffmode.SelectorFactory;
import uicomponents.staffmode.eventhandler.JSelector;
import uicomponents.staffmode.eventhandler.StaffModeChangeObserver;
import uicomponents.staffmode.selectors.JComboSelector;

public class JComboSelectorFactory implements SelectorFactory {
    @Override
    public JSelector makeSelector(StaffModeChangeObserver staffMode) {
        return new JComboSelector(staffMode);
    }
}
