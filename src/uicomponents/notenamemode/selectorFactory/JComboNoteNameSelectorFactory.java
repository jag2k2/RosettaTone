package uicomponents.notenamemode.selectorFactory;

import uicomponents.notenamemode.NoteNameMode;
import uicomponents.notenamemode.selectors.JComboNoteNameSelector;
import uicomponents.util.CanSetMode;
import uicomponents.util.JSelector;
import uicomponents.util.SelectorFactory;

public class JComboNoteNameSelectorFactory implements SelectorFactory<NoteNameMode> {
    @Override
    public JSelector<NoteNameMode> makeSelector(CanSetMode<NoteNameMode> defaultMode) {
        return new JComboNoteNameSelector(defaultMode);
    }
}
