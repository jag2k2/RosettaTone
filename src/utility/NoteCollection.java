package utility;

import music.Note;

public interface NoteCollection extends Iterable<Note>{
    void add(Note note);
    boolean contains(NoteCollection noteCollection);
}
