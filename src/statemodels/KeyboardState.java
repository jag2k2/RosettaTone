package statemodels;

import instrument.Key;
import music.NoteList;

public interface KeyboardState {
    void KeyPressed(Key key);
    void KeyReleased(Key key);
    NoteList getActiveNotes();
}
