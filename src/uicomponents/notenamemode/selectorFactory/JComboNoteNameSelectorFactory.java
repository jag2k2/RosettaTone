package uicomponents.notenamemode.selectorFactory;

import uicomponents.notenamemode.NoteNameMode;
import uicomponents.util.*;
import uicomponents.util.selectors.JComboSelector;

import javax.swing.*;

public class JComboNoteNameSelectorFactory implements SelectorFactory<NoteNameMode> {
    @Override
    public JSelector<NoteNameMode> makeSelector(SelectableState<NoteNameMode> state) {
        ListCellRenderer<NoteNameMode> renderer = new JSelectorRenderer<>(new DefaultListCellRenderer());
        JSelector<NoteNameMode> selector = new JComboSelector<>(state, renderer);
        selector.refreshSelections();
        return selector;
    }
}
