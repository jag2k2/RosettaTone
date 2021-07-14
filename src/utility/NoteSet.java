package utility;

import music.Note;

public interface NoteSet extends Iterable<Note>{
    void add(Note note);
    boolean containsAll(NoteSet noteSet);
}
