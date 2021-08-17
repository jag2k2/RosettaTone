package uicomponents.notenamemode.selectorFactory;

import uicomponents.notenamemode.NoteNameMode;
import uicomponents.util.selectors.JComboSelector;
import uicomponents.util.CanSetMode;
import uicomponents.util.JSelector;
import uicomponents.util.SelectorFactory;

public class JComboNoteNameSelectorFactory implements SelectorFactory<NoteNameMode> {
    @Override
    public JSelector<NoteNameMode> makeSelector(CanSetMode<NoteNameMode> defaultMode) {
        return new JComboSelector<>(NoteNameMode.class, defaultMode);
    }
}
