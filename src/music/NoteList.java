package music;

public interface NoteList extends Iterable<Note>{
    void add(Note note);
    boolean isSandwiched(Note note);
}
