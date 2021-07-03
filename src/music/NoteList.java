package music;

public interface NoteList extends Iterable<Note>{
    void add(Note note);
    void clear();
    boolean contains(NoteList noteList);
    boolean isSandwiched(Note note);
}
