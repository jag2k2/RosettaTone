package statemodels;

import instrument.Key;
import music.NoteCollection;

public interface KeyboardState {
    void keyPressed(Key key);
    void keyReleased(Key key);
    NoteCollection getActiveNotes();
}
