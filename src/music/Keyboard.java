package music;

import java.util.List;

public interface Keyboard {
    public void pressKey(Key key);
    public void releaseKey(Key key);
    public List<Note> getPressedNotes();
}
