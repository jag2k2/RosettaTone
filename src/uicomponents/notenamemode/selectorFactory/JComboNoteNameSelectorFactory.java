package uicomponents.notenamemode.selectorFactory;

import uicomponents.notenamemode.NoteNameMode;
import uicomponents.util.*;
import uicomponents.util.selectors.JComboSelector;
import uicomponents.util.selectors.JComboSelectorImp;
import uicomponents.util.selectors.JSelector;
import uicomponents.util.selectors.JSelectorRenderer;

import javax.swing.*;

public class JComboNoteNameSelectorFactory implements SelectorFactory<NoteNameMode> {
    @Override
    public JSelector<NoteNameMode> makeSelector(SelectableState<NoteNameMode> state) {
        ListCellRenderer<NoteNameMode> renderer = new JSelectorRenderer<>(new DefaultListCellRenderer());
        JComboSelector<NoteNameMode> selector = new JComboSelectorImp<>(state);
        selector.setRenderer(renderer);
        selector.refreshSelections();
        return selector;
    }
}
