package uicomponents.staffmode.selectorfactory;

import statemodels.StaffMode;
import uicomponents.util.*;
import uicomponents.util.selectors.JComboSelector;
import uicomponents.util.selectors.JComboSelectorImp;
import uicomponents.util.selectors.JSelector;
import uicomponents.util.selectors.JSelectorRenderer;

import javax.swing.*;

public class JComboStaffSelectorFactory implements SelectorFactory<StaffMode> {
    @Override
    public JSelector<StaffMode> makeSelector(SelectableState<StaffMode> state) {
        ListCellRenderer<StaffMode> renderer = new JSelectorRenderer<>(new DefaultListCellRenderer());
        JComboSelector<StaffMode> selector = new JComboSelectorImp<>(state);
        selector.setRenderer(renderer);
        selector.refreshSelections();
        return selector;
    }
}
