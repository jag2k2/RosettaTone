package uicomponents.rangeselector.noteselector.selectorfactory;

import music.note.Note;
import uicomponents.rangeselector.noteselector.NoteListRenderer;
import uicomponents.util.*;
import uicomponents.util.selectors.JComboSelector;

import javax.swing.*;

public class JComboNoteSelectorFactory implements SelectorFactory<Note> {
    private final State<Note> previewLimit;

    public JComboNoteSelectorFactory(State<Note> previewLimit) {
        this.previewLimit = previewLimit;
    }

    @Override
    public JSelector<Note> makeSelector(SelectableState<Note> state) {
        ListCellRenderer<Note> renderer = new NoteListRenderer(new JSelectorRenderer<>(new DefaultListCellRenderer()), previewLimit);
        JSelector<Note> selector = new JComboSelector<>(state, renderer);
        selector.setPreviewState(previewLimit);
        selector.refreshSelections();
        return selector;
    }
}
