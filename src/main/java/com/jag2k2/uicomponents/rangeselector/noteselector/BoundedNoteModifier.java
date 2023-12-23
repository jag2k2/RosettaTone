package uicomponents.rangeselector.noteselector;

import music.Note;

import javax.swing.*;

public interface BoundedNoteModifier extends LimitModifier {
    void refreshJComboBoxOptions(JComboBox<Note> comboBox);
}
