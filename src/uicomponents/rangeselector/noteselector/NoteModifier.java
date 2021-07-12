package uicomponents.rangeselector.noteselector;

import music.Note;

public interface NoteModifier extends Comparable<Note> {
    void setLimit(Note note);
    Note getLimit();
    void increment();
    void decrement();
}
