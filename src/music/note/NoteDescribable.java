package music.note;

public interface NoteDescribable {
    NoteName getNoteName();
    int getOctave();
    NoteAccidental getAccidental();
}
