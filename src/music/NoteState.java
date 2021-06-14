package music;

import instrument.Key;

public interface NoteState {
    public void NoteOn(Key key);
    public void NoteOff(Key key);
    public ActiveNotes getActiveNotes(NoteClef noteClef);
}
