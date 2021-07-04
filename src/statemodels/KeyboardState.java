package statemodels;

import instrument.Key;
import music.NoteCollection;

public interface KeyboardState {
    void KeyPressed(Key key);
    void KeyReleased(Key key);
    NoteCollection getActiveNotes();
}
