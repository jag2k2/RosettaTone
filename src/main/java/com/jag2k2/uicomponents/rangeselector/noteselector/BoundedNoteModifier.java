package com.jag2k2.uicomponents.rangeselector.noteselector;

import com.jag2k2.music.Note;

import javax.swing.*;

public interface BoundedNoteModifier extends LimitModifier {
    void refreshJComboBoxOptions(JComboBox<Note> comboBox);
}
