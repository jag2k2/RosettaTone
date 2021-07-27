package instrument;

import instrument.key.Key;

public interface KeyStateManipulator {
    void keyPressed(Key key);
    void keyReleased(Key key);
}
