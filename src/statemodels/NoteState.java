package statemodels;

import instrument.Key;
import music.NoteList;

public interface NoteState {
    public void NoteOn(Key key);
    public void NoteOff(Key key);
    public NoteList getActiveNotes();
}
