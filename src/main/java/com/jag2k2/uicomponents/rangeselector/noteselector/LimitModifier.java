package com.jag2k2.uicomponents.rangeselector.noteselector;

import com.jag2k2.music.Note;

public interface LimitModifier extends Comparable<Note> {
    void setLimit(Note note);
    Note getLimit();
    void increment();
    void decrement();
}
