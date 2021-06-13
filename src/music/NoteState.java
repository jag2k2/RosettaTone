package music;

import instrument.Key;

import java.util.List;

public interface NoteState {
    public void NoteOn(Key key);
    public void NoteOff(Key key);
    public ActiveNotes getActiveNotes();
}
