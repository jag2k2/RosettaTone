package com.jag2k2.utility;

import com.jag2k2.music.Note;

public interface NoteSet extends Iterable<Note>{
    void add(Note note);
    boolean containsAll(NoteSet noteSet);
}
