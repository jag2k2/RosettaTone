package uicomponents.rangeselector;

import music.Note;
import uicomponents.UIComponent;

import javax.swing.event.PopupMenuListener;
import java.awt.event.ActionListener;

public interface NoteSelector extends UIComponent, PopupMenuListener, ActionListener {
    void refreshSelection(Note upperNote, Note lowerNote, Note selectedNote);
    Note getSelectedNote();
    void addActionListener(ActionListener actionListener);
}
