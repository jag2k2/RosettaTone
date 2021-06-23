package music;

public interface ActiveNotes extends Iterable<Note>{
    void add(Note note);
    boolean isShifted(Note note);
}
