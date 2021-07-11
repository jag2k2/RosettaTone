package uicomponents.rangeselector.noteselector;

import music.Note;

import javax.swing.*;

public interface NoteLimitModel {
    void setActiveLimit(Note note);
    void incrementActive();
    void decrementActive();
    void refreshJComboBoxOptions(JComboBox<Note> comboBox);
}
