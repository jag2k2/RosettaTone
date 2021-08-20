package uicomponents.staffmode.selectorfactory;

import statemodels.StaffMode;
import uicomponents.util.*;
import uicomponents.util.selectors.JComboSelector;

import javax.swing.*;

public class JComboStaffSelectorFactory implements SelectorFactory<StaffMode> {
    @Override
    public JSelector<StaffMode> makeSelector(SelectableState<StaffMode> state) {
        ListCellRenderer<StaffMode> renderer = new JSelectorRenderer<>(new DefaultListCellRenderer());
        JSelector<StaffMode> selector = new JComboSelector<>(state, renderer);
        selector.refreshSelections();
        return selector;
    }
}
