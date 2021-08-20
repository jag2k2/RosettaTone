package uicomponents.rangeselector.noteselector;

import music.note.Note;
import uicomponents.util.State;

public interface SteppableState<T> extends State<T>, Comparable<Note> {
    void increment();
    void decrement();
}
