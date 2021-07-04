package music;

public interface NoteCollection extends Iterable<Note>{
    void add(Note note);
    void clear();
    boolean contains(NoteCollection noteCollection);
    boolean isSqueezed(Note note);
}
