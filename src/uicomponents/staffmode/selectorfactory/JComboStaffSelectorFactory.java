package uicomponents.staffmode.selectorfactory;

import uicomponents.staffmode.StaffMode;
import uicomponents.util.JSelector;
import uicomponents.util.CanSetMode;
import uicomponents.util.selectors.JComboSelector;
import uicomponents.util.SelectorFactory;

public class JComboStaffSelectorFactory implements SelectorFactory<StaffMode> {
    @Override
    public JSelector<StaffMode> makeSelector(CanSetMode<StaffMode> defaultMode) {
        return new JComboSelector<>(StaffMode.class, defaultMode);
    }
}