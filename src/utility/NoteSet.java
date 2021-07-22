package utility;

import music.note.Note;

public interface NoteSet extends Iterable<Note>{
    void add(Note note);
    boolean containsAll(NoteSet noteSet);
}
